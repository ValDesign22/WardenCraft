package fr.valdesign.wardencraft.world.feature;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, WardenCraft.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> WARDENDIM_ECHO_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_ECHO_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_ECHO_ORE.get().defaultBlockState())
    ));

    public static final RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_ECHO_ORE = CONFIGURED_FEATURE.register("deepslate_echo_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(WARDENDIM_ECHO_ORES.get(), 7)));

    public static void register(IEventBus eventBus)
    {
        CONFIGURED_FEATURE.register(eventBus);
    }
}
