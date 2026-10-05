package io.redspace.ironsspellbooks.network.casting;

import io.redspace.ironsspellbooks.api.backwards_compat.CustomPacketPayload;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ICastData;
import io.redspace.ironsspellbooks.api.spells.ICastDataSerializable;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.fabricmc.api.EnvType;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;

import java.util.function.Supplier;

public class OnClientCastPacket implements CustomPacketPayload {
    String spellId;
    int level;
    CastSource castSource;
    ICastData castData;

    public OnClientCastPacket(String spellId, int level, CastSource castSource, ICastData castData) {
        this.spellId = spellId;
        this.level = level;
        this.castSource = castSource;
        this.castData = castData;
    }

    public OnClientCastPacket(FriendlyByteBuf buf) {
        spellId = buf.readUtf();
        level = buf.readInt();
        castSource = buf.readEnum(CastSource.class);
        if (buf.readBoolean()) {
            var tmp = SpellRegistry.getSpell(spellId).getEmptyCastData();
            tmp.readFromBuffer(buf);
            castData = tmp;
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(spellId);
        buf.writeInt(level);
        buf.writeEnum(castSource);
        if (castData instanceof ICastDataSerializable castDataSerializable) {
            buf.writeBoolean(true);
            castDataSerializable.writeToBuffer(buf);
        } else {
            buf.writeBoolean(false);
        }
    }

    public boolean handle(Supplier<FabricPacketContext> supplier) {
        FabricPacketContext ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ClientSpellCastHelper.handleClientboundOnClientCast(spellId, level, castSource, castData);
        });
        return true;
    }
}