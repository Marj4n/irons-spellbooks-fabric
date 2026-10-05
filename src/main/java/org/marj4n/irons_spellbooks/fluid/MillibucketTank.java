package org.marj4n.irons_spellbooks.fluid;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.redspace.ironsspellbooks.api.backwards_compat.FluidHelper;
import org.marj4n.irons_spellbooks.fluid.MillibucketHandler.FluidAction;

public class MillibucketTank {
    private final int capacity;
    private FluidStack fluid = FluidStack.EMPTY;
    public MillibucketTank(int capacity) { this.capacity = capacity; }
    public int getCapacity() { return capacity; }
    public FluidStack getFluid() { return fluid; }
    public int getFluidAmount() { return Math.toIntExact(fluid.getAmount()); }
    public boolean isFluidValid(FluidStack stack) { return true; }
    protected void onContentsChanged() {}
    public void restore(FluidStack snapshot) { fluid = snapshot.copy(); }
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource)
                || (!fluid.isEmpty() && !FluidHelper.isSameFluidSameComponents(fluid, resource))) return 0;
        int amount = (int) Math.min(capacity - getFluidAmount(), resource.getAmount());
        if (amount <= 0) return 0;
        if (action == FluidAction.EXECUTE) {
            fluid = FluidHelper.copyWithAmount(resource, getFluidAmount() + amount);
            onContentsChanged();
        }
        return amount;
    }
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (!FluidHelper.isSameFluidSameComponents(fluid, resource)) return FluidStack.EMPTY;
        return drain((int) Math.min(Integer.MAX_VALUE, resource.getAmount()), action);
    }
    public FluidStack drain(int amount, FluidAction action) {
        amount = Math.min(Math.max(0, amount), getFluidAmount());
        if (amount == 0) return FluidStack.EMPTY;
        var result = FluidHelper.copyWithAmount(fluid, amount);
        if (action == FluidAction.EXECUTE) {
            fluid = getFluidAmount() == amount ? FluidStack.EMPTY
                    : FluidHelper.copyWithAmount(fluid, getFluidAmount() - amount);
            onContentsChanged();
        }
        return result;
    }
}
