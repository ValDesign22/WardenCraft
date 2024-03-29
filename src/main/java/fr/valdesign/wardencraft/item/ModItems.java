package fr.valdesign.wardencraft.item;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.item.custom.ModArmorItem;
import fr.valdesign.wardencraft.item.custom.WardenKey;
import fr.valdesign.wardencraft.item.custom.WardianRelic;
import fr.valdesign.wardencraft.item.custom.WardianStaff;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WardenCraft.MOD_ID);

    public static final RegistryObject<Item> ECHO_NETHERITE_INGOT = ITEMS.register("echo_netherite_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.WARDENCRAFT_TAB)));

    public static final RegistryObject<Item> ECHO_STAR = ITEMS.register("echo_star",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.WARDENCRAFT_TAB)));

    public static final RegistryObject<Item> ECHO_BONE = ITEMS.register("echo_bone",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.WARDENCRAFT_TAB)));

    public static final RegistryObject<Item> WARDEN_HEART = ITEMS.register("warden_heart",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.WARDENCRAFT_TAB)));

    public static final RegistryObject<Item> WARDIAN_STAFF = ITEMS.register("wardian_staff", WardianStaff::new);

    public static final RegistryObject<Item> WARDEN_KEY = ITEMS.register("warden_key", WardenKey::new);
    public static final RegistryObject<Item> WARDIAN_RELIC = ITEMS.register("wardian_relic", WardianRelic::new);

    public static final RegistryObject<Item> ECHO_NETHERITE_HELMET = ITEMS.register("echo_netherite_helmet",
            () -> new ModArmorItem(ModArmorMaterials.ECHO_NETHERITE, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.WARDENCRAFT_TAB)));
    public static final RegistryObject<Item> ECHO_NETHERITE_CHESTPLATE = ITEMS.register("echo_netherite_chestplate",
            () -> new ArmorItem(ModArmorMaterials.ECHO_NETHERITE, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.WARDENCRAFT_TAB)));
    public static final RegistryObject<Item> ECHO_NETHERITE_LEGGING = ITEMS.register("echo_netherite_leggings",
            () -> new ArmorItem(ModArmorMaterials.ECHO_NETHERITE, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.WARDENCRAFT_TAB)));
    public static final RegistryObject<Item> ECHO_NETHERITE_BOOTS = ITEMS.register("echo_netherite_boots",
            () -> new ArmorItem(ModArmorMaterials.ECHO_NETHERITE, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.WARDENCRAFT_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
