package fr.valdesign.wardencraft.item.custom;

import fr.valdesign.wardencraft.enchantment.ModEnchantments;
import fr.valdesign.wardencraft.item.ModCreativeModeTab;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WardianStaff extends Item {
        public WardianStaff() {
            super(new Item.Properties()
                    .tab(ModCreativeModeTab.WARDENCRAFT_TAB)
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
            );
        }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            int enchantLvl = this.getEnchantmentLevel(player.getItemInHand(hand), ModEnchantments.ECHOLOCATION.get());
            echolocate(level, player, enchantLvl);
            player.getCooldowns().addCooldown(this, 400);
        }

        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("This wand can be used to echolocate mobs and players.").withStyle(ChatFormatting.DARK_AQUA));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.WHITE));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    public void echolocate(Level level, Player player, int enchantLvl) {
        int range = 10;
        int duration = 100;

        if(enchantLvl > 0) {
            range += enchantLvl * 5;
            duration += enchantLvl * 100;
        }

        int rangeSquared = range * range;
        int pX = (int) player.getX();
        int pY = (int) player.getY();
        int pZ = (int) player.getZ();

        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(range))) {
            int eX = (int) e.getX();
            int eY = (int) e.getY();
            int eZ = (int) e.getZ();

            if (pX - eX <= range && pY - eY <= range && pZ - eZ <= range) {
                if (pX - eX >= -range && pY - eY >= -range && pZ - eZ >= -range) {
                    if (pX - eX <= rangeSquared && pY - eY <= rangeSquared && pZ - eZ <= rangeSquared) {
                        if (pX - eX >= -rangeSquared && pY - eY >= -rangeSquared && pZ - eZ >= -rangeSquared) {
                            if (!e.isDeadOrDying() && e.getUUID() != player.getUUID()) {
                                e.addEffect(new MobEffectInstance(MobEffects.GLOWING, duration, 0, false, false));
                                e.addEffect(new MobEffectInstance(MobEffects.DARKNESS, duration, 0, false, false));
                            }
                        }
                    }
                }
            }
        }
    }
}
