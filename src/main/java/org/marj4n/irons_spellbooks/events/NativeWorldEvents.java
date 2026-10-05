package org.marj4n.irons_spellbooks.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class NativeWorldEvents {
    public interface AllowDestroy { boolean allow(Entity entity, BlockPos pos, BlockState state); }
    public static final Event<AllowDestroy> ALLOW_ENTITY_DESTROY = EventFactory.createArrayBacked(AllowDestroy.class,
            listeners -> (entity,pos,state) -> {
                for (var listener:listeners) if (!listener.allow(entity,pos,state)) return false;
                return true;
            });
    public static boolean allowPlayerBreak(net.minecraft.server.level.ServerLevel level,
            net.minecraft.server.level.ServerPlayer player, BlockPos pos) {
        var state=level.getBlockState(pos);
        if (!level.mayInteract(player,pos) || player.blockActionRestricted(level,pos,player.gameMode.getGameModeForPlayer())) return false;
        if (!net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.BEFORE.invoker()
                .beforeBlockBreak(level,player,pos,state,level.getBlockEntity(pos))) return false;
        var event=new io.github.fabricators_of_create.porting_lib.event.common.BlockEvents.BreakEvent(level,pos,state,player);
        event.post();
        return !event.isCanceled();
    }
}
