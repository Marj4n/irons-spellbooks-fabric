package io.redspace.ironsspellbooks.api.events;


import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.marj4n.irons_spellbooks.events.SpellLivingEvent;

public class SpellDamageEvent extends SpellLivingEvent  {
    @Override public boolean isCancelable() { return true; }

    //TODO: pre and post?
    private final SpellDamageSource spellDamageSource;
    private final float baseAmount;
    private float amount;

    public SpellDamageEvent(LivingEntity livingEntity, float amount, SpellDamageSource spellDamageSource) {
        super(livingEntity);
        this.spellDamageSource = spellDamageSource;
        this.baseAmount = amount;
        this.amount = this.baseAmount;
    }

    public float getOriginalAmount() {
        return this.baseAmount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public SpellDamageSource getSpellDamageSource() {
        return this.spellDamageSource;
    }
}
