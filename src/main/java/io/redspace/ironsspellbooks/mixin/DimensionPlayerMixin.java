package io.redspace.ironsspellbooks.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(net.minecraft.server.level.ServerPlayer.class)
public abstract class DimensionPlayerMixin {
    @Inject(method="changeDimension(Lnet/minecraft/server/level/ServerLevel;)Lnet/minecraft/world/entity/Entity;",at=@At("HEAD"),cancellable=true)
    private void irons_spellbooks$dimension(net.minecraft.server.level.ServerLevel destination,
            CallbackInfoReturnable<net.minecraft.world.entity.Entity> cir) {
        if (!io.redspace.ironsspellbooks.player.ServerPlayerEvents.onChangeDimensions((net.minecraft.world.entity.Entity)(Object)this)) cir.setReturnValue(null);
    }
}
