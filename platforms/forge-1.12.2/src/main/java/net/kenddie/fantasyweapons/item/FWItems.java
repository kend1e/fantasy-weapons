package net.kenddie.fantasyweapons.item;

import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID)
public final class FWItems {
    public static final CreativeTabs TAB = new CreativeTabs(ModConstants.MOD_ID + ".fw_tab") {
        @Override public ItemStack createIcon() { return new ItemStack(get(WeaponCatalog.INSTANCE.tabIcon())); }
        @Override @SideOnly(Side.CLIENT)
        public String getTranslationKey() { return ModConstants.CREATIVE_TAB_TITLE; }
    };

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        for (String id : WeaponCatalog.INSTANCE.materials()) {
            Item item = new Item() {
                @Override @SideOnly(Side.CLIENT)
                public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
                    super.addInformation(stack, world, tooltip, flag);
                    tooltip.add(TextFormatting.GRAY + I18n.format(getTranslationKey() + ".tooltip"));
                }
            };
            event.getRegistry().register(named(id, item));
        }
        for (String id : WeaponCatalog.INSTANCE.weaponIds()) {
            event.getRegistry().register(named(id, new FWWeaponBasicSwordItem(id)));
        }
    }

    private static Item named(String id, Item item) {
        return item.setRegistryName(new ResourceLocation(ModConstants.MOD_ID, id))
                .setTranslationKey(ModConstants.MOD_ID + "." + id).setCreativeTab(TAB);
    }

    public static Item get(String id) { return ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModConstants.MOD_ID, id)); }
    private FWItems() {}
}
