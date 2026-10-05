package io.redspace.ironsspellbooks.mixin;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(LivingEntity.class)
public abstract class PocketTeleportMixin {
    @Inject(method="randomTeleport",at=@At("HEAD"),cancellable=true)
    private void irons_spellbooks$teleport(double x,double y,double z,boolean particles,CallbackInfoReturnable<Boolean> cir) {
        if (io.redspace.ironsspellbooks.player.ServerPlayerEvents.isPocketTeleportBlocked((LivingEntity)(Object)this)) cir.setReturnValue(false);
    }
}
