package fr.valdesign.wardencraft.item;

import fr.valdesign.wardencraft.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab WARDENCRAFT_TAB = new CreativeModeTab("wardencrafttab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ECHO_NETHERITE_INGOT.get());
        }
    };
}
