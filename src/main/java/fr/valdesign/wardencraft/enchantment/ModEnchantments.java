package fr.valdesign.wardencraft.enchantment;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.enchantment.custom.EcholocationEnchantment;
import fr.valdesign.wardencraft.item.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, WardenCraft.MOD_ID);


    static EnchantmentCategory STAFF_CAT = EnchantmentCategory.create("wardian_staff", (item) -> item == ModItems.WARDIAN_STAFF.get());
    public static RegistryObject<Enchantment> ECHOLOCATION = ENCHANTMENTS.register("echolocation",
            () -> new EcholocationEnchantment(Enchantment.Rarity.RARE,
                    STAFF_CAT, EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
