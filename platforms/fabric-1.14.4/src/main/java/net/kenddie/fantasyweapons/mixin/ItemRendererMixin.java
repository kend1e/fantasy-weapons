package net.kenddie.fantasyweapons.mixin;

import net.kenddie.fantasyweapons.FantasyWeaponsClient;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;Z)V",
            at = @At("HEAD"), argsOnly = true)
    private BakedModel fantasyWeaponsHandModel(BakedModel model, ItemStack stack, BakedModel original,
            ItemTransforms.TransformType context, boolean leftHand) {
        return FantasyWeaponsClient.modelFor(model, stack, context);
    }
}
