package io.redspace.ironsspellbooks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import io.redspace.ironsspellbooks.player.ClientPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingRenderMixin {
    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"), cancellable = true)
    private void irons_spellbooks$beforeRender(LivingEntity entity, float yaw, float partialTick,
            PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci) {
        if (ClientPlayerEvents.beforeLivingRender(entity)) ci.cancel();
    }
    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("RETURN"))
    private void irons_spellbooks$afterRender(LivingEntity entity, float yaw, float partialTick,
            PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci) {
        ClientPlayerEvents.afterLivingRender(entity, poseStack, buffers, partialTick);
    }
}
