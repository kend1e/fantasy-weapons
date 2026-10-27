package net.kenddie.fantasyweapons.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.GameVersion;

public final class FWCreativeModTabs {
    public static void register() {
        FWCreativeTabContents.configure(FabricItemGroup.builder(GameVersion.id(ModConstants.MOD_ID, "fw_tab"))).build();
    }

    private FWCreativeModTabs() {}
}
