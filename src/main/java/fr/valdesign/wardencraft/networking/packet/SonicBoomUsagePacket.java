package fr.valdesign.wardencraft.networking.packet;

import fr.valdesign.wardencraft.blood.PlayerBloodProvider;
import fr.valdesign.wardencraft.item.ModItems;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

            if(hasEnoughWardenBlood(player)) {
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

                AtomicInteger radius = new AtomicInteger(3);
                for (int i = 0; i < 3; i++) {
                    level.getAllEntities().forEach(entity -> {
                        if (entity.getType() == EntityType.ITEM) return;

                        if (entity.getType() == EntityType.PLAYER) {
                            Iterable<ItemStack> armorSlots = entity.getArmorSlots();
                            int equiped = 0;

                            for (ItemStack armorSlot : armorSlots) {
                                if (armorSlot.getItem() == ModItems.ECHO_NETHERITE_HELMET.get()) equiped++;
                                if (armorSlot.getItem() == ModItems.ECHO_NETHERITE_CHESTPLATE.get()) equiped++;
                                if (armorSlot.getItem() == ModItems.ECHO_NETHERITE_LEGGING.get()) equiped++;
                                if (armorSlot.getItem() == ModItems.ECHO_NETHERITE_BOOTS.get()) equiped++;
                            }

                            if (equiped == 4) return;
                        }

                        if (entity.distanceToSqr(x, y, z) <= radius.get() * radius.get()) {
                            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.25, 0));
                            entity.hurtMarked = true;
                        }
                    });

                    for (int j = 0; j < 10; j++) {
                        int nextX = Mth.floor(x) + level.random.nextInt(radius.get() * 2) - radius.get();
                        int nextY = Mth.floor(y) + level.random.nextInt(radius.get() * 2) - radius.get();
                        int nextZ = Mth.floor(z) + level.random.nextInt(radius.get() * 2) - radius.get();

                        level.destroyBlock(new BlockPos(nextX, nextY, nextZ), false);

                        System.out.println("x: " + nextX + " y: " + nextY + " z: " + nextZ);

                        level.addParticle(ParticleTypes.EXPLOSION_EMITTER, nextX, nextY, nextZ, 0, 0, 0);
                    }

                    level.addParticle(ParticleTypes.SONIC_BOOM, x, y, z, 0, 0, 0);
                    level.playSound(null, x, y, z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                    radius.getAndIncrement();
                }
            } else {
                player.sendSystemMessage(Component.translatable("Not enough warden blood.").withStyle(ChatFormatting.RED));
                player.getCapability(PlayerBloodProvider.PLAYER_BLOOD).ifPresent(blood -> {
                    ModMessages.sendToPlayer(new BloodDataSyncS2CPacket(blood.getBlood()), player);
                });
            }
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
