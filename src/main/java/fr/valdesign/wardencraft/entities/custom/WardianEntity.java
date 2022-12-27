package fr.valdesign.wardencraft.entities.custom;

import com.google.common.annotations.VisibleForTesting;
import fr.valdesign.wardencraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Optional;

public class WardianEntity extends Monster implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS);

    public WardianEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.xpReward = 100;
        this.setHealth(this.getMaxHealth());

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000.0D)
                .add(Attributes.ATTACK_DAMAGE, 60.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0f).build();

    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, WitherBoss.class, false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Warden.class, false));
    }

    @Override
    public void onEnterCombat() {
        super.onEnterCombat();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float fl) {
        if (super.hurt(damageSource, fl)) {
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
            return true;
        } else {
            return false;
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isNoAi()) return PlayState.STOP;
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wardian.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wardian.idle", true));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent event) {
        if (this.isNoAi()) return PlayState.STOP;
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wardian.attack", true));
            this.swinging = false;
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }



    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.WARDEN_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.WARDEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WARDEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WARDEN_DEATH;
    }

    protected float getSoundVolume() {
        return 1F;
    }

    //@Override
    //    public boolean shouldListen(ServerLevel serverLevel, GameEventListener gameEventListener, BlockPos blockPos, GameEvent gameEvent, GameEvent.Context gameEventContext) {
    //        if (!this.isNoAi() && !this.isDeadOrDying() && !this.getBrain().hasMemoryValue(MemoryModuleType.VIBRATION_COOLDOWN) && serverLevel.getWorldBorder().isWithinBounds(blockPos) && !this.isRemoved() && this.level == serverLevel) {
    //            Entity entity = gameEventContext.sourceEntity();
    //            if (entity instanceof LivingEntity) {
    //                LivingEntity livingEntity = (LivingEntity)entity;
    //                return this.canTargetEntity(livingEntity);
    //            }
    //
    //            return true;
    //        } else {
    //            return false;
    //        }
    //    }

    //@Override
    //    public void onSignalReceive(ServerLevel serverLevel, GameEventListener gameEventListener, BlockPos blockPos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entityTwo, float v) {
    //        if (!this.isDeadOrDying()) {
    //            this.brain.setMemoryWithExpiry(MemoryModuleType.VIBRATION_COOLDOWN, Unit.INSTANCE, 40L);
    //            serverLevel.broadcastEntityEvent(this, (byte)61);
    //            this.playSound(SoundEvents.WARDEN_TENDRIL_CLICKS, 5.0F, this.getVoicePitch());
    //            BlockPos blockpos = blockPos;
    //            if (entityTwo != null) {
    //                if (this.closerThan(entityTwo, 30.0D)) {
    //                    if (this.getBrain().hasMemoryValue(MemoryModuleType.RECENT_PROJECTILE)) {
    //                        if (this.canTargetEntity(entityTwo)) {
    //                            blockpos = entityTwo.blockPosition();
    //                        }
    //
    //                        this.increaseAngerAt(entityTwo);
    //                    } else {
    //                        this.increaseAngerAt(entityTwo, 10, true);
    //                    }
    //                }
    //
    //                this.getBrain().setMemoryWithExpiry(MemoryModuleType.RECENT_PROJECTILE, Unit.INSTANCE, 100L);
    //            } else {
    //                this.increaseAngerAt(entity);
    //            }
    //
    //            if (!this.getAngerLevel().isAngry()) {
    //                Optional<LivingEntity> optional = this.angerManagement.getActiveEntity();
    //                if (entityTwo != null || optional.isEmpty() || optional.get() == entity) {
    //                    WardenAi.setDisturbanceLocation(this, blockpos);
    //                }
    //            }
    //
    //        }
    //    }
    //
    //    private AngerLevel getAngerLevel() {
    //        return AngerLevel.byAnger(this.getActiveAnger());
    //    }
    //
    //    private int getActiveAnger() {
    //        return this.angerManagement.getActiveAnger(this.getTarget());
    //    }
    //
    //    private void increaseAngerAt(@Nullable Entity entity) {
    //        this.increaseAngerAt(entity, 35, true);
    //    }
    //
    //    @VisibleForTesting
    //    private void increaseAngerAt(@Nullable Entity entity, int integer, boolean b) {
    //        if (!this.isNoAi() && this.canTargetEntity(entity) {
    //            boolean flag = !(this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse((LivingEntity)null) instanceof Player);
    //            int i = this.angerManagement.increaseAnger(entity, integer);
    //            if (entity instanceof Player && flag && AngerLevel.byAnger(i).isAngry()) {
    //                this.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
    //            }
    //
    //            if (b) {
    //                this.playListeningSound();
    //            }
    //        }
    //    }
    //
    //    private boolean canTargetEntity(Entity entity) {
    //    }

    public boolean canChangeDimensions() {
        return false;
    }
}
