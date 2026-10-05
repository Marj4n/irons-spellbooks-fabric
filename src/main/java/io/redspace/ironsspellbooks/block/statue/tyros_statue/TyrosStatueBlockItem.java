package io.redspace.ironsspellbooks.block.statue.tyros_statue;

import org.marj4n.irons_spellbooks.ironlib.statue.block.statue_block.decorative.DecorativeStatueItemClientExtensions;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.marj4n.irons_spellbooks.client.item.NativeClientItemExtensions;

import java.util.function.Consumer;

public class TyrosStatueBlockItem extends BlockItem implements org.marj4n.irons_spellbooks.client.item.NativeClientItem {
    public TyrosStatueBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initializeClient(Consumer<NativeClientItemExtensions> consumer) {
        consumer.accept(new DecorativeStatueItemClientExtensions(BlockRegistry.TYROS_STATUE_BLOCK::get, BlockRegistry.TYROS_STATUE_BLOCK_ENTITY));
    }
}
