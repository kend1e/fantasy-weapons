package net.kenddie.fantasyweapons.shared.content;

import org.junit.Test;
import java.util.Collections;
import static org.junit.Assert.*;

public class WeaponCatalogTest {
    @Test public void loadsCatalogWithOptionalLargeModels() {
        WeaponCatalog catalog = WeaponCatalog.INSTANCE;
        assertTrue(catalog.materials().contains("blazing_stone"));
        assertTrue(catalog.weapons().stream().anyMatch(weapon -> weapon.id().equals("weapon_savage_sword") && weapon.largeModel()));
        assertTrue(catalog.weapons().stream().anyMatch(weapon -> weapon.id().equals("weapon_wakizashi") && !weapon.largeModel()));
        assertTrue(catalog.itemIds().contains(catalog.tabIcon()));
    }

    @Test public void rejectsDuplicateIdsAndUnknownIcon() {
        assertThrows(IllegalArgumentException.class, () -> new WeaponCatalog("a", Collections.singletonList("a"), Collections.singletonList(new WeaponCatalog.Weapon("a", true))));
        assertThrows(IllegalArgumentException.class, () -> new WeaponCatalog("missing", Collections.singletonList("a"), Collections.emptyList()));
    }

    @Test public void exposesImmutableCatalogData() {
        assertThrows(UnsupportedOperationException.class, () -> WeaponCatalog.INSTANCE.materials().add("other"));
        assertThrows(UnsupportedOperationException.class, () -> WeaponCatalog.INSTANCE.weapons().clear());
    }
}
