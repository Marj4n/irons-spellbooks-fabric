package io.redspace.ironsspellbooks.api.events;


import io.redspace.ironsspellbooks.api.spells.SpellData;
import net.minecraft.world.entity.player.Player;
import org.marj4n.irons_spellbooks.events.SpellPlayerEvent;


public class InscribeSpellEvent extends SpellPlayerEvent {
    private final SpellData spellData;

    public InscribeSpellEvent(Player player, SpellData spellData) {
        super(player);
        this.spellData = spellData;
    }

    public SpellData getSpellData() {
        return this.spellData;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
