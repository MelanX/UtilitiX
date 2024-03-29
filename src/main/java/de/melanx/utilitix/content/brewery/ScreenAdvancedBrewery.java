package de.melanx.utilitix.content.brewery;

import com.mojang.blaze3d.systems.RenderSystem;
import de.melanx.utilitix.UtilitiX;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import java.awt.Color;

public class ScreenAdvancedBrewery extends AbstractContainerScreen<ContainerMenuAdvancedBrewery> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(UtilitiX.getInstance().modid, "textures/container/advanced_brewery.png");
    private static final int[] BUBBLE_SIZES = new int[]{29, 24, 20, 16, 11, 6, 0};

    private int relX;
    private int relY;

    public ScreenAdvancedBrewery(ContainerMenuAdvancedBrewery menu, Inventory inv, Component title) {
        super(menu, inv, title);
        MinecraftForge.EVENT_BUS.addListener(this::onGuiInit);
    }

    private void onGuiInit(ScreenEvent.Init event) {
        this.relX = (event.getScreen().width - this.imageWidth) / 2;
        this.relY = (event.getScreen().height - this.imageHeight) / 2;
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(TEXTURE, this.relX, this.relY, 0, 0, this.imageWidth, this.imageHeight);
        TileAdvancedBrewery tile = this.menu.getBlockEntity();
        int fuelWidth = Mth.clamp(Math.round(((18 * tile.getFuel()) + 19) / 20f), 0, 18);
        if (tile.getFuel() > 0 && fuelWidth > 0) {
            guiGraphics.blit(TEXTURE, this.relX + 60, this.relY + 44, 176, 29, fuelWidth, 4);
        }
        int brewTime = Mth.clamp(tile.getBrewTime(), 0, TileAdvancedBrewery.MAX_BREW_TIME);
        if (tile.getFuel() > 0 && brewTime > 0) {
            int textureHeight = Mth.clamp(Math.round(28f * (brewTime / (float) TileAdvancedBrewery.MAX_BREW_TIME)), 0, 28);
            if (textureHeight > 0) {
                guiGraphics.blit(TEXTURE, this.relX + 97, this.relY + 16, 176, 0, 9, textureHeight);
            }
            textureHeight = BUBBLE_SIZES[((TileAdvancedBrewery.MAX_BREW_TIME - brewTime) / 2) % BUBBLE_SIZES.length];
            if (textureHeight > 0) {
                guiGraphics.blit(TEXTURE, this.relX + 63, this.relY + 14 + 29 - textureHeight, 185, 29 - textureHeight, 12, textureHeight);
            }
        }
    }

    @Override
    protected void renderLabels(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        String s = this.title.getString();
        guiGraphics.drawString(this.font, s, (this.imageWidth / 2) - (this.font.width(s) / 2), 5, Color.DARK_GRAY.getRGB(), false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 94, Color.DARK_GRAY.getRGB(), false);
    }
}
