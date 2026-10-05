package io.redspace.ironsspellbooks.item.curios;

import io.redspace.ironsspellbooks.compat.Curios;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import org.marj4n.irons_spellbooks.equipment.EquipmentSlotContext;

public class VisibilityRing extends SimpleDescriptiveCurio {
    public VisibilityRing(Properties properties) {
        super(properties, Curios.RING_SLOT);
    }

    @Override
    public void curioTick(EquipmentSlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        slotContext.entity().removeEffect(MobEffects.BLINDNESS);
        slotContext.entity().removeEffect(MobEffects.DARKNESS);
    }

}
