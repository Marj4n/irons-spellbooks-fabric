package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.entity.armor.PyromancerArmorModel;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PyromancerArmorItem extends ImbuableChestplateArmorItem implements IArmorCapeProvider {
    public PyromancerArmorItem(Type slot, Properties settings) {
        super(ExtendedArmorMaterials.PYROMANCER, slot, settings);
    }

    @Override
    public ResourceLocation getCapeResourceLocation() {
        return new ResourceLocation(IronsSpellbooks.MODID, "textures/models/armor/pyromancer_cape.png");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GenericCustomArmorRenderer<>(new PyromancerArmorModel());
    }
}
