package org.marj4n.irons_spellbooks.client.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
public interface NativeClientItemExtensions {
    default BlockEntityWithoutLevelRenderer getCustomRenderer() { return null; }
}
