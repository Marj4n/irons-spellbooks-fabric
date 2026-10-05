package org.marj4n.irons_spellbooks.equipment;
import dev.emi.trinkets.api.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.*;
import com.google.common.collect.*;
import java.util.UUID;
/** Adds ISS upgrades to external accessories without replacing their own lifecycle. */
public record UpgradedTrinket(Trinket delegate) implements Trinket {
    public void tick(ItemStack stack,SlotReference slot,LivingEntity entity){delegate.tick(stack,slot,entity);}
    public void onEquip(ItemStack stack,SlotReference slot,LivingEntity entity){delegate.onEquip(stack,slot,entity);}
    public void onUnequip(ItemStack stack,SlotReference slot,LivingEntity entity){delegate.onUnequip(stack,slot,entity);}
    public boolean canEquip(ItemStack stack,SlotReference slot,LivingEntity entity){return delegate.canEquip(stack,slot,entity);}
    public boolean canUnequip(ItemStack stack,SlotReference slot,LivingEntity entity){return delegate.canUnequip(stack,slot,entity);}
    public void onBreak(ItemStack stack,SlotReference slot,LivingEntity entity){delegate.onBreak(stack,slot,entity);}
    public TrinketEnums.DropRule getDropRule(ItemStack stack,SlotReference slot,LivingEntity entity){return delegate.getDropRule(stack,slot,entity);}
    public Multimap<Attribute,AttributeModifier> getModifiers(ItemStack stack,SlotReference slot,LivingEntity entity,UUID uuid){
        var modifiers=ArrayListMultimap.create(delegate.getModifiers(stack,slot,entity,uuid));
        var upgrade=io.redspace.ironsspellbooks.api.item.UpgradeData.getUpgradeData(stack);
        if(upgrade!=io.redspace.ironsspellbooks.api.item.UpgradeData.NONE && upgrade.getUpgradedSlot().equals(slot.inventory().getSlotType().getName()))
            io.redspace.ironsspellbooks.util.UpgradeUtils.handleAttributeEvent(modifiers,upgrade,modifiers::put,modifiers::remove,java.util.Optional.of(uuid));
        return modifiers;
    }
}
