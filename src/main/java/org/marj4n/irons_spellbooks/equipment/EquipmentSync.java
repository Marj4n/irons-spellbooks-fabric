package org.marj4n.irons_spellbooks.equipment;
import java.util.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
/** Detects changes to spell containers owned by other Trinkets mods too. */
public final class EquipmentSync {
    private static final Map<ServerPlayer,Map<String,ItemStack>> snapshots=new WeakHashMap<>();
    public static void forget(ServerPlayer player){snapshots.remove(player);}
    public static void tick(ServerPlayer player){
        Map<String,ItemStack> current=new TreeMap<>();
        EquipmentApi.getCuriosInventory(player).ifPresent(inventory -> {
            for(var equipped:inventory.findCurios(io.redspace.ironsspellbooks.api.spells.ISpellContainer::isSpellContainer))
                current.put(equipped.slotContext().identifier()+"/"+equipped.slotContext().index(),equipped.stack().copy());
        });
        var previous=snapshots.put(player,current);
        if(previous==null){if(current.isEmpty())return;}
        else if(previous.keySet().equals(current.keySet()) && current.entrySet().stream().allMatch(entry -> ItemStack.matches(entry.getValue(),previous.get(entry.getKey()))))return;
        io.redspace.ironsspellbooks.setup.PacketDistributor.sendToPlayer(player,new io.redspace.ironsspellbooks.network.EquipmentChangedPacket());
    }
}
