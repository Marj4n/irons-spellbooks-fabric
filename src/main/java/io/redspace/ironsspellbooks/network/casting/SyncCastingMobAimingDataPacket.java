package io.redspace.ironsspellbooks.network.casting;

import io.redspace.ironsspellbooks.api.backwards_compat.CustomPacketPayload;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import io.redspace.ironsspellbooks.spells.CastingMobAimingData;
import net.minecraft.network.FriendlyByteBuf;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;

import java.util.function.Supplier;

public class SyncCastingMobAimingDataPacket implements CustomPacketPayload {
    private final int entityId;
    private final CastingMobAimingData aimingData;

    public SyncCastingMobAimingDataPacket(int entityId, CastingMobAimingData aimingData) {
        this.entityId = entityId;
        this.aimingData = aimingData;
    }

    public SyncCastingMobAimingDataPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.aimingData = new CastingMobAimingData();
        this.aimingData.readFromBuffer(buf);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        aimingData.writeToBuffer(buf);
    }

    public boolean handle(Supplier<FabricPacketContext> supplier) {
        FabricPacketContext ctx = supplier.get();
        ctx.enqueueWork(() -> ClientSpellCastHelper.handleCastingMobAimingData(entityId, aimingData));
        return true;
    }
}
