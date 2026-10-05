package io.redspace.ironsspellbooks.item.curios;

import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import org.marj4n.irons_spellbooks.equipment.EquipmentApi;

public class WickedBoneRingItem extends SimpleDescriptiveCurio {
    public WickedBoneRingItem(Properties properties) {
        super(properties, Curios.RING_SLOT);
    }

    public static void handleAbility(net.minecraft.world.entity.Entity entity, net.minecraft.world.level.Level level, boolean loadedFromDisk) {
        if (!loadedFromDisk && entity instanceof AbstractMagicProjectile magicProjectile) {
            var owner = magicProjectile.getOwner();
            if (owner instanceof LivingEntity livingEntity) {
                int ringCount = EquipmentApi.getCuriosInventory(livingEntity).map(inventory -> inventory.findCurios(ItemRegistry.WICKED_BONE_RING.get()).size()).orElse(0);
                int r = magicProjectile.getRicochetLevel();
                //fixme: manual circumvention of infinite terminator as -1 is unsustainable
                if (ringCount > 0 && r >= 0) {
                    magicProjectile.setRicochetLevel(r + ringCount);
                }
            }
        }
    }
}
