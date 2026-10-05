package io.redspace.ironsspellbooks.api.events;

import net.minecraft.world.entity.Entity;
import org.marj4n.irons_spellbooks.events.SpellEvent;

public class CounterSpellEvent extends SpellEvent {
    public final Entity caster;
    public final Entity target;

    public CounterSpellEvent(Entity caster, Entity target) {
        this.caster = caster;
        this.target = target;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
