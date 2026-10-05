package io.redspace.ironsspellbooks.item.curios;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.marj4n.irons_spellbooks.equipment.EquipmentApi;
import org.marj4n.irons_spellbooks.equipment.EquipmentSlotContext;
import org.marj4n.irons_spellbooks.equipment.EquipmentItem;

import java.util.UUID;
import java.util.function.Function;

public class CurioBaseItem extends Item implements EquipmentItem, org.marj4n.irons_spellbooks.client.item.NativeClientItem {
    public java.util.List<net.minecraft.network.chat.Component> getSlotsTooltip(
            java.util.List<net.minecraft.network.chat.Component> lines, ItemStack stack) { return lines; }
    @Override public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level,
            java.util.List<net.minecraft.network.chat.Component> lines, net.minecraft.world.item.TooltipFlag flags) {
        super.appendHoverText(stack,level,lines,flags);
        getSlotsTooltip(lines,stack);
    }
    String attributeSlot = "";
    Function<Integer, Multimap<Attribute, AttributeModifier>> attributes = null;

    public CurioBaseItem(Item.Properties properties) {
        super(properties);
        dev.emi.trinkets.api.TrinketsApi.registerTrinket(this, this);
    }

    @Override
    public net.minecraft.world.InteractionResultHolder<ItemStack> use(
            net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player,
            net.minecraft.world.InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        var component = dev.emi.trinkets.api.TrinketsApi.getTrinketComponent(player);
        if (component.isPresent()) {
            for (var group : component.get().getInventory().values()) {
                for (var inventory : group.values()) {
                    for (int index = 0; index < inventory.getContainerSize(); index++) {
                        if (!inventory.getItem(index).isEmpty()) continue;
                        var slot = new dev.emi.trinkets.api.SlotReference(inventory, index);
                        var context = EquipmentSlotContext.of(slot, player);
                        if (canEquipFromUse(context, stack)
                                && dev.emi.trinkets.TrinketSlot.canInsert(stack, slot, player)) {
                            ItemStack equipped = stack.copy();
                            inventory.setItem(index, equipped);
                            onEquipFromUse(context, equipped);
                            player.gameEvent(net.minecraft.world.level.gameevent.GameEvent.EQUIP);
                            stack.setCount(0);
                            return net.minecraft.world.InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                        }
                    }
                }
            }
        }
        return super.use(level, player, hand);
    }

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return entity != null && EquipmentApi.getCuriosInventory(entity).map(inv -> inv.findFirstCurio(this).isPresent()).orElse(false);
    }

    @NotNull
    @Override
    public EquipmentItem.SoundInfo getEquipSound(EquipmentSlotContext slotContext, ItemStack stack) {
        return new EquipmentItem.SoundInfo(SoundEvents.ARMOR_EQUIP_CHAIN, 1.0f, 1.0f);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotContext slotContext, UUID uuid, ItemStack stack) {
        return attributes != null && slotContext.identifier().equals(this.attributeSlot) ? attributes.apply(slotContext.index()) : EquipmentItem.super.getAttributeModifiers(slotContext, uuid, stack);
    }

    public CurioBaseItem withAttributes(String slot, AttributeContainer... attributes) {
        this.attributeSlot = slot;
        this.attributes = (index) -> {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            for (AttributeContainer holder : attributes) {
                String id = String.format("%s_%s", attributeSlot, index);
                builder.put(holder.attribute().get(), holder.createModifier(id));
            }
            return builder.build();
        };
        return this;
    }

    public CurioBaseItem withSpellbookAttributes(AttributeContainer... attributes) {
        return withAttributes(Curios.SPELLBOOK_SLOT, attributes);
    }
}
