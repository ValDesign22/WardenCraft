package fr.valdesign.mcupdate.item;

import fr.valdesign.mcupdate.MCUpdate;
import fr.valdesign.mcupdate.item.custom.ModArmorItem;
import fr.valdesign.mcupdate.item.custom.ModCreativeModTab;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MCUpdate.MOD_ID);

    // ITEMS
    public static final RegistryObject<Item> ECHO_NETHERITE_INGOT = ITEMS.register("echo_netherite_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.MCUPDATE_TAB)));

    // ARMORS
    public static final RegistryObject<Item> ECHO_NETHERITE_HELMET = ITEMS.register("echo_netherite_helmet",
            () -> new ModArmorItem(ModArmorMaterials.ECHO_NETHERITE, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModTab.MCUPDATE_TAB)));
    public static final RegistryObject<Item> ECHO_NETHERITE_CHESTPLATE = ITEMS.register("echo_netherite_chestplate",
            () -> new ArmorItem(ModArmorMaterials.ECHO_NETHERITE, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModTab.MCUPDATE_TAB)));
    public static final RegistryObject<Item> ECHO_NETHERITE_LEGGING = ITEMS.register("echo_netherite_leggings",
            () -> new ArmorItem(ModArmorMaterials.ECHO_NETHERITE, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModTab.MCUPDATE_TAB)));
    public static final RegistryObject<Item> ECHO_NETHERITE_BOOTS = ITEMS.register("echo_netherite_boots",
            () -> new ArmorItem(ModArmorMaterials.ECHO_NETHERITE, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModTab.MCUPDATE_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
