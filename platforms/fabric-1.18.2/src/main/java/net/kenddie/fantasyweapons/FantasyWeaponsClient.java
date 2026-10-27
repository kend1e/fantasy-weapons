package net.kenddie.fantasyweapons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.kenddie.fantasyweapons.item.FWItems;
import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class FantasyWeaponsClient implements ClientModInitializer {
    private static Map<Item, ModelResourceLocation> handModels = Collections.emptyMap();

    @Override public void onInitializeClient() {
        Map<Item, ModelResourceLocation> models = new HashMap<>();
        for (WeaponCatalog.Weapon weapon : WeaponCatalog.INSTANCE.weapons()) {
            if (weapon.largeModel()) models.put(FWItems.get(weapon.id()), new ModelResourceLocation(
                    new ResourceLocation(ModConstants.MOD_ID, weapon.id() + "_large"), "inventory"));
        }
        handModels = Collections.unmodifiableMap(models);
        ModelLoadingRegistry.INSTANCE.registerAppender((resources, consumer) -> models.values().forEach(consumer));
    }

    public static BakedModel modelFor(BakedModel original, ItemStack stack, ItemTransforms.TransformType context) {
        switch (context) {
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
                ModelResourceLocation id = handModels.get(stack.getItem());
                return id == null ? original : Minecraft.getInstance().getModelManager().getModel(id);
            default:
                return original;
        }
    }
}
