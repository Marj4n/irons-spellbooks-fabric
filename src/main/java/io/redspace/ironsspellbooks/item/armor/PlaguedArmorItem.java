package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.entity.armor.PlaguedArmorModel;
import net.minecraft.world.item.ArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PlaguedArmorItem extends ImbuableChestplateArmorItem {
    public PlaguedArmorItem(ArmorItem.Type slot, Properties settings) {
        super(ExtendedArmorMaterials.PLAGUED, slot, settings/*, schoolAttributes(AttributeRegistry.NATURE_SPELL_POWER)*/);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GenericCustomArmorRenderer<>(new PlaguedArmorModel());
    }
}
