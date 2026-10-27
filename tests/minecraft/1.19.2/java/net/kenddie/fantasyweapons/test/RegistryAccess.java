package net.kenddie.fantasyweapons.test;

final class RegistryAccess {
    static java.util.stream.Stream<net.minecraft.resources.ResourceLocation> itemIds() {
        return net.minecraft.core.Registry.ITEM.keySet().stream();
    }
    private RegistryAccess() {}
}
