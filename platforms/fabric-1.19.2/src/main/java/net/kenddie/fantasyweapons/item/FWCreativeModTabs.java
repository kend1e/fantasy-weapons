package net.kenddie.fantasyweapons.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.kenddie.fantasyweapons.GameVersion;
import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class FWCreativeModTabs {
    public static CreativeModeTab TAB;
    public static void register() {
        TAB = FabricItemGroupBuilder.create(GameVersion.id(ModConstants.MOD_ID, "fw_tab"))
                .icon(() -> new ItemStack(FWItems.get(WeaponCatalog.INSTANCE.tabIcon())))
                .build();
    }
    private FWCreativeModTabs() {}
}
