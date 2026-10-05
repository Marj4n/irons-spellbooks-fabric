package org.marj4n.irons_spellbooks.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

/** Vanilla inventory with the original Forge inventory NBT format retained. */
public class NativeItemInventory extends SimpleContainer {
    public NativeItemInventory(int size) { super(size); }
    public int getSlots() { return getContainerSize(); }
    public ItemStack getStackInSlot(int slot) { return getItem(slot); }
    public void setStackInSlot(int slot, ItemStack stack) { setItem(slot, stack); }
    protected void onContentsChanged(int slot) {}
    @Override public void setItem(int slot, ItemStack stack) {
        super.setItem(slot, stack);
        onContentsChanged(slot);
    }
    @Override public ItemStack removeItem(int slot, int amount) {
        ItemStack result = super.removeItem(slot, amount);
        if (!result.isEmpty()) onContentsChanged(slot);
        return result;
    }
    @Override public ItemStack removeItemNoUpdate(int slot) {
        ItemStack result = super.removeItemNoUpdate(slot);
        if (!result.isEmpty()) onContentsChanged(slot);
        return result;
    }
    public CompoundTag serializeNBT() {
        CompoundTag result = new CompoundTag();
        ListTag items = new ListTag();
        for (int slot = 0; slot < getContainerSize(); slot++) {
            if (getItem(slot).isEmpty()) continue;
            CompoundTag entry = getItem(slot).save(new CompoundTag());
            entry.putInt("Slot", slot);
            items.add(entry);
        }
        result.put("Items", items);
        result.putInt("Size", getContainerSize());
        return result;
    }
    public void deserializeNBT(CompoundTag tag) {
        for (int slot = 0; slot < getContainerSize(); slot++) setItem(slot, ItemStack.EMPTY);
        ListTag items = tag.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < items.size(); i++) {
            CompoundTag entry = items.getCompound(i);
            int slot = entry.getInt("Slot");
            if (slot >= 0 && slot < getContainerSize()) setItem(slot, ItemStack.of(entry));
        }
    }
}
