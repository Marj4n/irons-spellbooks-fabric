package org.marj4n.irons_spellbooks.client.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.marj4n.irons_spellbooks.network.FabricChannel;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;

/** Client-only transport is loaded exclusively by the Fabric client entrypoint. */
@Environment(EnvType.CLIENT)
public final class FabricChannelClient {
    private FabricChannelClient() {}

    public static void initialize(FabricChannel channel) {
        channel.installClientTransport((id, payload) -> {
            if (!ClientPlayNetworking.canSend(id)) {
                payload.release();
                throw new IllegalStateException("The server does not support Iron's Fabric spell packets");
            }
            ClientPlayNetworking.send(id, payload);
        });
        ClientPlayNetworking.registerGlobalReceiver(channel.id(), (client, listener, buffer, response) ->
                channel.handle(buffer, FabricChannel.Direction.PLAY_TO_CLIENT,
                        new FabricPacketContext(client, null)));
    }
}
