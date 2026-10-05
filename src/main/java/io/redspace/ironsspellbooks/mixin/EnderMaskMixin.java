package io.redspace.ironsspellbooks.mixin;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(EnderMan.class)
public abstract class EnderMaskMixin {
    @Inject(method="isLookingAtMe",at=@At("HEAD"),cancellable=true)
    private void irons_spellbooks$mask(Player player, CallbackInfoReturnable<Boolean> cir) {
        var stack=player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD);
        if (stack.getItem() instanceof io.redspace.ironsspellbooks.item.armor.PumpkinArmorItem mask
                && mask.isEnderMask(stack,player,(EnderMan)(Object)this)) cir.setReturnValue(false);
    }
}
