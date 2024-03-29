package fr.valdesign.wardencraft.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_WARDENCRAFT = "key.category.wardencraft.wardencraft";
    public static final String KEY_DRINK_WARDEN_BLOOD = "key.wardencraft.drink_warden_blood";
    public static final String KEY_SEND_SONIC_BOOM = "key.wardencraft.send_sonic_boom";

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_WARDEN_BLOOD, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY_WARDENCRAFT);
    public static final KeyMapping SONIC_BOOM_KEY = new KeyMapping(KEY_SEND_SONIC_BOOM, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W, KEY_CATEGORY_WARDENCRAFT);
}
