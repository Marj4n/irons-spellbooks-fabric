package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow @Final private DataSlot cost;
    @Shadow private int repairItemCountCost;
    protected AnvilMenuMixin(MenuType<?> type, int id, Inventory inventory, ContainerLevelAccess access) {
        super(type, id, inventory, access);
    }
    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void ironsSpellbooks$shrivingRecipe(CallbackInfo ci) {
        if (inputSlots.getItem(1).is(ItemRegistry.SHRIVING_STONE.get())) {
            var result = Utils.handleShriving(inputSlots.getItem(0));
            if (!result.isEmpty()) {
                resultSlots.setItem(0, result);
                cost.set(1);
                repairItemCountCost = 1;
                broadcastChanges();
                ci.cancel();
            }
        }
    }
}
