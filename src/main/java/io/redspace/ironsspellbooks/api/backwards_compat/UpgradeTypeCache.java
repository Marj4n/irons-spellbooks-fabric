package io.redspace.ironsspellbooks.api.backwards_compat;

import io.redspace.ironsspellbooks.item.armor.UpgradeOrbType;
import io.redspace.ironsspellbooks.registries.UpgradeOrbTypeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.List;

public class UpgradeTypeCache {
    public final static HashMap<ResourceKey<UpgradeOrbType>, Holder<UpgradeOrbType>> CACHE = new HashMap<>();

    public static void onClientLoad(RegistryAccess registryAccess, List<ResourceKey<UpgradeOrbType>> types) {
        CACHE.clear();
        for (ResourceKey<UpgradeOrbType> type : types) {
            CACHE.put(type, UpgradeOrbTypeRegistry.upgradeTypeRegistry(registryAccess).getHolderOrThrow(type));
        }
    }


    public static void doCache(RegistryAccess registryAccess) {
        CACHE.clear();
        Registry<UpgradeOrbType> registry = UpgradeOrbTypeRegistry.upgradeTypeRegistry(registryAccess);
        for (var entry : registry.entrySet()) {
            CACHE.put(entry.getKey(), registry.wrapAsHolder(entry.getValue()));
        }
    }

    public static void onServerStart(net.minecraft.server.MinecraftServer server) {
        doCache(server.registryAccess());
    }

    public static void onDatapackLoad(net.minecraft.server.level.ServerPlayer player, boolean joined) {
        if (!joined || CACHE.isEmpty()) doCache(player.server.registryAccess());
    }
}
