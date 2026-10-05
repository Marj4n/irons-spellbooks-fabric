package org.marj4n.irons_spellbooks.client;

import io.redspace.ironsspellbooks.player.ClientPlayerEvents;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.particle.ClientShieldHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import io.github.fabricators_of_create.porting_lib.client_events.event.client.MovementInputUpdateCallback;
import io.github.fabricators_of_create.porting_lib.event.client.CameraSetupCallback;
import io.github.fabricators_of_create.porting_lib.event.client.FogEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;

public final class ClientRuntimeHooks {
    private ClientRuntimeHooks() {}
    public static void register() {
        ClientEntityEvents.ENTITY_UNLOAD.register((entity, level) -> {
            if (entity instanceof org.marj4n.irons_spellbooks.events.WorldRemovalListener listener) listener.onRemovedFromWorld();
        });
        net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.LAST.register(
                io.redspace.ironsspellbooks.particle.FallingBlockParticle::globalrender);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ClientPlayerEvents.onClientLogin(client.player);
            ClientPlayerEvents.onPlayerLogin(client.player);
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            org.marj4n.irons_spellbooks.ironlib.ChronicleData.update(java.util.List.of());
            ClientPlayerEvents.onPlayerLogOut(client.player);
            ClientShieldHelper.onPlayerLogOut();
            CameraShakeManager.clientCameraShakeData.clear();
        });
        ClientTickEvents.START_CLIENT_TICK.register(client -> io.redspace.ironsspellbooks.api.util.MusicManager.tick());
        FogEvents.SET_COLOR.register(io.redspace.ironsspellbooks.api.util.FogManager::fog);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEvents.onPlayerTick(client.player);
            CameraShakeManager.handleCameraShake();
        });
        LivingEntityEvents.LivingTickEvent.TICK.register(event -> {
            if (event.getEntity().level().isClientSide) ClientPlayerEvents.onClientEntityTick(event);
        });
        ClientEntityEvents.ENTITY_LOAD.register((entity, level) -> ClientShieldHelper.trackShieldCreated(entity));
        ClientEntityEvents.ENTITY_UNLOAD.register((entity, level) -> ClientShieldHelper.trackShieldRemoved(entity));
        MovementInputUpdateCallback.EVENT.register(ClientPlayerEvents::onCalculatePlayerSpeed);
        ItemTooltipCallback.EVENT.register(ClientPlayerEvents::imbuedWeaponTooltips);
        ItemTooltipCallback.EVENT.register(ClientPlayerEvents::customPotionTooltips);
        FogEvents.SET_COLOR.register(ClientPlayerEvents::changeFogColor);
        CameraSetupCallback.EVENT.register(info -> {
            CameraShakeManager.handleCameraShake(info);
            return false;
        });
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> ClientPlayerEvents.onChatReceived(message));
    }
}
