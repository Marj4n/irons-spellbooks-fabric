package org.marj4n.irons_spellbooks.ironlib;
import java.util.*;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;
public record SyncChroniclePacket(List<ChronicleEntry> entries) {
    public SyncChroniclePacket { entries=List.copyOf(entries); }
    public SyncChroniclePacket(FriendlyByteBuf buffer) { this(read(buffer)); }
    private static List<ChronicleEntry> read(FriendlyByteBuf buffer) {
        int count=buffer.readVarInt(); if(count<0 || count>10000) throw new IllegalArgumentException("Invalid Chronicle entry count");
        List<ChronicleEntry> entries=new ArrayList<>(count);
        for(int i=0;i<count;i++) entries.add(new ChronicleEntry(buffer.readUtf(256),buffer.readVarInt(),buffer.readVarInt()));
        return entries;
    }
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeVarInt(entries.size()); for(var entry:entries) {
            buffer.writeUtf(entry.displayName(),256); buffer.writeVarInt(entry.pledge()); buffer.writeVarInt(entry.bookCategory());
        }
    }
    public boolean handle(Supplier<FabricPacketContext> context) {
        context.get().enqueueWork(() -> ChronicleData.update(entries)); return true;
    }
}
