package net.kenddie.fantasyweapons.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kenddie.fantasyweapons.config.FWConfig;
import net.kenddie.fantasyweapons.config.WeaponSettings;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

public final class FWWeaponBasicSwordItem extends SwordItem {
    private final String id;
    private volatile AttributeSnapshot cached;

    public FWWeaponBasicSwordItem(String id, Item.Properties properties) {
        super(ItemTier.DIAMOND, 0, 0.0f, properties);
        this.id = id;
    }

    @Override public int getEnchantmentValue() { return 15; }
    @Override public float getDamage() { return (float) FWConfig.weapon(id).damage(); }

    @Override public Multimap<String, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
        if (slot != EquipmentSlotType.MAINHAND) return super.getDefaultAttributeModifiers(slot);
        WeaponSettings settings = FWConfig.weapon(id);
        AttributeSnapshot snapshot = cached;
        if (snapshot == null || !settings.equals(snapshot.settings)) {
            Multimap<String, AttributeModifier> modifiers = ImmutableMultimap.<String, AttributeModifier>builder()
                    .put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(
                            BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", settings.damage(), AttributeModifier.Operation.ADDITION))
                    .put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(
                            BASE_ATTACK_SPEED_UUID, "Weapon modifier", settings.speed(), AttributeModifier.Operation.ADDITION)).build();
            snapshot = new AttributeSnapshot(settings, modifiers);
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
