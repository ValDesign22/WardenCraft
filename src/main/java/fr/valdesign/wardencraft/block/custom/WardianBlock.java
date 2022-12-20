package fr.valdesign.wardencraft.block.custom;

import fr.valdesign.wardencraft.entities.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class WardianBlock extends Block {
    public WardianBlock() {
        super(Properties.of(Material.STONE)
                .strength(6F)
                .noLootTable()
        );
    }

    public boolean trySpawnWardian(LevelAccessor level, BlockPos pos) {
        level.removeBlock(pos, false);

        if(level.getBlockState(pos).isAir()) {
            Entity wardian = ModEntities.WARDIAN.get().create((Level) level);

            assert wardian != null;

            wardian.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            level.addFreshEntity(wardian);
            return true;
        }
        else return false;
    }
}
