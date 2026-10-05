package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.entity.armor.BootsOfSpeedArmorModel;
import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BootsOfSpeedArmorItem extends ImbuableChestplateArmorItem {
    public BootsOfSpeedArmorItem(Type type, Properties settings) {
        super(ExtendedArmorMaterials.BOOTS_OF_SPEED, type, settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GenericCustomArmorRenderer<>(new BootsOfSpeedArmorModel());
    }

}
