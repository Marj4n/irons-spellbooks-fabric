package org.marj4n.irons_spellbooks.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

/** Context supplied by the native Fabric item/block/entity interaction callbacks. */
public abstract class InteractionEvents extends SpellPlayerEvent {
    private final InteractionHand hand;
    private InteractionResult result = InteractionResult.PASS;
    protected InteractionEvents(Player player, InteractionHand hand) {
        super(player); this.hand = hand;
    }
    public InteractionHand getHand() { return hand; }
    public ItemStack getItemStack() { return getEntity().getItemInHand(hand); }
    public Level getLevel() { return getEntity().level(); }
    public InteractionResult getCancellationResult() { return result; }
    public void setCancellationResult(InteractionResult value) { result = value; }
    @Override public boolean isCancelable() { return true; }

    public static final class RightClickItem extends InteractionEvents {
        public RightClickItem(Player player, InteractionHand hand) { super(player, hand); }
    }
    public static final class RightClickBlock extends InteractionEvents {
        private final BlockHitResult hit;
        public RightClickBlock(Player player, InteractionHand hand, BlockHitResult hit) {
            super(player, hand); this.hit = hit;
        }
        public BlockHitResult getHitVec() { return hit; }
        public BlockPos getPos() { return hit.getBlockPos(); }
    }
    public static final class EntityInteractSpecific extends InteractionEvents {
        private final Entity target;
        public EntityInteractSpecific(Player player, InteractionHand hand, Entity target) {
            super(player, hand); this.target = target;
        }
        public Entity getTarget() { return target; }
    }
}
