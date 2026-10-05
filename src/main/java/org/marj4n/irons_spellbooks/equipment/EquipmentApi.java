package org.marj4n.irons_spellbooks.equipment;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/** Real equipped-item access backed by the synced Trinkets component. */
public final class EquipmentApi {
    private EquipmentApi() {}

    public static Optional<Inventory> getCuriosInventory(LivingEntity entity) {
        return TrinketsApi.getTrinketComponent(entity).map(Inventory::new);
    }

    public static EquipmentApi getCuriosHelper() { return new EquipmentApi(); }

    public java.util.Set<String> getCurioTags(Item item) {
        return net.minecraft.core.registries.BuiltInRegistries.ITEM.wrapAsHolder(item).tags()
                .map(net.minecraft.tags.TagKey::location)
                .filter(id -> id.getNamespace().equals("curios"))
                .map(net.minecraft.resources.ResourceLocation::getPath)
                .collect(java.util.stream.Collectors.toCollection(java.util.TreeSet::new));
    }

    public Optional<EquippedSlot> findCurio(LivingEntity entity, String slot, int index) {
        return getCuriosInventory(entity).flatMap(inv -> inv.findSlot(slot, index));
    }

    public static final class Inventory {
        private final TrinketComponent component;
        private Inventory(TrinketComponent component) { this.component = component; }

        public List<EquippedSlot> findCurios(Predicate<ItemStack> predicate) {
            return component.getEquipped(predicate).stream()
                    .map(pair -> new EquippedSlot(EquipmentSlotContext.of(pair.getA(),
                            component.getEntity()), pair.getB()))
                    .sorted(Comparator.comparing((EquippedSlot slot) -> slot.slotContext().identifier())
                            .thenComparingInt(slot -> slot.slotContext().index())).toList();
        }

        public List<EquippedSlot> findCurios(Item item) {
            return findCurios(stack -> stack.is(item));
        }

        public Optional<EquippedSlot> findFirstCurio(Item item) {
            return findCurios(item).stream().findFirst();
        }

        private List<TrinketInventory> inventories(String name) {
            return component.getInventory().entrySet().stream().sorted(java.util.Map.Entry.comparingByKey())
                    .map(group -> group.getValue().get(name)).filter(java.util.Objects::nonNull).toList();
        }
        public Optional<EquippedSlot> findSlot(String name, int index) {
            if(index < 0) return Optional.empty(); int offset=0;
            for(var inv:inventories(name)) {
                if(index < offset+inv.getContainerSize()) return Optional.of(new EquippedSlot(
                        new EquipmentSlotContext(name,component.getEntity(),index,false,true),inv.getItem(index-offset)));
                offset+=inv.getContainerSize();
            }
            return Optional.empty();
        }
        public void setEquippedCurio(String name,int index,ItemStack stack) {
            if(index<0)return; int offset=0;
            for(var inv:inventories(name)) {
                if(index<offset+inv.getContainerSize()) { inv.setItem(index-offset,stack); inv.setChanged(); return; }
                offset+=inv.getContainerSize();
            }
        }
    }
}
