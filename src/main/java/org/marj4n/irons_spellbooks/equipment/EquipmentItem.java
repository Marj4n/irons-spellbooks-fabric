package org.marj4n.irons_spellbooks.equipment;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

/** Retains item hooks while forwarding all equipment lifecycle to Trinkets. */
public interface EquipmentItem extends Trinket {
    record SoundInfo(SoundEvent soundEvent, float volume, float pitch) {}

    default void curioTick(EquipmentSlotContext context, ItemStack stack) {}
    default boolean canEquipFromUse(EquipmentSlotContext context, ItemStack stack) { return false; }
    default SoundInfo getEquipSound(EquipmentSlotContext context, ItemStack stack) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC, 1, 1);
    }
    default void onEquipFromUse(EquipmentSlotContext context, ItemStack stack) {
        SoundInfo sound = getEquipSound(context, stack);
        context.entity().playSound(sound.soundEvent(), sound.volume(), sound.pitch());
    }
    default Multimap<Attribute, AttributeModifier> getAttributeModifiers(
            EquipmentSlotContext context, UUID uuid, ItemStack stack) {
        return com.google.common.collect.ArrayListMultimap.create();
    }
    @Override
    default void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        curioTick(EquipmentSlotContext.of(slot, entity), stack);
    }
    @Override
    default Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack,
            SlotReference slot, LivingEntity entity, UUID uuid) {
        Multimap<Attribute, AttributeModifier> modifiers = com.google.common.collect.ArrayListMultimap.create(
                Trinket.super.getModifiers(stack, slot, entity, uuid));
        modifiers.putAll(getAttributeModifiers(EquipmentSlotContext.of(slot, entity), uuid, stack));
        var upgrade = io.redspace.ironsspellbooks.api.item.UpgradeData.getUpgradeData(stack);
        if (upgrade != io.redspace.ironsspellbooks.api.item.UpgradeData.NONE
                && upgrade.getUpgradedSlot().equals(slot.inventory().getSlotType().getName())) {
            io.redspace.ironsspellbooks.util.UpgradeUtils.handleAttributeEvent(modifiers, upgrade,
                    modifiers::put, modifiers::remove, java.util.Optional.of(uuid));
        }
        return modifiers;
    }

    @Override
    default void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        syncSelection(stack, entity);
    }

    @Override
    default void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        syncSelection(stack, entity);
    }

    private static void syncSelection(ItemStack stack, LivingEntity entity) {
        if (entity instanceof net.minecraft.server.level.ServerPlayer player
                && io.redspace.ironsspellbooks.api.spells.ISpellContainer.isSpellContainer(stack)) {
            io.redspace.ironsspellbooks.setup.PacketDistributor.sendToPlayer(player,
                    new io.redspace.ironsspellbooks.network.EquipmentChangedPacket());
        }
    }
}
