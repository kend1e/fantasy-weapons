package net.kenddie.fantasyweapons.client;

import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FantasyWeaponsClient {
    @SubscribeEvent
    public static void register(ModelRegistryEvent event) {
        for (WeaponCatalog.Weapon weapon : WeaponCatalog.INSTANCE.weapons()) {
            if (weapon.largeModel()) ModelLoader.addSpecialModel(large(weapon.id()));
        }
    }

    @SubscribeEvent
    public static void bake(ModelBakeEvent event) {
        for (WeaponCatalog.Weapon weapon : WeaponCatalog.INSTANCE.weapons()) {
            if (!weapon.largeModel()) continue;
            ModelResourceLocation id = new ModelResourceLocation(new ResourceLocation(ModConstants.MOD_ID, weapon.id()), "inventory");
            IBakedModel small = event.getModelRegistry().get(id);
            IBakedModel large = event.getModelRegistry().get(large(weapon.id()));
            if (small == null || large == null) throw new IllegalStateException("Missing weapon model: " + weapon.id());
            event.getModelRegistry().put(id, new WeaponBakedModel(small, large));
        }
    }

    private static ResourceLocation large(String id) { return new ResourceLocation(ModConstants.MOD_ID, "item/" + id + "_large"); }
    private FantasyWeaponsClient() {}
}
