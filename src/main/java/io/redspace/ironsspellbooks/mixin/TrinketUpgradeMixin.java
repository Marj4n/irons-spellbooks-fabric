package io.redspace.ironsspellbooks.mixin;
import dev.emi.trinkets.api.*;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(value=TrinketsApi.class,remap=false)
public abstract class TrinketUpgradeMixin {
    @Inject(method="getTrinket",at=@At("RETURN"),cancellable=true)
    private static void irons_spellbooks$upgrade(Item item,CallbackInfoReturnable<Trinket> cir){
        var trinket=cir.getReturnValue();
        if(!(trinket instanceof org.marj4n.irons_spellbooks.equipment.EquipmentItem)
                && !(trinket instanceof org.marj4n.irons_spellbooks.equipment.UpgradedTrinket))
            cir.setReturnValue(new org.marj4n.irons_spellbooks.equipment.UpgradedTrinket(trinket));
    }
}
