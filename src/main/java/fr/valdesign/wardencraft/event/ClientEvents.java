package fr.valdesign.wardencraft.event;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.networking.packet.SonicBoomUsagePacket;
import fr.valdesign.wardencraft.client.WardenBloodHUDOverlay;
import fr.valdesign.wardencraft.networking.ModMessages;
import fr.valdesign.wardencraft.networking.packet.DrinkBloodC2SPacket;
import fr.valdesign.wardencraft.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = WardenCraft.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.DRINKING_KEY.consumeClick()) {
                ModMessages.sendToServer(new DrinkBloodC2SPacket());
            }
            if(KeyBinding.SONIC_BOOM_KEY.consumeClick()) {
                ModMessages.sendToServer(new SonicBoomUsagePacket());
            }
        }
    }
    @Mod.EventBusSubscriber(modid = WardenCraft.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.SONIC_BOOM_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("blood", WardenBloodHUDOverlay.HUD_WARDEN_BLOOD);
        }
    }
}
