package fr.valdesign.wardencraft.event;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.networking.packet.SonicBoomUsagePacket;
import fr.valdesign.wardencraft.client.WardenBloodHUDOverlay;
import fr.valdesign.wardencraft.networking.ModMessages;
import fr.valdesign.wardencraft.networking.packet.DrinkBloodC2SPacket;
import fr.valdesign.wardencraft.util.KeyBinding;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
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

        @SubscribeEvent
        public static void EntityJoinLevelEvent(EntityJoinLevelEvent event) {
            VersionChecker.CheckResult result = VersionChecker.getResult(ModList.get().getModFileById(WardenCraft.MOD_ID).getMods().get(0));

            if (result.status() == VersionChecker.Status.OUTDATED) {
                event.getEntity().sendSystemMessage(Component.translatable("Warning: WardenCraft is outdated! A new version is available: " + result.url()));
            } else if (result.status() == VersionChecker.Status.BETA) {
                event.getEntity().sendSystemMessage(Component.translatable("Warning: You are using a beta version of WardenCraft, please use a stable version: " + result.url()));
            } else if (result.status() == VersionChecker.Status.UP_TO_DATE) {
                event.getEntity().sendSystemMessage(Component.translatable("WardenCraft is up to date!"));
            } else if (result.status() == VersionChecker.Status.FAILED) {
                event.getEntity().sendSystemMessage(Component.translatable("Warning: WardenCraft version check failed!"));
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
