package net.kenddie.fantasyweapons.mixin;

import net.kenddie.fantasyweapons.FantasyWeaponsClient;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    private BakedModel fantasyWeaponsHandModel(BakedModel model, ItemStack stack, ItemDisplayContext context,
            boolean leftHand, PoseStack poses, MultiBufferSource buffers, int light, int overlay, BakedModel original) {
        return FantasyWeaponsClient.modelFor(model, stack, context);
    }
}
