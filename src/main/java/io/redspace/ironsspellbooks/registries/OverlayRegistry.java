package io.redspace.ironsspellbooks.registries;

public final class OverlayRegistry {
    private OverlayRegistry() {}
    public static void register() {
        net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback.EVENT.register(
                org.marj4n.irons_spellbooks.client.hud.NativeHud::topLayer);
    }
}
