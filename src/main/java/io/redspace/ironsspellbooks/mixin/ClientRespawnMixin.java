package io.redspace.ironsspellbooks.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientRespawnMixin {
    @Inject(method = "handleRespawn", at = @At("RETURN"))
    private void irons_spellbooks$respawn(net.minecraft.network.protocol.game.ClientboundRespawnPacket packet, CallbackInfo ci) {
        var player = Minecraft.getInstance().player;
        if (player != null) io.redspace.ironsspellbooks.player.ClientPlayerEvents.onRespawn(player);
    }
}
