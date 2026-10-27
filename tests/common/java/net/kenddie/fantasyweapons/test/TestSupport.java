package net.kenddie.fantasyweapons.test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.world.item.Items;
import net.kenddie.fantasyweapons.GameVersion;
import net.minecraft.world.item.Item;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

final class TestSupport {
    static final JsonObject EXPECTED = load();
    static Item item(String id) {
        var key = GameVersion.id(id.contains(":") ? id : "fantasy_weapons:" + id);
        var item = net.kenddie.fantasyweapons.item.FWItems.get(id);
        if (item == null || item == Items.AIR) throw new IllegalStateException("Missing item: " + key);
        return item;
    }
    static void near(double actual, double expected, String message) {
        if (!Double.isFinite(actual) || Math.abs(actual - expected) > 0.00001)
            throw new IllegalStateException(message + ": expected " + expected + ", got " + actual);
    }
    private static JsonObject load() {
        var stream = TestSupport.class.getResourceAsStream("/weapon_test_expectations.json");
        if (stream == null) throw new IllegalStateException("Missing expectations");
        try (var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (java.io.IOException e) { throw new IllegalStateException(e); }
    }
    private TestSupport() {}
}
