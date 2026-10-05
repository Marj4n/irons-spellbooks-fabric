package org.marj4n.irons_spellbooks.fluid;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;

/** Recipe-facing millibucket operations. Automation is exposed as Fabric Storage. */
public interface MillibucketHandler {
    enum FluidAction { EXECUTE, SIMULATE }
    int getTanks();
    FluidStack getFluidInTank(int tank);
    int getTankCapacity(int tank);
    boolean isFluidValid(int tank, FluidStack stack);
    int fill(FluidStack resource, FluidAction action);
    FluidStack drain(FluidStack resource, FluidAction action);
    FluidStack drain(int maxDrain, FluidAction action);
}
