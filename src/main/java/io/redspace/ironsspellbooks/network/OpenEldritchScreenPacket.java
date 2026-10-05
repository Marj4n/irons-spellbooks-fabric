package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.api.backwards_compat.CustomPacketPayload;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;

import java.util.function.Supplier;


public class OpenEldritchScreenPacket implements CustomPacketPayload {
    private final InteractionHand hand;

    public OpenEldritchScreenPacket(InteractionHand pHand) {
        this.hand = pHand;
    }

    public OpenEldritchScreenPacket(FriendlyByteBuf buf) {
        this.hand = buf.readEnum(InteractionHand.class);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(this.hand);
    }

    public boolean handle(Supplier<FabricPacketContext> supplier) {
        FabricPacketContext ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ClientSpellCastHelper.openEldritchResearchScreen(this.hand);
        });
        return true;
    }
}