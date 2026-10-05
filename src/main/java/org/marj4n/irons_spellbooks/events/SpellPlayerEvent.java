package org.marj4n.irons_spellbooks.events;

import net.minecraft.world.entity.player.Player;

public abstract class SpellPlayerEvent extends SpellEvent {
    private final Player player;
    protected SpellPlayerEvent(Player player) { this.player = player; }
    public Player getEntity() { return player; }
}
