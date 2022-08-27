package fr.valdesign.wardencraft.networking.packet;

import fr.valdesign.wardencraft.item.ModItems;
import fr.valdesign.wardencraft.networking.ModMessages;
import fr.valdesign.wardencraft.blood.PlayerBloodProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DrinkBloodC2SPacket {
    private static final String MESSAGE_DRINK_BLOOD = "message.wardencraft.drink_blood";
    private static final String MESSAGE_NO_BLOOD = "message.wardencraft.no_blood";
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

            if(hasWardenHearthInInventory(player)) {
                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_BLOOD).withStyle(ChatFormatting.DARK_AQUA));
                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
                    player.sendSystemMessage(Component.nullToEmpty("Your blood: " + blood.getBlood()));
                    blood.addBlood(5);
                    if(blood.getBlood() < 10) removeWardenHearth(player);
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

    public void removeWardenHearth(ServerPlayer player) {
        int slotMatchingItem = player.getInventory().findSlotMatchingItem(ModItems.WARDEN_HEART.get().getDefaultInstance());

        player.getInventory().removeItem(slotMatchingItem, 1);
    }

    private boolean hasWardenHearthInInventory(ServerPlayer player) {
        int wardenHearthSlot = player.getInventory().findSlotMatchingItem(ModItems.WARDEN_HEART.get().getDefaultInstance());

        return wardenHearthSlot != -1;
    }
}
