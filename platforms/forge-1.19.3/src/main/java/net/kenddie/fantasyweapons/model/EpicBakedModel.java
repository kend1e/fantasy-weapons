package net.kenddie.fantasyweapons.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;

public final class EpicBakedModel extends WeaponBakedModel {
    public EpicBakedModel(BakedModel smallModel, BakedModel largeModel) { super(smallModel, largeModel); }

    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType context, PoseStack poses, boolean leftHand) {
        return modelFor(context).applyTransform(context, poses, leftHand);
    }
}
