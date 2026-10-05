package io.redspace.ironsspellbooks.render;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.util.Mth;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import org.marj4n.irons_spellbooks.client.item.NativeClientItemExtensions;
import java.util.function.Consumer;

/** Native model hook for the same held-staff arm rotation. */
public final class StaffArmPose {
    public static void initializeClientHelper(Consumer<NativeClientItemExtensions> consumer) {
        // Ordinary staffs use the standard item model; arm rotation is handled below.
    }
    public static void apply(HumanoidModel<?> model, LivingEntity entity) {
        boolean mainRight = entity.getMainArm() == HumanoidArm.RIGHT;
        if (entity.getMainHandItem().getItem() instanceof StaffItem) rotate(model, mainRight);
        if (entity.getOffhandItem().getItem() instanceof StaffItem) rotate(model, !mainRight);
    }
    private static void rotate(HumanoidModel<?> model, boolean right) {
        var arm = right ? model.rightArm : model.leftArm;
        arm.xRot = Mth.lerp(.85f, arm.xRot, -(float)Math.PI / 3.5f + model.head.xRot / 2f);
    }
}
