package org.marj4n.irons_spellbooks.network;

import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.Executor;

/** Context supplied after a packet has been decoded on the network thread. */
public final class FabricPacketContext {
    private final Executor executor;
    private final @Nullable ServerPlayer sender;

    public FabricPacketContext(Executor executor, @Nullable ServerPlayer sender) {
        this.executor = Objects.requireNonNull(executor);
        this.sender = sender;
    }

    public @Nullable ServerPlayer getSender() {
        return sender;
    }

    public void enqueueWork(Runnable work) {
        executor.execute(Objects.requireNonNull(work));
    }
}
