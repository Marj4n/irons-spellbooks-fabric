package org.marj4n.irons_spellbooks.client.hud;

import io.redspace.ironsspellbooks.gui.overlays.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public final class NativeHud {
    private static final HudContext CONTEXT = new HudContext();
    private static float partialTick;
    private NativeHud() {}
    public static void beginFrame(float tick) {
        partialTick = tick;
        CONTEXT.beginFrame();
        RecastOverlay.instance.setBossbarOffset(0);
    }
    private static void render(HudOverlay overlay, GuiGraphics graphics) {
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().options.hideGui) return;
        overlay.render(CONTEXT, graphics, partialTick, graphics.guiWidth(), graphics.guiHeight());
    }
    public static void castBar(GuiGraphics graphics) { render(CastBarOverlay.instance, graphics); }
    public static void mana(GuiGraphics graphics) { render(ManaBarOverlay.instance, graphics); }
    public static void experienceLayer(GuiGraphics graphics) {
        render(SpellBarOverlay.instance, graphics);
        render(RecastOverlay.instance, graphics);
    }
    public static void topLayer(GuiGraphics graphics, float tick) {
        partialTick = tick;
        render(SpellWheelOverlay.instance, graphics);
        render(ScreenEffectsOverlay.instance, graphics);
        render(ScreenTooltipOverlay.instance, graphics);
    }
}
