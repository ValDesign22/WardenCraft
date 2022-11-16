package fr.valdesign.wardencraft.networking.packet;

import fr.valdesign.wardencraft.item.ModItems;
import fr.valdesign.wardencraft.networking.ModMessages;
import fr.valdesign.wardencraft.blood.PlayerBloodProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DrinkBloodC2SPacket {
    private static final String MESSAGE_DRINK_BLOOD = "message.wardencraft.drink_blood";
    private static final String MESSAGE_NO_BLOOD = "message.wardencraft.no_blood";
    private static final String MESSAGE_ALREADY_FULL = "message.wardencraft.already_full";
    public DrinkBloodC2SPacket() {
    }
    public DrinkBloodC2SPacket(FriendlyByteBuf buf) {
    }
    public void toBytes(FriendlyByteBuf buf) {
    }
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            assert player != null;
            ServerLevel level = player.getLevel();

            if(hasWardenHeartInInventory(player)) {

                player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
                    if(blood.getBlood() < 10) {
                        removeWardenHeart(player);
                        blood.addBlood(5);
                        player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_BLOOD).withStyle(ChatFormatting.DARK_AQUA));
                    } else player.sendSystemMessage(Component.translatable(MESSAGE_ALREADY_FULL).withStyle(ChatFormatting.RED));
                    ModMessages.sendToPlayer(new BloodDataSyncS2CPacket(blood.getBlood()), player);
                });


            } else {
                player.sendSystemMessage(Component.translatable(MESSAGE_NO_BLOOD).withStyle(ChatFormatting.RED));
                player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
                    ModMessages.sendToPlayer(new BloodDataSyncS2CPacket(blood.getBlood()), player);
                });
            }
        });
    }

    public void removeWardenHeart(ServerPlayer player) {
        int slotMatchingItem = player.getInventory().findSlotMatchingItem(ModItems.WARDEN_HEART.get().getDefaultInstance());

        player.getInventory().removeItem(slotMatchingItem, 1);
    }

    private boolean hasWardenHeartInInventory(ServerPlayer player) {
        int wardenHeartSlot = player.getInventory().findSlotMatchingItem(ModItems.WARDEN_HEART.get().getDefaultInstance());

        return wardenHeartSlot != -1;
    }
}
