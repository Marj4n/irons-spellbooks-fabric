package org.marj4n.irons_spellbooks.fluid;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

/** Texture and potion-NBT tint shared by cauldron rendering and particle colors. */
public interface FluidAppearance {
    ResourceLocation getStillTexture();
    ResourceLocation getFlowingTexture();
    default int getTintColor() { return 0xFFFFFFFF; }
    default int getTintColor(FluidStack stack) { return getTintColor(); }
    default ResourceLocation getStillTexture(FluidState state, BlockAndTintGetter level, BlockPos pos) {
        return getStillTexture();
    }
    default int getTintColor(FluidState state, BlockAndTintGetter level, BlockPos pos) {
        return getTintColor();
    }
    static FluidAppearance of(Fluid fluid) { return FluidAppearances.of(fluid); }
}
