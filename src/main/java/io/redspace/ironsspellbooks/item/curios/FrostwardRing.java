package io.redspace.ironsspellbooks.item.curios;

import io.redspace.ironsspellbooks.compat.Curios;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.marj4n.irons_spellbooks.equipment.EquipmentSlotContext;

public class FrostwardRing extends SimpleDescriptiveCurio implements io.github.fabricators_of_create.porting_lib.item.WalkOnSnowItem {
    public FrostwardRing(Properties properties) {
        super(properties, Curios.RING_SLOT);
    }

    @Override
    public void curioTick(EquipmentSlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        slotContext.entity().setTicksFrozen(0);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
