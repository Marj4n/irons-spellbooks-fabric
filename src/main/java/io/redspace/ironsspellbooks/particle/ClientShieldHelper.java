package io.redspace.ironsspellbooks.particle;

import io.redspace.ironsspellbooks.config.ClientConfigs;
import io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.fabricmc.api.EnvType;

import java.util.ArrayList;
import java.util.List;

public class ClientShieldHelper {
    private static final ArrayList<AbstractShieldEntity> trackedEntities = new ArrayList<>();

    public static synchronized void trackShieldCreated(net.minecraft.world.entity.Entity entity) {
        if (entity instanceof AbstractShieldEntity ase) {
            trackedEntities.add(ase);
        }
    }

    public static synchronized void trackShieldRemoved(net.minecraft.world.entity.Entity entity) {
        if (entity instanceof AbstractShieldEntity ase) {
            trackedEntities.remove(ase);
        }
    }

    public static synchronized void onPlayerLogOut() {
        trackedEntities.clear();
    }

    public static synchronized List<VoxelShape> getShieldsFor(AABB boundingBox) {
        if (trackedEntities.isEmpty() || !ClientConfigs.SHIELD_PARTICLE_COLLISIONS.get()) {
            return List.of();
        } else {
            List<VoxelShape> shieldCollisions = new ArrayList<>();
            for (var s : trackedEntities) {
                if (boundingBox.intersects(s.getBoundingBox().inflate(1))) shieldCollisions.addAll(s.getVoxels());
            }
            return shieldCollisions;
        }
    }
}
