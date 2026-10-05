package org.marj4n.irons_spellbooks.events;
import net.minecraft.world.entity.LivingEntity;
public final class LivingKnockBackEvent extends SpellLivingEvent {
    public LivingKnockBackEvent(LivingEntity entity) { super(entity); }
    @Override public boolean isCancelable() { return true; }
}
