package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.marj4n.irons_spellbooks.events.LivingHealEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;


public class BlightEffect extends MagicMobEffect {
    public static final float DAMAGE_PER_LEVEL = -.05f;
    public static final float HEALING_PER_LEVEL = -.10f;

    public BlightEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public static void reduceHealing(LivingHealEvent event) {
        var effect = event.getEntity().getEffect(MobEffectRegistry.BLIGHT.get());
        if (effect != null) {
            int lvl = effect.getAmplifier() + 1;
            float healingMult = 1 + HEALING_PER_LEVEL * lvl;
            float before = event.getAmount();
            event.setAmount(event.getAmount() * healingMult);
            //IronsSpellbooks.LOGGER.debug("BlightEffect.reduceHealing: {}->{}", before, event.getAmount());

        }
    }

    public static void reduceDamageOutput(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof LivingEntity livingAttacker) {
            var effect = livingAttacker.getEffect(MobEffectRegistry.BLIGHT.get());
            if (effect != null) {
                int lvl = effect.getAmplifier() + 1;
                float before = event.getAmount();
                float multiplier = 1 + BlightEffect.DAMAGE_PER_LEVEL * lvl;
                event.setAmount(event.getAmount() * multiplier);
                //IronsSpellbooks.LOGGER.debug("BlightEffect.reduceDamageOutput: {}->{}", before, event.getAmount());
            }
        }
    }
}
