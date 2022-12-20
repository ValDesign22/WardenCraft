package fr.valdesign.wardencraft.event;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.entities.ModEntities;
import fr.valdesign.wardencraft.entities.custom.WardianEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import fr.valdesign.wardencraft.event.loot.WardenLootModifier;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = WardenCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, helper -> {
            helper.register(new ResourceLocation(WardenCraft.MOD_ID,"warden_loot_modifier"),
                    WardenLootModifier.CODEC);
        });
    }

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WARDIAN.get(), WardianEntity.setAttributes());
    }
}
