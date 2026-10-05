package org.marj4n.irons_spellbooks.equipment;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;

/** Slot names retained in spell-selection data; inventories are owned by Trinkets. */
public record EquipmentSlotContext(String identifier, LivingEntity entity, int index,
                                   boolean cosmetic, boolean visible) {
    public static EquipmentSlotContext of(SlotReference slot, LivingEntity entity) {
        int offset=0;
        var component=dev.emi.trinkets.api.TrinketsApi.getTrinketComponent(entity);
        if(component.isPresent()) {
            for(var group:component.get().getInventory().entrySet().stream().sorted(java.util.Map.Entry.comparingByKey()).toList()) {
                var inventory=group.getValue().get(slot.inventory().getSlotType().getName());
                if(inventory==slot.inventory()) break;
                if(inventory!=null) offset+=inventory.getContainerSize();
            }
        }
        return new EquipmentSlotContext(slot.inventory().getSlotType().getName(), entity,
                offset+slot.index(), false, true);
    }
}
