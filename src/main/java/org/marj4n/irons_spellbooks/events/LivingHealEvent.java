package org.marj4n.irons_spellbooks.events;
import net.minecraft.world.entity.LivingEntity;
public final class LivingHealEvent extends SpellLivingEvent {
    private float amount;
    public LivingHealEvent(LivingEntity entity, float amount) { super(entity); this.amount = amount; }
    public float getAmount() { return amount; }
    public void setAmount(float amount) { this.amount = amount; }
    @Override public boolean isCancelable() { return true; }
}
