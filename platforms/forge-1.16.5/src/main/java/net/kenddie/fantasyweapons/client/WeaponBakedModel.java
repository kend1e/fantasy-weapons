package net.kenddie.fantasyweapons.client;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import java.util.Random;

public final class WeaponBakedModel implements IBakedModel {
    private final IBakedModel small;
    private final IBakedModel large;

    public WeaponBakedModel(IBakedModel small, IBakedModel large) {
        this.small = small;
        this.large = large;
    }

    @Override public boolean doesHandlePerspectives() { return true; }

    @Override public IBakedModel handlePerspective(ItemCameraTransforms.TransformType type, MatrixStack poses) {
        switch (type) {
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
                return large.handlePerspective(type, poses);
            default:
                return small.handlePerspective(type, poses);
        }
    }

    @Override public List<BakedQuad> getQuads(BlockState state, Direction side, Random random) { return small.getQuads(state, side, random); }
    @Override public boolean useAmbientOcclusion() { return small.useAmbientOcclusion(); }
    @Override public boolean usesBlockLight() { return small.usesBlockLight(); }
    @Override public boolean isGui3d() { return small.isGui3d(); }
    @Override public boolean isCustomRenderer() { return small.isCustomRenderer(); }
    @Override public TextureAtlasSprite getParticleIcon() { return small.getParticleIcon(); }
    @Override public ItemOverrideList getOverrides() { return small.getOverrides(); }
}
