package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.entity.armor.ShadowwalkerArmorModel;
import net.minecraft.world.item.ArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ShadowwalkerArmorItem extends ImbuableChestplateArmorItem implements IDisableJacket {
    public ShadowwalkerArmorItem(ArmorItem.Type slot, Properties settings) {
        super(ExtendedArmorMaterials.SHADOWWALKER, slot, settings/*, schoolAttributes(AttributeRegistry.ENDER_SPELL_POWER)*/);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GenericCustomArmorRenderer<>(new ShadowwalkerArmorModel());
    }
}
