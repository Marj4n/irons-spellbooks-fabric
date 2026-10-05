package org.marj4n.irons_spellbooks.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

/** Vanilla health/air offsets used to position the configurable mana bar. */
public final class HudContext {
    public int leftHeight = 39;
    public int rightHeight = 39;
    public Font getFont() { return Minecraft.getInstance().font; }
    public void beginFrame() {
        leftHeight = rightHeight = 39;
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        int heartRows = Mth.ceil((player.getMaxHealth() + player.getAbsorptionAmount()) / 20f);
        leftHeight += Math.max(0, heartRows - 1) * Math.max(10 - (heartRows - 2), 3) + 10;
        if (player.getArmorValue() > 0) leftHeight += 10;
        if (player.getVehicle() instanceof LivingEntity mount) {
            int hearts = Math.min(30, Mth.ceil(mount.getMaxHealth() / 2f));
            rightHeight += Mth.ceil(hearts / 10f) * 10;
        } else rightHeight += 10;
        if (player.isEyeInFluid(net.minecraft.tags.FluidTags.WATER)
                || player.getAirSupply() < player.getMaxAirSupply()) rightHeight += 10;
    }
}
