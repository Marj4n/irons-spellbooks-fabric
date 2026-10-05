package io.redspace.ironsspellbooks.mixin;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class PortalPushReactionMixin {
    @Inject(method="getPistonPushReaction",at=@At("RETURN"),cancellable=true)
    private void irons_spellbooks$reaction(CallbackInfoReturnable<PushReaction> cir) {
        var state=(BlockState)(Object)this;
        if (state.getBlock() instanceof io.redspace.ironsspellbooks.block.portal_frame.PortalFrameBlock portal)
            cir.setReturnValue(portal.getPistonPushReaction(state));
    }
}
