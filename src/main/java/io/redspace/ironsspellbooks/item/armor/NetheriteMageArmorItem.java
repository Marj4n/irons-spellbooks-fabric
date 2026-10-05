package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.entity.armor.DyeableArmorRenderer;
import io.redspace.ironsspellbooks.entity.armor.netherite.NetheriteMageArmorModel;
import net.minecraft.world.item.DyeableLeatherItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class NetheriteMageArmorItem extends ImbuableChestplateArmorItem implements DyeableLeatherItem {
    public NetheriteMageArmorItem(Type type, Properties settings) {
        super(ExtendedArmorMaterials.NETHERITE_BATTLEMAGE, type, settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new DyeableArmorRenderer<>(new NetheriteMageArmorModel());
    }

}
