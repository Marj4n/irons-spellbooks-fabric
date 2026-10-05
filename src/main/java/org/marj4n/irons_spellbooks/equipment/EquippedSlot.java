package org.marj4n.irons_spellbooks.equipment;

import net.minecraft.world.item.ItemStack;

public record EquippedSlot(EquipmentSlotContext slotContext, ItemStack stack) {}
