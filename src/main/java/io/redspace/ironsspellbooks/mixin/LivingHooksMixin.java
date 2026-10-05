package io.redspace.ironsspellbooks.mixin;

import org.marj4n.irons_spellbooks.events.LivingHealEvent;
import org.marj4n.irons_spellbooks.events.LivingKnockBackEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingHooksMixin {
    @ModifyVariable(method = "heal", at = @At("HEAD"), argsOnly = true)
    private float ironsSpellbooks$modifyHealing(float amount) {
        var event = new LivingHealEvent((LivingEntity) (Object) this, amount);
        return event.post() ? 0 : Math.max(0, event.getAmount());
    }
    @Inject(method = "knockback", at = @At("HEAD"), cancellable = true)
    private void ironsSpellbooks$cancelSpellKnockback(double strength, double x, double z, CallbackInfo ci) {
        if (new LivingKnockBackEvent((LivingEntity) (Object) this).post()) ci.cancel();
    }
}
