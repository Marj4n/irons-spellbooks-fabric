package io.redspace.ironsspellbooks.mixin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.MenuProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerInventoryMixin {
    @Inject(method="drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",at=@At("RETURN"))
    private void irons_spellbooks$drop(ItemStack stack,boolean random,boolean trace,CallbackInfoReturnable<ItemEntity> cir) {
        if(cir.getReturnValue()!=null) io.redspace.ironsspellbooks.player.ServerPlayerEvents.onPlayerDropItem(cir.getReturnValue(),(ServerPlayer)(Object)this);
    }
    @Inject(method="openMenu",at=@At("RETURN"))
    private void irons_spellbooks$menu(MenuProvider provider,CallbackInfoReturnable<java.util.OptionalInt> cir) {
        if(cir.getReturnValue().isPresent()) io.redspace.ironsspellbooks.player.ServerPlayerEvents.onPlayerOpenContainer((ServerPlayer)(Object)this);
    }
}
