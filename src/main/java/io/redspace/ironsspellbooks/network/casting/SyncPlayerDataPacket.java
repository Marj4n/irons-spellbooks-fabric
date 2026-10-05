package io.redspace.ironsspellbooks.network.casting;


import io.redspace.ironsspellbooks.api.backwards_compat.CustomPacketPayload;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.network.FriendlyByteBuf;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;

import java.util.function.Supplier;


public class SyncPlayerDataPacket implements CustomPacketPayload {
    SyncedSpellData syncedSpellData;

    public SyncPlayerDataPacket(SyncedSpellData playerSyncedData) {
        this.syncedSpellData = playerSyncedData;
    }

    public SyncPlayerDataPacket(FriendlyByteBuf buf) {
        syncedSpellData = SyncedSpellData.read(buf);
    }

    public void toBytes(FriendlyByteBuf buf) {
        SyncedSpellData.write(buf, syncedSpellData);
    }

    public boolean handle(Supplier<FabricPacketContext> supplier) {
        FabricPacketContext ctx = supplier.get();

        ctx.enqueueWork(() -> {
            ClientMagicData.handlePlayerSyncedData(syncedSpellData);
        });

        return true;
    }
}