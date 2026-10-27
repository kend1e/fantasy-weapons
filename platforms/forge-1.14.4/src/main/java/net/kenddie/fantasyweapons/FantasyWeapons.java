package net.kenddie.fantasyweapons;

import net.kenddie.fantasyweapons.config.FWConfig;
import net.kenddie.fantasyweapons.item.FWItems;
import net.kenddie.fantasyweapons.shared.ModConstants;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModConstants.MOD_ID)
public final class FantasyWeapons {
    public FantasyWeapons() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, FWConfig.SPEC);
        FWItems.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
