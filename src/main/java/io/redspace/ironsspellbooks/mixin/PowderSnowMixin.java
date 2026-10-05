package io.redspace.ironsspellbooks.mixin;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowMixin {
    @Inject(method="canEntityWalkOnPowderSnow",at=@At("RETURN"),cancellable=true)
    private static void irons_spellbooks$frostward(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof net.minecraft.world.entity.LivingEntity living
                && io.redspace.ironsspellbooks.registries.ItemRegistry.FROSTWARD_RING.get().isEquippedBy(living)) cir.setReturnValue(true);
    }
}
