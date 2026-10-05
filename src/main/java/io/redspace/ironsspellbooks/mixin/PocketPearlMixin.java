package io.redspace.ironsspellbooks.mixin;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(ThrownEnderpearl.class)
public abstract class PocketPearlMixin {
    @Inject(method="onHit",at=@At("HEAD"),cancellable=true)
    private void irons_spellbooks$pearl(HitResult hit,CallbackInfo ci) {
        var pearl=(ThrownEnderpearl)(Object)this;
        if (io.redspace.ironsspellbooks.player.ServerPlayerEvents.isPocketTeleportBlocked(pearl.getOwner())) {
            pearl.discard(); ci.cancel();
        }
    }
}
