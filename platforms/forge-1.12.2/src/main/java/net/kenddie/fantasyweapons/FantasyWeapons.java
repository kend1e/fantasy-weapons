package net.kenddie.fantasyweapons;

import net.kenddie.fantasyweapons.config.FWConfig;
import net.kenddie.fantasyweapons.shared.ModConstants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModConstants.MOD_ID, name = "Fantasy Weapons", useMetadata = true,
        acceptedMinecraftVersions = "[1.12.2,1.13)")
public final class FantasyWeapons {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FWConfig.load(event.getSuggestedConfigurationFile());
        FWConfig.registerNetwork();
    }
}
