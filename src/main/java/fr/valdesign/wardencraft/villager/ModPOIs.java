package fr.valdesign.wardencraft.villager;

import com.google.common.collect.ImmutableSet;
import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.block.ModBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPOIs {
    public static final DeferredRegister<PoiType> POI
            = DeferredRegister.create(ForgeRegistries.POI_TYPES, WardenCraft.MOD_ID);

    public static final RegistryObject<PoiType> WARDEN_PORTAL =
            POI.register("warden_portal", () -> new PoiType(
                    ImmutableSet.copyOf(ModBlocks.WARDEN_PORTAL.get().getStateDefinition().getPossibleStates())
                    , 0, 1));


    public static void register(IEventBus eventBus) {
        POI.register(eventBus);
    }
}