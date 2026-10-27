package net.kenddie.fantasyweapons.item;

import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class FWCreativeModTabs {
    public static final CreativeModeTab TAB = new CreativeModeTab(ModConstants.MOD_ID + ".fw_tab") {
        @Override public ItemStack makeIcon() { return new ItemStack(FWItems.get(WeaponCatalog.INSTANCE.tabIcon())); }
    };
    private FWCreativeModTabs() {}
}
