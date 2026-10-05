package org.marj4n.irons_spellbooks.ironlib.statue.block.statue_block.decorative;

import org.marj4n.irons_spellbooks.client.item.NativeClientItemExtensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;

public class DecorativeStatueItemClientExtensions implements NativeClientItemExtensions {
    private final Supplier<Block> block;
    private final Supplier<BlockEntityType<DecorativeStatueBlockEntity>> type;
    public DecorativeStatueItemClientExtensions(Supplier<Block> block, Supplier<BlockEntityType<DecorativeStatueBlockEntity>> type) {
        this.block=block; this.type=type;
    }
    @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        var client = Minecraft.getInstance();
        return new BlockEntityWithoutLevelRenderer(client.getBlockEntityRenderDispatcher(), client.getEntityModels()) {
            @Override public void renderByItem(net.minecraft.world.item.ItemStack stack,
                    net.minecraft.world.item.ItemDisplayContext context, com.mojang.blaze3d.vertex.PoseStack poses,
                    net.minecraft.client.renderer.MultiBufferSource buffers, int light, int overlay) {
                var tile = new DecorativeStatueBlockEntity(type.get(), net.minecraft.core.BlockPos.ZERO, block.get().defaultBlockState());
                client.getBlockEntityRenderDispatcher().renderItem(tile, poses, buffers, light, overlay);
            }
        };
    }
}
