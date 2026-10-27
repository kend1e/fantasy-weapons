package net.kenddie.fantasyweapons.client;

import net.kenddie.fantasyweapons.item.FWItems;
import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID, value = Side.CLIENT)
public final class FantasyWeaponsClient {
    @SubscribeEvent
    public static void register(ModelRegistryEvent event) {
        for (String id : WeaponCatalog.INSTANCE.itemIds()) {
            ModelLoader.setCustomModelResourceLocation(FWItems.get(id), 0, model(id));
        }
        for (WeaponCatalog.Weapon weapon : WeaponCatalog.INSTANCE.weapons()) {
            if (weapon.largeModel()) {
                ModelBakery.registerItemVariants(FWItems.get(weapon.id()), new ResourceLocation(ModConstants.MOD_ID, weapon.id() + "_large"));
            }
        }
    }

    @SubscribeEvent
    public static void bake(ModelBakeEvent event) {
        for (WeaponCatalog.Weapon weapon : WeaponCatalog.INSTANCE.weapons()) {
            if (!weapon.largeModel()) continue;
            ModelResourceLocation id = model(weapon.id());
            IBakedModel small = event.getModelRegistry().getObject(id);
            IBakedModel large = event.getModelRegistry().getObject(model(weapon.id() + "_large"));
            if (small == null || large == null) throw new IllegalStateException("Missing weapon model: " + weapon.id());
            event.getModelRegistry().putObject(id, new WeaponBakedModel(small, large));
        }
    }

    private static ModelResourceLocation model(String id) {
        return new ModelResourceLocation(new ResourceLocation(ModConstants.MOD_ID, id), "inventory");
    }
    private FantasyWeaponsClient() {}
}
