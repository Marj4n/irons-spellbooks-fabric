package org.marj4n.irons_spellbooks.client.hud;
import net.minecraft.client.gui.GuiGraphics;
@FunctionalInterface
public interface HudOverlay {
    void render(HudContext hud, GuiGraphics graphics, float partialTick, int width, int height);
}
