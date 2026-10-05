package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.ModTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;

public class SpiderAspectEffect extends MagicMobEffect {
    public static final float DAMAGE_PER_LEVEL = .05f;

    public SpiderAspectEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public static void increaseDamage(LivingDamageEvent event) {
        var attacker = event.getSource().getEntity();
        if (attacker instanceof LivingEntity livingAttacker) {
            if (livingAttacker.hasEffect(MobEffectRegistry.SPIDER_ASPECT.get())) {
                boolean targetHasTagEffect = event.getEntity().getActiveEffects().stream()
                        .anyMatch(instance -> BuiltInRegistries.MOB_EFFECT.wrapAsHolder(instance.getEffect()).is(ModTags.AFFECTED_BY_SPIDER_ASPECT));
                if (targetHasTagEffect) {
                    int lvl = livingAttacker.getEffect(MobEffectRegistry.SPIDER_ASPECT.get()).getAmplifier() + 1;
                    float multiplier = 1 + SpiderAspectEffect.DAMAGE_PER_LEVEL * lvl;
                    event.setAmount(event.getAmount() * multiplier);
                }
            }
        }
    }
}
