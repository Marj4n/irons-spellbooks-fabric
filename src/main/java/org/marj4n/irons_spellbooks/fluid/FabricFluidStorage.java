package org.marj4n.irons_spellbooks.fluid;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;

import java.util.Iterator;
import java.util.stream.IntStream;

/** Transactional native automation with exact conversion at the millibucket boundary. */
public final class FabricFluidStorage extends SnapshotParticipant<FluidStack[]> implements Storage<FluidVariant> {
    public interface Host {
        MillibucketHandler handler();
        FluidStack[] snapshot();
        void restore(FluidStack[] snapshot);
        void mutate(Runnable mutation);
        void commit();
    }
    private static final long DROPLETS_PER_MB = FluidConstants.BUCKET / 1000;
    private final Host host;
    public FabricFluidStorage(Host host) { this.host = host; }
    @Override protected FluidStack[] createSnapshot() { return host.snapshot(); }
    @Override protected void readSnapshot(FluidStack[] state) { host.restore(state); }
    @Override protected void onFinalCommit() { host.commit(); }

    private static FluidStack stack(FluidVariant resource, int amount) {
        return new FluidStack(resource.getFluid(), amount, resource.copyNbt());
    }
    private static int millibuckets(FluidVariant resource, long maxAmount) {
        if (resource.isBlank() || maxAmount < 0) throw new IllegalArgumentException("Invalid fluid transfer");
        return (int) Math.min(Integer.MAX_VALUE, maxAmount / DROPLETS_PER_MB);
    }
    @Override public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        int offered = millibuckets(resource, maxAmount);
        if (offered == 0) return 0;
        int amount = host.handler().fill(stack(resource, offered), MillibucketHandler.FluidAction.SIMULATE);
        if (amount == 0) return 0;
        updateSnapshots(transaction);
        host.mutate(() -> host.handler().fill(stack(resource, amount), MillibucketHandler.FluidAction.EXECUTE));
        return amount * DROPLETS_PER_MB;
    }
    @Override public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        int requested = millibuckets(resource, maxAmount);
        if (requested == 0) return 0;
        var available = host.handler().drain(stack(resource, requested), MillibucketHandler.FluidAction.SIMULATE);
        int amount = Math.toIntExact(available.getAmount());
        if (amount == 0) return 0;
        updateSnapshots(transaction);
        host.mutate(() -> host.handler().drain(stack(resource, amount), MillibucketHandler.FluidAction.EXECUTE));
        return amount * DROPLETS_PER_MB;
    }
    @Override public Iterator<StorageView<FluidVariant>> iterator() {
        return IntStream.range(0, host.handler().getTanks()).<StorageView<FluidVariant>>mapToObj(index ->
                new StorageView<>() {
                    @Override public FluidVariant getResource() {
                        var fluid = host.handler().getFluidInTank(index);
                        return fluid.isEmpty() ? FluidVariant.blank() : FluidVariant.of(fluid.getFluid(), fluid.getTag());
                    }
                    @Override public boolean isResourceBlank() { return getResource().isBlank(); }
                    @Override public long getAmount() { return host.handler().getFluidInTank(index).getAmount() * DROPLETS_PER_MB; }
                    @Override public long getCapacity() { return host.handler().getTankCapacity(index) * DROPLETS_PER_MB; }
                    @Override public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
                        return getResource().equals(resource)
                                ? FabricFluidStorage.this.extract(resource, Math.min(maxAmount, getAmount()), transaction) : 0;
                    }
                }).iterator();
    }
}
