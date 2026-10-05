package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.api.backwards_compat.IBackwardsCompatDefaultNbtItem;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;I)V", at = @At("TAIL"))
    public void init(ItemLike itemLike, int count, CallbackInfo ci) {
        if (itemLike != null && itemLike.asItem() instanceof IPresetSpellContainer iPresetSpellContainer) {
            iPresetSpellContainer.initializeSpellContainer((ItemStack) (Object) this);
        }

        if (itemLike != null && itemLike.asItem() instanceof IBackwardsCompatDefaultNbtItem iPresetSpellContainer) {
            iPresetSpellContainer.setupItem((ItemStack) (Object) this);
        }
    }
    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void ironsSpellbooks$upgradeAttributes(net.minecraft.world.entity.EquipmentSlot slot,
            org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<com.google.common.collect.Multimap<net.minecraft.world.entity.ai.attributes.Attribute, net.minecraft.world.entity.ai.attributes.AttributeModifier>> cir) {
        var upgrade = io.redspace.ironsspellbooks.api.item.UpgradeData.getUpgradeData((ItemStack) (Object) this);
        if (upgrade != io.redspace.ironsspellbooks.api.item.UpgradeData.NONE
                && upgrade.getUpgradedSlot().equals(slot.getName())) {
            var modifiers = com.google.common.collect.ArrayListMultimap.create(cir.getReturnValue());
            io.redspace.ironsspellbooks.util.UpgradeUtils.handleAttributeEvent(modifiers, upgrade,
                    modifiers::put, modifiers::remove, java.util.Optional.empty());
            cir.setReturnValue(modifiers);
        }
    }
    @Inject(method="getTooltipLines",at=@At("RETURN"))
    private void irons_spellbooks$cinderousName(net.minecraft.world.entity.player.Player player,
            net.minecraft.world.item.TooltipFlag flag, CallbackInfoReturnable<java.util.List<net.minecraft.network.chat.Component>> cir) {
        var lines=cir.getReturnValue();
        if (!lines.isEmpty() && io.redspace.ironsspellbooks.render.CinderousRarity.isCinderous((ItemStack)(Object)this))
            lines.set(0,lines.get(0).copy().withStyle(style -> style.withColor(io.redspace.ironsspellbooks.render.CinderousRarity.COLOR)));
    }
}