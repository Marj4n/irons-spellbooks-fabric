package io.redspace.ironsspellbooks.network.casting;

import io.redspace.ironsspellbooks.api.backwards_compat.CustomPacketPayload;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import net.minecraft.network.FriendlyByteBuf;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;

import java.util.UUID;
import java.util.function.Supplier;

public class OnCastStartedPacket implements CustomPacketPayload {
    private String spellId;
    private int spellLevel;
    private UUID castingEntityId;

    public OnCastStartedPacket(UUID castingEntityId, String spellId, int spellLevel) {
        this.spellId = spellId;
        this.spellLevel = spellLevel;
        this.castingEntityId = castingEntityId;
    }

    public OnCastStartedPacket(FriendlyByteBuf buf) {
        spellId = buf.readUtf();
        spellLevel = buf.readInt();
        castingEntityId = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(spellId);
        buf.writeInt(spellLevel);
        buf.writeUUID(castingEntityId);
    }

    public boolean handle(Supplier<FabricPacketContext> supplier) {
        FabricPacketContext ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ClientSpellCastHelper.handleClientBoundOnCastStarted(castingEntityId, spellId, spellLevel);
        });
        return true;
    }
}
