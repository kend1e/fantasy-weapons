package net.kenddie.fantasyweapons.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kenddie.fantasyweapons.config.FWConfig;
import net.kenddie.fantasyweapons.config.WeaponSettings;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

public final class FWWeaponBasicSwordItem extends SwordItem {
    private final String id;
    private volatile AttributeSnapshot cached;

    public FWWeaponBasicSwordItem(String id, Item.Properties properties) {
        super(ItemTier.NETHERITE, 0, 0.0f, properties);
        this.id = id;
    }

    @Override public int getEnchantmentValue() { return 15; }
    @Override public float getDamage() { return (float) FWConfig.weapon(id).damage(); }

    @Override public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
        if (slot != EquipmentSlotType.MAINHAND) return super.getDefaultAttributeModifiers(slot);
        WeaponSettings settings = FWConfig.weapon(id);
        AttributeSnapshot snapshot = cached;
        if (snapshot == null || !settings.equals(snapshot.settings)) {
            Multimap<Attribute, AttributeModifier> modifiers = ImmutableMultimap.<Attribute, AttributeModifier>builder()
                    .put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                            BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", settings.damage(), AttributeModifier.Operation.ADDITION))
                    .put(Attributes.ATTACK_SPEED, new AttributeModifier(
                            BASE_ATTACK_SPEED_UUID, "Weapon modifier", settings.speed(), AttributeModifier.Operation.ADDITION)).build();
            snapshot = new AttributeSnapshot(settings, modifiers);
            cached = snapshot;
        }
        return snapshot.modifiers;
    }

    private static final class AttributeSnapshot {
        final WeaponSettings settings;
        final Multimap<Attribute, AttributeModifier> modifiers;
        AttributeSnapshot(WeaponSettings settings, Multimap<Attribute, AttributeModifier> modifiers) {
            this.settings = settings;
            this.modifiers = modifiers;
        }
    }
}
