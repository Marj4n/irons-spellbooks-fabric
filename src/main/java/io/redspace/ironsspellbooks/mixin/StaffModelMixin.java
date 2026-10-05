package io.redspace.ironsspellbooks.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class StaffModelMixin {
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    private void irons_spellbooks$staffPose(LivingEntity entity, float limbSwing, float limbAmount,
            float age, float yaw, float pitch, CallbackInfo ci) {
        io.redspace.ironsspellbooks.render.StaffArmPose.apply((HumanoidModel<?>)(Object)this, entity);
    }
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void irons_spellbooks$ridingPose(LivingEntity entity, float swing, float amount, float age, float yaw, float pitch, CallbackInfo ci) {
        if (entity.isPassenger() && !org.marj4n.irons_spellbooks.events.RiderSettings.shouldSit(entity.getVehicle()))
            ((HumanoidModel<?>)(Object)this).riding=false;
    }
}
