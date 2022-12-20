package fr.valdesign.wardencraft.item.custom;

import fr.valdesign.wardencraft.block.ModBlocks;
import fr.valdesign.wardencraft.block.custom.WardianBlock;
import fr.valdesign.wardencraft.item.ModCreativeModeTab;
import fr.valdesign.wardencraft.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;

public class WardianRelic extends Item {
    public WardianRelic() {
        super(new Properties()
                .tab(ModCreativeModeTab.WARDENCRAFT_TAB)
                .rarity(Rarity.EPIC)
                .stacksTo(1)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getPlayer() != null) {
            if(context.getPlayer().level.dimension() == ModDimensions.WARDENDIM_KEY) {
                BlockPos blockPos = context.getClickedPos();

                context.getLevel().playSound(context.getPlayer(), blockPos,
                        SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0f, 1.0f);

                if(context.getLevel().getBlockState(blockPos).getBlock() instanceof WardianBlock) {
                    if (((WardianBlock) ModBlocks.WARDIAN_BLOCK.get()).trySpawnWardian(context.getLevel(), blockPos)) {
                        return InteractionResult.CONSUME;
                    }
                    else return InteractionResult.FAIL;
                }
                else return InteractionResult.FAIL;
            }
        }
        return InteractionResult.FAIL;
    }
}
