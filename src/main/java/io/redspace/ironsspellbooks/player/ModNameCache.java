package io.redspace.ironsspellbooks.player;


import java.util.HashMap;
import java.util.Optional;

public class ModNameCache {
    public static final HashMap<String, String> MOD_NAME_LOOKUP_CACHE = new HashMap<>();

    public static Optional<String> getModName(String modid) {
        if (!MOD_NAME_LOOKUP_CACHE.containsKey(modid)) {
            // force null entries, preventing repeated cache misses, which computeIfAbsent does not provide
            MOD_NAME_LOOKUP_CACHE.put(modid, net.fabricmc.loader.api.FabricLoader.getInstance().getModContainer(modid)
                    .map(mod -> mod.getMetadata().getName())
                    .orElse(null));
        }
        return Optional.ofNullable(MOD_NAME_LOOKUP_CACHE.get(modid));
    }
}
