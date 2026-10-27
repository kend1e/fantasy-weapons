package net.kenddie.fantasyweapons.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;
import javax.vecmath.Matrix4f;
import java.util.List;

public final class WeaponBakedModel implements IBakedModel {
    private final IBakedModel small;
    private final IBakedModel large;

    public WeaponBakedModel(IBakedModel small, IBakedModel large) {
        this.small = small;
        this.large = large;
    }

    @Override public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type) {
        switch (type) {
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
                return large.handlePerspective(type);
            default:
                return small.handlePerspective(type);
        }
    }

    @Override public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long random) { return small.getQuads(state, side, random); }
    @Override public boolean isAmbientOcclusion() { return small.isAmbientOcclusion(); }
    @Override public boolean isGui3d() { return small.isGui3d(); }
    @Override public boolean isBuiltInRenderer() { return small.isBuiltInRenderer(); }
    @Override public TextureAtlasSprite getParticleTexture() { return small.getParticleTexture(); }
    @Override public ItemOverrideList getOverrides() { return small.getOverrides(); }
}
