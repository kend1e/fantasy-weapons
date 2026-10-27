package net.kenddie.fantasyweapons.config;

import io.netty.buffer.ByteBuf;
import net.kenddie.fantasyweapons.shared.ModConstants;
import net.kenddie.fantasyweapons.shared.content.WeaponCatalog;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID)
public final class FWConfig {
    private static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(ModConstants.MOD_ID);
    private static Map<String, WeaponSettings> local = Collections.emptyMap();
    private static volatile Map<String, WeaponSettings> active = Collections.emptyMap();

    public static void load(File file) {
        Configuration config = new Configuration(file);
        Map<String, WeaponSettings> values = new LinkedHashMap<>();
        for (String id : WeaponCatalog.INSTANCE.weaponIds()) {
            String category = "weapons." + id;
            double damage = config.get(category, "damage", WeaponSettings.DEFAULT.damage(),
                    "Attack damage modifier; added to the entity's base damage.",
                    WeaponSettings.MIN_DAMAGE, WeaponSettings.MAX_DAMAGE).getDouble();
            double speed = config.get(category, "speed", WeaponSettings.DEFAULT.speed(),
                    "Attack speed modifier; added to the entity's base attack speed.",
                    WeaponSettings.MIN_SPEED, WeaponSettings.MAX_SPEED).getDouble();
            if (!WeaponSettings.validDamage(damage)) {
                damage = WeaponSettings.DEFAULT.damage();
                config.get(category, "damage", damage).set(damage);
            }
            if (!WeaponSettings.validSpeed(speed)) {
                speed = WeaponSettings.DEFAULT.speed();
                config.get(category, "speed", speed).set(speed);
            }
            values.put(id, new WeaponSettings(damage, speed));
        }
        if (config.hasChanged()) config.save();
        local = Collections.unmodifiableMap(values);
        active = local;
    }

    public static WeaponSettings weapon(String id) { return active.getOrDefault(id, WeaponSettings.DEFAULT); }

    public static void registerNetwork() {
        NETWORK.registerMessage(SettingsHandler.class, SettingsPacket.class, 0, Side.CLIENT);
    }

    @SubscribeEvent
    public static void login(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) NETWORK.sendTo(new SettingsPacket(local), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public static void disconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        FMLCommonHandler.instance().getWorldThread(event.getHandler()).addScheduledTask(() -> active = local);
    }

    public static final class SettingsPacket implements IMessage {
        private Map<String, WeaponSettings> values;
        public SettingsPacket() {}
        private SettingsPacket(Map<String, WeaponSettings> values) { this.values = values; }

        @Override public void fromBytes(ByteBuf buffer) {
            int count = buffer.readInt();
            if (count != WeaponCatalog.INSTANCE.weaponIds().size()) throw new IllegalArgumentException("Incomplete weapon settings");
            Map<String, WeaponSettings> decoded = new LinkedHashMap<>();
            for (int i = 0; i < count; i++) {
                String id = ByteBufUtils.readUTF8String(buffer);
                if (!WeaponCatalog.INSTANCE.weaponIds().contains(id) || decoded.containsKey(id)) {
                    throw new IllegalArgumentException("Unknown or duplicate weapon: " + id);
                }
                decoded.put(id, new WeaponSettings(buffer.readDouble(), buffer.readDouble()));
            }
            values = Collections.unmodifiableMap(decoded);
        }

        @Override public void toBytes(ByteBuf buffer) {
            buffer.writeInt(values.size());
            values.forEach((id, settings) -> {
                ByteBufUtils.writeUTF8String(buffer, id);
                buffer.writeDouble(settings.damage());
                buffer.writeDouble(settings.speed());
            });
        }
    }

    public static final class SettingsHandler implements IMessageHandler<SettingsPacket, IMessage> {
        @Override public IMessage onMessage(SettingsPacket message, MessageContext context) {
            FMLCommonHandler.instance().getWorldThread(context.netHandler).addScheduledTask(() -> active = message.values);
            return null;
        }
    }

    private FWConfig() {}
}
