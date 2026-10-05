package io.redspace.ironsspellbooks.api.events;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.Entity;
import org.marj4n.irons_spellbooks.events.SpellEntityTeleportEvent;

public class SpellTeleportEvent extends SpellEntityTeleportEvent {
    private final AbstractSpell spell;

    public SpellTeleportEvent(AbstractSpell spell, Entity entity, double targetX, double targetY, double targetZ) {
        super(entity, targetX, targetY, targetZ);
        this.spell = spell;
    }

    public AbstractSpell getSpell() {
        return spell;
    }
}
