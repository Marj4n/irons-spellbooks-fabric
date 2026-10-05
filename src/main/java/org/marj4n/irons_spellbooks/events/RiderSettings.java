package org.marj4n.irons_spellbooks.events;
import net.minecraft.world.entity.player.Player;
public interface RiderSettings {
    default boolean shouldRiderSit() { return true; }
    default boolean shouldRiderFaceForward(Player player) { return false; }
    static boolean shouldSit(net.minecraft.world.entity.Entity vehicle) {
        return !(vehicle instanceof RiderSettings settings) || settings.shouldRiderSit();
    }
}
