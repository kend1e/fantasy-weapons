package net.kenddie.fantasyweapons.shared.content;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class WeaponCatalog {
    public static final WeaponCatalog INSTANCE = load();

    private final String tabIcon;
    private final List<String> materials;
    private final List<Weapon> weapons;

    public WeaponCatalog(String tabIcon, List<String> materials, List<Weapon> weapons) {
        this.tabIcon = Objects.requireNonNull(tabIcon);
        this.materials = immutableCopy(materials);
        this.weapons = immutableCopy(weapons);
        List<String> ids = itemIds();
        if (ids.size() != ids.stream().distinct().count() || !ids.contains(tabIcon)) {
            throw new IllegalArgumentException("Invalid weapon catalog IDs or creative tab icon");
        }
    }

    public String tabIcon() { return tabIcon; }
    public List<String> materials() { return materials; }
    public List<Weapon> weapons() { return weapons; }

    public List<String> itemIds() {
        return Collections.unmodifiableList(Stream.concat(materials.stream(), weapons.stream().map(Weapon::id))
                .collect(Collectors.toList()));
    }

    public List<String> weaponIds() {
        return Collections.unmodifiableList(weapons.stream().map(Weapon::id).collect(Collectors.toList()));
    }

    private static <T> List<T> immutableCopy(List<T> values) {
        List<T> copy = new ArrayList<>(values);
        copy.forEach(Objects::requireNonNull);
        return Collections.unmodifiableList(copy);
    }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof WeaponCatalog)) return false;
        WeaponCatalog catalog = (WeaponCatalog) other;
        return tabIcon.equals(catalog.tabIcon) && materials.equals(catalog.materials) && weapons.equals(catalog.weapons);
    }

    @Override public int hashCode() { return Objects.hash(tabIcon, materials, weapons); }

    @Override public String toString() {
        return "WeaponCatalog[tabIcon=" + tabIcon + ", materials=" + materials + ", weapons=" + weapons + "]";
    }

    public static final class Weapon {
        private final String id;
        private final boolean largeModel;

        public Weapon(String id, boolean largeModel) {
            this.id = id;
            this.largeModel = largeModel;
        }

        public String id() { return id; }
        public boolean largeModel() { return largeModel; }

        @Override public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof Weapon)) return false;
            Weapon weapon = (Weapon) other;
            return Objects.equals(id, weapon.id) && largeModel == weapon.largeModel;
        }

        @Override public int hashCode() { return Objects.hash(id, largeModel); }

        @Override public String toString() {
            return "Weapon[id=" + id + ", largeModel=" + largeModel + "]";
        }
    }

    private static WeaponCatalog load() {
        InputStream stream = WeaponCatalog.class.getResourceAsStream("/weapon_catalog.json");
        if (stream == null) throw new IllegalStateException("Missing generated weapon catalog");
        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            CatalogData data = new Gson().fromJson(reader, CatalogData.class);
            if (data == null) throw new IllegalStateException("Empty generated weapon catalog");
            return new WeaponCatalog(data.tabIcon, data.materials, data.weapons);
        } catch (java.io.IOException exception) {
            throw new IllegalStateException("Cannot read generated weapon catalog", exception);
        }
    }

    private static final class CatalogData {
        String tabIcon;
        List<String> materials;
        List<Weapon> weapons;
    }
}
