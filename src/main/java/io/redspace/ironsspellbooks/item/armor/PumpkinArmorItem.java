package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.entity.armor.pumpkin.PumpkinArmorModel;
import io.redspace.ironsspellbooks.entity.armor.pumpkin.PumpkinArmorRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PumpkinArmorItem extends ExtendedArmorItem {
    public PumpkinArmorItem(Type slot, Properties settings) {
        super(ExtendedArmorMaterials.PUMPKIN, slot, settings, withManaAttribute(75));
    }

    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(this);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new PumpkinArmorRenderer(new PumpkinArmorModel());
    }
}
