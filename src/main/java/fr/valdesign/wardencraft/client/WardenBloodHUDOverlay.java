package fr.valdesign.wardencraft.client;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.valdesign.wardencraft.WardenCraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class WardenBloodHUDOverlay {
    private static final ResourceLocation FILLED_WARDEN = new ResourceLocation(WardenCraft.MOD_ID,
            "textures/warden_blood/filled_warden_blood.png");
    private static final ResourceLocation EMPTY_WARDEN = new ResourceLocation(WardenCraft.MOD_ID,
            "textures/warden_blood/empty_warden_blood.png");

    public static final IGuiOverlay HUD_WARDEN_BLOOD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_WARDEN);
        for(int i = 0; i < 10; i++) {
            GuiComponent.blit(poseStack,x - 94 + (i * 8), height - 54,0,0,8,8,
                    8,8);
        }

        RenderSystem.setShaderTexture(0, FILLED_WARDEN);
        for(int i = 0; i < 10; i++) {
            if(ClientWardenBloodData.getPlayerWardenBlood() > i) {
                GuiComponent.blit(poseStack,x - 94 + (i * 8), height - 54,0,0,8,8,
                        8,8);
            } else break;
        }
    });
}
