package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.util.ModTags;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.marj4n.irons_spellbooks.equipment.EquipmentSlotContext;
import dev.emi.trinkets.TrinketSlot;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;

@Mixin(TrinketSlot.class)
public interface CurioHooksMixin {

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true, remap = false)
    private static void killGenericCurio(ItemStack stack, SlotReference slotReference, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (slotReference.inventory().getSlotType().getName().equals("curio")) {
            if (stack.is(ModTags.SPELLBOOK_CURIO)) {
                cir.setReturnValue(false);
            }
        }
    }
}
