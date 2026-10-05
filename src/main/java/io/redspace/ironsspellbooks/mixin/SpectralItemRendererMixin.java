package io.redspace.ironsspellbooks.mixin;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
@Mixin(ItemRenderer.class)
public abstract class SpectralItemRendererMixin {
    @ModifyVariable(method="render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",at=@At("STORE"),ordinal=0)
    private RenderType irons_spellbooks$spectral(RenderType original,ItemStack stack,ItemDisplayContext context,
            boolean leftHand,PoseStack pose,MultiBufferSource buffers,int light,int overlay,BakedModel model) {
        return model instanceof io.redspace.ironsspellbooks.render.SpectralItemModel spectral
                ? spectral.getRenderTypes(stack,false).get(0) : original;
    }
    @ModifyVariable(method="render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",at=@At("HEAD"),argsOnly=true)
    private BakedModel irons_spellbooks$perspective(BakedModel model,ItemStack stack,ItemDisplayContext context,
            boolean leftHand,PoseStack pose,MultiBufferSource buffers,int light,int overlay,BakedModel original){
        return model instanceof org.marj4n.irons_spellbooks.client.item.NativePerspectiveModels.PerspectiveModel views
                ? views.forContext(context) : model;
    }
}
