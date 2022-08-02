package fr.valdesign.wardencraft.item.custom;

import fr.valdesign.wardencraft.block.ModBlocks;
import fr.valdesign.wardencraft.block.custom.WardenDimPortalBlock;
import fr.valdesign.wardencraft.item.ModCreativeModeTab;
import fr.valdesign.wardencraft.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class WardenKey extends Item {
    public WardenKey()
    {
        super(new Properties()
                .tab(ModCreativeModeTab.WARDENCRAFT_TAB)
                .stacksTo(1)
                .rarity(Rarity.RARE)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        if(context.getPlayer() != null)
        {
            if(context.getPlayer().level.dimension() == ModDimensions.WARDENDIM_KEY
            || context.getPlayer().level.dimension() == Level.OVERWORLD)
            {
                for (Direction direction : Direction.Plane.VERTICAL)
                {
                    BlockPos framePos = context.getClickedPos().relative(direction);
                    if(((WardenDimPortalBlock) ModBlocks.WARDEN_PORTAL.get()).trySpawnPortal(context.getLevel(), framePos))
                    {
                        context.getLevel().playSound(context.getPlayer(), framePos,
                                SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0f, 1.0f);
                        return InteractionResult.CONSUME;
                    }
                    else return InteractionResult.FAIL;
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
