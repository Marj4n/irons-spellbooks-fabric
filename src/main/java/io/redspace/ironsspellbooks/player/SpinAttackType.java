package io.redspace.ironsspellbooks.player;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;

public record SpinAttackType(ResourceLocation textureId, boolean fullbright) {
    public static final SpinAttackType FIRE = new SpinAttackType(new ResourceLocation(IronsSpellbooks.MODID, "textures/entity/fire_riptide.png"), true);
    public static final SpinAttackType LIGHTNING = new SpinAttackType(new ResourceLocation(IronsSpellbooks.MODID, "textures/entity/lightning_riptide.png"), true);
    public static final SpinAttackType RIPTIDE = new SpinAttackType(new ResourceLocation("textures/entity/trident_riptide.png"), false);
}
