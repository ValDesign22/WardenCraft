package fr.valdesign.wardencraft.networking.packet;

import fr.valdesign.wardencraft.blood.PlayerBloodProvider;
import fr.valdesign.wardencraft.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class SonicBoomUsagePacket {
    public SonicBoomUsagePacket() {
    }
    public SonicBoomUsagePacket(FriendlyByteBuf buf) {
    }
    public void toBytes(FriendlyByteBuf buf) {
    }
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            assert player != null;
            ServerLevel level = player.getLevel();

            if (!player.isShiftKeyDown()) return;

            //if(hasEnoughWardenBlood(player)) {
                player.sendSystemMessage(Component.translatable("Using effect").withStyle(ChatFormatting.DARK_AQUA));
                level.playSound(null, player.getOnPos(), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
                    blood.subBlood(3);
                    ModMessages.sendToPlayer(new BloodDataSyncS2CPacket(blood.getBlood()), player);
                });

                Vec3 playerPos = player.getEyePosition(1.0F);
                Vec3 playerLook = player.getViewVector(1.0F);
                Vec3 playerLookPos = playerPos.add(playerLook.x * 10, playerLook.y * 10, playerLook.z * 10);
                BlockHitResult blockHitResult = level.clip(new ClipContext(playerPos, playerLookPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
                BlockPos blockPos = blockHitResult.getBlockPos();
                int x = blockPos.getX();
                int y = blockPos.getY();
                int z = blockPos.getZ();

                AtomicInteger i = new AtomicInteger();

                for (int j = 0; j < 10; j++) {
                    level.addParticle(ParticleTypes.WITCH, player.getX(), player.getY() + 1, player.getZ(), 0, 0, 0);
                }

                for (int j = 0; j < 10; j++) {
                    level.addParticle(ParticleTypes.WITCH, player.getX(), player.getY() + 1, player.getZ(), 0, 0, 0);
                }
            //} else {
            //    player.sendSystemMessage(Component.translatable("Not enough warden blood.").withStyle(ChatFormatting.RED));
            //    player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
            //        ModMessages.sendToPlayer(new BloodDataSyncS2CPacket(blood.getBlood()), player);
            //    });
            //}
        });
    }

    protected static BlockHitResult rayTrace(Level world, Player player, ClipContext.Fluid fluidMode) {
        double range = 15;

        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vector3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vector3d1 = vector3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return world.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.OUTLINE, fluidMode, player));
    }

    private boolean hasEnoughWardenBlood(ServerPlayer player) {
        AtomicInteger currentBlood = new AtomicInteger();

        player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
            currentBlood.set(blood.getBlood());
        });

        return currentBlood.get() >= 3;
    }
}
