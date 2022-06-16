package fr.valdesign.mcupdate.item.custom;

import fr.valdesign.mcupdate.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTab {
    public static final CreativeModeTab MCUPDATE_TAB = new CreativeModeTab("mcupdatetab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ECHO_NETHERITE_INGOT.get());
        }
    };
}
