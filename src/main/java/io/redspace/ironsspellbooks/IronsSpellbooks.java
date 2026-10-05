package io.redspace.ironsspellbooks;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.slf4j.Logger;

/** Shared constants and state. Fabric lifecycle lives in the Marj4n port package. */
public final class IronsSpellbooks {
    public static final String MODID = "irons_spellbooks";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static MagicManager MAGIC_MANAGER;
    public static MinecraftServer MCS;
    public static ServerLevel OVERWORLD;

    private IronsSpellbooks() {}

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
