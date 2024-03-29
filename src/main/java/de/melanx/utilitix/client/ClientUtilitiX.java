package de.melanx.utilitix.client;

import de.melanx.utilitix.content.bell.ItemMobBell;
import de.melanx.utilitix.content.slime.SlimeRender;
import de.melanx.utilitix.registration.ModItems;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientUtilitiX {

    public ClientUtilitiX() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerItemColors);

        MinecraftForge.EVENT_BUS.addListener(SlimeRender::renderWorld);
        MinecraftForge.EVENT_BUS.register(new ClientEventListener());
    }

    private void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, idx) -> idx == 1 ? 0xFF000000 | ItemMobBell.getColor(stack) : 0xFFFFFFFF, ModItems.mobBell);
    }
}
