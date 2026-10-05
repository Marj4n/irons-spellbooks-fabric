package org.marj4n.irons_spellbooks.events;

import net.minecraft.world.entity.LivingEntity;

public abstract class SpellLivingEvent extends SpellEvent {
    private final LivingEntity entity;
    protected SpellLivingEvent(LivingEntity entity) { this.entity = entity; }
    public LivingEntity getEntity() { return entity; }
}
