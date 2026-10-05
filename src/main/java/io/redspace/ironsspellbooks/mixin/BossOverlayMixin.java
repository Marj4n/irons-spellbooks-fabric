package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.api.util.BossbarManager;
import io.redspace.ironsspellbooks.gui.overlays.RecastOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public abstract class BossOverlayMixin {
    @Shadow @Final private java.util.Map<java.util.UUID,LerpingBossEvent> events;
    @Shadow private void drawBar(GuiGraphics graphics, int x, int y, BossEvent event) { throw new AssertionError("Mixin shadow"); }
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void irons_spellbooks$renderBars(GuiGraphics graphics, CallbackInfo ci) {
        int y = 12;
        var font = Minecraft.getInstance().font;
        for (var event : events.values()) {
            int x = graphics.guiWidth()/2 - 91;
            if (!BossbarManager.renderCustomBossbar(graphics, x, y, event)) drawBar(graphics,x,y,event);
            graphics.drawString(font,event.getName(),graphics.guiWidth()/2-font.width(event.getName())/2,y-9,0xFFFFFF);
            y += BossbarManager.getIncrement(event);
            if (y >= graphics.guiHeight()/3) break;
        }
        RecastOverlay.instance.setBossbarOffset(y - 12);
        ci.cancel();
    }
}
