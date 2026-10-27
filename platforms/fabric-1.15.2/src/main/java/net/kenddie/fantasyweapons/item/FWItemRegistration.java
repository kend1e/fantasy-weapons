package net.kenddie.fantasyweapons.item;

import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.world.item.Item;
import net.minecraft.core.Registry;

import net.kenddie.fantasyweapons.GameVersion;

public final class FWItemRegistration {
    public static void register() {
        WeaponCatalog catalog = WeaponCatalog.INSTANCE;
        catalog.materials().forEach(id -> Registry.register(Registry.ITEM, GameVersion.id(ModConstants.MOD_ID, id), FWItems.material(new Item.Properties().tab(FWCreativeModTabs.TAB))));
        catalog.weapons().forEach(weapon -> Registry.register(Registry.ITEM, GameVersion.id(ModConstants.MOD_ID, weapon.id()), FWItems.weapon(weapon.id(), new Item.Properties().tab(FWCreativeModTabs.TAB).durability(2031))));
    }
    private FWItemRegistration() {}
}
