package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.api.backwards_compat.CustomPacketPayload;
import io.redspace.ironsspellbooks.block.scroll_forge.ScrollForgeTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;

import java.util.function.Supplier;


public class ScrollForgeSelectSpellPacket implements CustomPacketPayload {
    private final BlockPos pos;
    private final String spellId;

    public ScrollForgeSelectSpellPacket(BlockPos pos, String spellId) {
        this.pos = pos;
        this.spellId = spellId;
    }

    public ScrollForgeSelectSpellPacket(FriendlyByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        pos = new BlockPos(x, y, z);
        spellId = buf.readUtf();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeUtf(spellId);
    }

    public boolean handle(Supplier<FabricPacketContext> supplier) {
        FabricPacketContext ctx = supplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getSender().level().getBlockEntity(this.pos) instanceof ScrollForgeTile scrollForgeTile) {
                scrollForgeTile.setRecipeSpell(this.spellId);
            }
        });
        return true;
    }
}
