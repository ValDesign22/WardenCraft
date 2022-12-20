package fr.valdesign.wardencraft.entities.client;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.entities.custom.WardianEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WardianModel extends AnimatedGeoModel<WardianEntity> {
    @Override
    public ResourceLocation getModelResource(WardianEntity object) {
        return new ResourceLocation(WardenCraft.MOD_ID, "geo/wardian.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WardianEntity object) {
        return new ResourceLocation(WardenCraft.MOD_ID, "textures/entity/wardian.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WardianEntity animatable) {
        return new ResourceLocation(WardenCraft.MOD_ID, "animations/wardian.animation.json");
    }
}
