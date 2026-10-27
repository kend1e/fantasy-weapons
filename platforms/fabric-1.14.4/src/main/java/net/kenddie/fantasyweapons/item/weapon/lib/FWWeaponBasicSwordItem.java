package net.kenddie.fantasyweapons.item.weapon.lib;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kenddie.fantasyweapons.config.WeaponSettings;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.SharedMonsterAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.function.Supplier;

public class FWWeaponBasicSwordItem extends SwordItem {
    private final Supplier<WeaponSettings> settings;
    private volatile AttributeSnapshot cached;

    public FWWeaponBasicSwordItem(Tier tier, Supplier<WeaponSettings> settings, Properties properties) {
        super(tier, 0, 0.0f, properties);
        this.settings = settings;
    }

    @Override public int getEnchantmentValue() { return 15; }

    @Override
    public float getDamage() {
        return (float) settings.get().damage();
    }

    @Override
    public Multimap<String, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot != EquipmentSlot.MAINHAND) return super.getDefaultAttributeModifiers(slot);
        WeaponSettings current = settings.get();
        AttributeSnapshot snapshot = cached;
        if (snapshot == null || !current.equals(snapshot.settings)) {
            Multimap<String, AttributeModifier> modifiers = ImmutableMultimap.<String, AttributeModifier>builder()
                    .put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID,
                            "Weapon modifier", current.damage(), AttributeModifier.Operation.ADDITION))
                    .put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(BASE_ATTACK_SPEED_UUID,
                            "Weapon modifier", current.speed(), AttributeModifier.Operation.ADDITION))
                    .build();
            snapshot = new AttributeSnapshot(current, modifiers);
            cached = snapshot;
        }
        return snapshot.modifiers;
    }
    private static final class AttributeSnapshot {
        final WeaponSettings settings;
        final Multimap<String, AttributeModifier> modifiers;
        AttributeSnapshot(WeaponSettings settings, Multimap<String, AttributeModifier> modifiers) {
            this.settings = settings;
            this.modifiers = modifiers;
        }
    }
}
