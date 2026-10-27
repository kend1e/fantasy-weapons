package net.kenddie.fantasyweapons.item;

import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.List;

public final class FWItems {
    public static final ItemGroup TAB = new ItemGroup(ModConstants.CREATIVE_TAB_TITLE.substring("itemGroup.".length())) {
        @Override @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() { return new ItemStack(get(WeaponCatalog.INSTANCE.tabIcon())); }
    };
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModConstants.MOD_ID);

    static {
        for (String id : WeaponCatalog.INSTANCE.materials()) {
            ITEMS.register(id, () -> new Item(new Item.Properties().tab(TAB)) {
                @Override @OnlyIn(Dist.CLIENT)
                public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
                    super.appendHoverText(stack, world, tooltip, flag);
                    tooltip.add(new TranslationTextComponent(getDescriptionId() + ".tooltip").withStyle(TextFormatting.GRAY));
                }
            });
        }
        for (String id : WeaponCatalog.INSTANCE.weaponIds()) {
            ITEMS.register(id, () -> new FWWeaponBasicSwordItem(id, new Item.Properties().tab(TAB).durability(2031)));
        }
    }

    public static void register(IEventBus bus) { ITEMS.register(bus); }
    public static Item get(String id) { return ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModConstants.MOD_ID, id)); }
    private FWItems() {}
}
