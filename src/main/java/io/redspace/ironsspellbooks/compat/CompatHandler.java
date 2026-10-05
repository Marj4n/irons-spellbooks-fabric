package io.redspace.ironsspellbooks.compat;

import net.fabricmc.loader.api.FabricLoader;

import java.util.Map;

public class CompatHandler {
    private static final Map<String, Runnable> MOD_MAP = Map.of(
            //TODO: 1.21: redo compat when mods port
//            "apotheosis", ApotheosisHandler::init
    );

    public static void init() {
        MOD_MAP.forEach((modid, supplier) -> {
            if (FabricLoader.getInstance().isModLoaded(modid)) {
                supplier.run();
            }
        });
    }
}
