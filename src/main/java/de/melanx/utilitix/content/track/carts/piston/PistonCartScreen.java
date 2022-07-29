package de.melanx.utilitix.content.track.carts.piston;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.melanx.utilitix.UtilitiX;
import de.melanx.utilitix.network.PistonCartModeCycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import java.awt.Color;

public class PistonCartScreen extends AbstractContainerScreen<PistonCartContainerMenu> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(UtilitiX.getInstance().modid, "textures/container/piston_cart.png");

    private int relX;
    private int relY;

    public PistonCartScreen(PistonCartContainerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 186;
        MinecraftForge.EVENT_BUS.addListener(this::onGuiInit);
    }

    private void onGuiInit(ScreenEvent.Init event) {
        this.relX = (event.getScreen().width - this.imageWidth) / 2;
        this.relY = (event.getScreen().height - this.imageHeight) / 2;
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(poseStack, this.relX, this.relY, 0, 0, this.imageWidth, this.imageHeight);
        if (mouseX >= this.relX + 65 && mouseX <= this.relX + 111 && mouseY >= this.relY + 18 && mouseY <= this.relY + 34) {
            this.blit(poseStack, this.relX + 64, this.relY + 17, 176, 0, 48, 18);
        }
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack poseStack, int mouseX, int mouseY) {
        String s = this.title.getString();
        //noinspection IntegerDivisionInFloatingPointContext
        this.font.draw(poseStack, s, (this.imageWidth / 2) - (this.font.width(s) / 2), 5, Color.DARK_GRAY.getRGB());
        this.font.draw(poseStack, this.playerInventoryTitle, 8, this.imageHeight - 94, Color.DARK_GRAY.getRGB());
        if (this.menu.entity != null) {
            //noinspection ConstantConditions
            int modeStrWidth = this.minecraft.font.width(this.menu.entity.getMode().name);
            //noinspection IntegerDivisionInFloatingPointContext
            this.minecraft.font.drawShadow(poseStack, this.menu.entity.getMode().name, 88 - (modeStrWidth / 2), 22, 0xFFFFFF);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (mouseX >= this.relX + 65 && mouseX <= this.relX + 111 && mouseY >= this.relY + 18 && mouseY <= this.relY + 34 && this.menu.entity != null) {
                UtilitiX.getNetwork().channel.sendToServer(new PistonCartModeCycle(this.menu.entity.getId()));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
