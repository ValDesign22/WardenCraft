package fr.valdesign.wardencraft.entities;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.entities.custom.WardianEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WardenCraft.MOD_ID);

    public static final RegistryObject<EntityType<WardianEntity>> WARDIAN =
            ENTITY_TYPES.register("wardian",
                    () -> EntityType.Builder.of(WardianEntity::new, MobCategory.MONSTER)
                            .sized(0.4f, 1.5f)
                            .build(new ResourceLocation(WardenCraft.MOD_ID, "wardian").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
