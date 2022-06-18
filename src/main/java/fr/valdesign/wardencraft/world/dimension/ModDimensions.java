package fr.valdesign.wardencraft.world.dimension;

import fr.valdesign.wardencraft.WardenCraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    public static final ResourceKey<Level> WARDENDIM_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(WardenCraft.MOD_ID, "wardendim"));
    public static final ResourceKey<DimensionType> WARDENDIM_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY,
            WARDENDIM_KEY.registry());

    public static void register() {
        System.out.println("Registering ModDimensions for " + WardenCraft.MOD_ID);
    }
}
