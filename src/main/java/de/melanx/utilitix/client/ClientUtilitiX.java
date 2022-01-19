package de.melanx.utilitix.client;

import de.melanx.utilitix.Textures;
import de.melanx.utilitix.content.bell.ItemMobBell;
import de.melanx.utilitix.content.slime.SlimeRender;
import de.melanx.utilitix.registration.ModItems;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientUtilitiX {

    public ClientUtilitiX() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerItemColors);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Textures::registerTextures);

        MinecraftForge.EVENT_BUS.addListener(SlimeRender::renderWorld);
        MinecraftForge.EVENT_BUS.register(new ClientEventListener());
        ClientRegistry.registerKeyBinding(Keys.SAVE_MAP);
    }

    private void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, idx) -> idx == 1 ? 0xFF000000 | ItemMobBell.getColor(stack) : 0xFFFFFFFF, ModItems.mobBell);
    }
}