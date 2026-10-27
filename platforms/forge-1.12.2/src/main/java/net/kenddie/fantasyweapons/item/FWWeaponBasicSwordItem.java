package net.kenddie.fantasyweapons.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kenddie.fantasyweapons.config.FWConfig;
import net.kenddie.fantasyweapons.config.WeaponSettings;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;

public final class FWWeaponBasicSwordItem extends ItemSword {
    private final String id;
    private volatile AttributeSnapshot cached;

    public FWWeaponBasicSwordItem(String id) {
        super(ToolMaterial.DIAMOND);
        this.id = id;
        setMaxDamage(2031);
    }

    @Override public int getItemEnchantability() { return 15; }
    @Override public float getAttackDamage() { return (float) FWConfig.weapon(id).damage(); }

    @Override public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
        if (slot != EntityEquipmentSlot.MAINHAND) return super.getItemAttributeModifiers(slot);
        WeaponSettings settings = FWConfig.weapon(id);
        AttributeSnapshot snapshot = cached;
        if (snapshot == null || !settings.equals(snapshot.settings)) {
            Multimap<String, AttributeModifier> modifiers = ImmutableMultimap.<String, AttributeModifier>builder()
                    .put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(
                            ATTACK_DAMAGE_MODIFIER, "Weapon modifier", settings.damage(), 0))
                    .put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(
                            ATTACK_SPEED_MODIFIER, "Weapon modifier", settings.speed(), 0)).build();
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
