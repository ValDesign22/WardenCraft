package fr.valdesign.wardencraft.event;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.blood.PlayerBlood;
import fr.valdesign.wardencraft.networking.ModMessages;
import fr.valdesign.wardencraft.networking.packet.BloodDataSyncS2CPacket;
import fr.valdesign.wardencraft.blood.PlayerBloodProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WardenCraft.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerBloodProvider.PLAYER_BLOOD).isPresent()) {
                event.addCapability(new ResourceLocation(WardenCraft.MOD_ID, "properties"), new PlayerBloodProvider());
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerBlood.class);
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
                if(blood.getBlood() > 0 && event.player.getRandom().nextFloat() < 0.002f) {
                    blood.subBlood(1);
                    ModMessages.sendToPlayer(new BloodDataSyncS2CPacket(blood.getBlood()), ((ServerPlayer) event.player));
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
                    ModMessages.sendToPlayer(new BloodDataSyncS2CPacket(blood.getBlood()), player);
                });
            }
        }
    }
}