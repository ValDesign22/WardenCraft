package fr.valdesign.wardencraft.entities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.entities.custom.WardianEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WardianRenderer extends GeoEntityRenderer<WardianEntity> {
    public WardianRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WardianModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(WardianEntity instance) {
        return new ResourceLocation(WardenCraft.MOD_ID, "textures/entity/wardian.png");
    }

    @Override
    public RenderType getRenderType(WardianEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8f, 0.8f, 0.8f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
