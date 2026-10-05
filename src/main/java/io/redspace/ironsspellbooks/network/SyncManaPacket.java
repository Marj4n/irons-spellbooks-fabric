package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.api.backwards_compat.CustomPacketPayload;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.network.FriendlyByteBuf;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;

import java.util.function.Supplier;


public class SyncManaPacket implements CustomPacketPayload {
    private int playerMana = 0;
    private MagicData playerMagicData = null;


    public SyncManaPacket(MagicData playerMagicData) {
        //Server side only
        this.playerMagicData = playerMagicData;
    }

    public SyncManaPacket(FriendlyByteBuf buf) {
        playerMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt((int) playerMagicData.getMana());
    }

    public boolean handle(Supplier<FabricPacketContext> supplier) {
        FabricPacketContext ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ClientMagicData.setMana(playerMana);
        });
        return true;
    }
}
