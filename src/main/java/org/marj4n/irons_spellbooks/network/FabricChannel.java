package org.marj4n.irons_spellbooks.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/** Native Fabric transport for the existing spell packet codecs. */
public final class FabricChannel {
    public enum Direction { PLAY_TO_CLIENT, PLAY_TO_SERVER }

    public interface ClientTransport {
        void send(ResourceLocation channel, FriendlyByteBuf payload);
    }

    private final ResourceLocation channel;
    private final Map<Integer, Binding<?>> byId = new HashMap<>();
    private final Map<Class<?>, Binding<?>> byClass = new HashMap<>();
    private ClientTransport clientTransport;
    private boolean registered;

    public FabricChannel(ResourceLocation channel) {
        this.channel = Objects.requireNonNull(channel);
    }

    public ResourceLocation id() {
        return channel;
    }

    public <T> Binding<T> messageBuilder(Class<T> type, int id, Direction direction) {
        if (registered) throw new IllegalStateException("Packet registration is closed");
        if (id < 0) throw new IllegalArgumentException("Negative packet ID");
        return new Binding<>(type, id, direction);
    }

    public void registerServerReceiver() {
        if (registered) throw new IllegalStateException("Channel registered twice: " + channel);
        registered = true;
        ServerPlayNetworking.registerGlobalReceiver(channel, (server, player, listener, buffer, response) -> {
            try {
                handle(buffer, Direction.PLAY_TO_SERVER, new FabricPacketContext(server, player));
            } catch (RuntimeException malformed) {
                // Reject invalid IDs/directions/codecs before invoking gameplay handlers.
                server.execute(() -> listener.disconnect(Component.literal("Invalid Iron's spell packet")));
            }
        });
    }

    public void installClientTransport(ClientTransport transport) {
        if (clientTransport != null) throw new IllegalStateException("Client transport already installed");
        clientTransport = Objects.requireNonNull(transport);
    }

    public void handle(FriendlyByteBuf buffer, Direction receivedDirection, FabricPacketContext context) {
        int id = buffer.readVarInt();
        Binding<?> binding = byId.get(id);
        if (binding == null || binding.direction != receivedDirection) {
            throw new IllegalArgumentException("Invalid spell packet ID/direction: " + id);
        }
        binding.receive(buffer, context);
    }

    private FriendlyByteBuf encode(Object message, Direction direction) {
        Binding<?> binding = byClass.get(message.getClass());
        if (binding == null || binding.direction != direction) {
            throw new IllegalArgumentException("Unregistered spell packet/direction: " + message.getClass());
        }
        FriendlyByteBuf buffer = PacketByteBufs.create();
        try {
            buffer.writeVarInt(binding.packetId);
            binding.encode(message, buffer);
            return buffer;
        } catch (RuntimeException failure) {
            buffer.release();
            throw failure;
        }
    }

    public void sendToServer(Object message) {
        if (clientTransport == null) throw new IllegalStateException("Client transport is unavailable");
        clientTransport.send(channel, encode(message, Direction.PLAY_TO_SERVER));
    }

    public void sendToPlayer(ServerPlayer player, Object message) {
        if (!ServerPlayNetworking.canSend(player, channel)) return;
        ServerPlayNetworking.send(player, channel, encode(message, Direction.PLAY_TO_CLIENT));
    }

    public void sendToAllPlayers(MinecraftServer server, Object message) {
        for (ServerPlayer player : PlayerLookup.all(server)) sendToPlayer(player, message);
    }

    public void sendToTracking(Entity entity, Object message) {
        for (ServerPlayer player : PlayerLookup.tracking(entity)) sendToPlayer(player, message);
    }

    public final class Binding<T> {
        private final Class<T> type;
        private final int packetId;
        private final Direction direction;
        private Function<FriendlyByteBuf, T> decoder;
        private BiConsumer<T, FriendlyByteBuf> encoder;
        private BiFunction<T, Supplier<FabricPacketContext>, Boolean> consumer;

        private Binding(Class<T> type, int packetId, Direction direction) {
            this.type = Objects.requireNonNull(type);
            this.packetId = packetId;
            this.direction = Objects.requireNonNull(direction);
        }

        public Binding<T> decoder(Function<FriendlyByteBuf, T> decoder) {
            this.decoder = Objects.requireNonNull(decoder);
            return this;
        }

        public Binding<T> encoder(BiConsumer<T, FriendlyByteBuf> encoder) {
            this.encoder = Objects.requireNonNull(encoder);
            return this;
        }

        public Binding<T> consumerMainThread(BiFunction<T, Supplier<FabricPacketContext>, Boolean> consumer) {
            this.consumer = Objects.requireNonNull(consumer);
            return this;
        }

        public void add() {
            if (registered) throw new IllegalStateException("Packet registration is closed");
            Objects.requireNonNull(decoder, "Missing decoder");
            Objects.requireNonNull(encoder, "Missing encoder");
            Objects.requireNonNull(consumer, "Missing handler");
            if (byId.containsKey(packetId) || byClass.containsKey(type)) {
                throw new IllegalStateException("Duplicate spell packet: " + type.getName());
            }
            byId.put(packetId, this);
            byClass.put(type, this);
        }

        private void receive(FriendlyByteBuf buffer, FabricPacketContext context) {
            T message = decoder.apply(buffer);
            // Upstream handlers enqueue their gameplay mutations through the context.
            consumer.apply(message, () -> context);
        }

        private void encode(Object message, FriendlyByteBuf buffer) {
            encoder.accept(type.cast(message), buffer);
        }
    }
}
