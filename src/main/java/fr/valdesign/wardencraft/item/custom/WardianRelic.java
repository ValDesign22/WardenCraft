package fr.valdesign.wardencraft.item.custom;

import fr.valdesign.wardencraft.item.ModCreativeModeTab;
import fr.valdesign.wardencraft.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class WardianRelic extends Item {
    public WardianRelic() {
        super(new Properties()
                .tab(ModCreativeModeTab.WARDENCRAFT_TAB)
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .durability(1)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getPlayer() != null) {
            if(context.getPlayer().level.dimension() == ModDimensions.WARDENDIM_KEY) {
                BlockPos blockPos = context.getClickedPos();

                Block block = context.getLevel().getBlockState(blockPos).getBlock();

                context.getLevel().playSound(context.getPlayer(), blockPos,
                        SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
        return InteractionResult.FAIL;
    }
}
