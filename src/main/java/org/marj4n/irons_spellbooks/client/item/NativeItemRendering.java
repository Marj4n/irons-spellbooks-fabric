package org.marj4n.irons_spellbooks.client.item;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import java.util.IdentityHashMap;
import java.util.Map;

public final class NativeItemRendering {
    private NativeItemRendering() {}
    public static void register() {
        for (Item item : BuiltInRegistries.ITEM) {
            if (!(item instanceof NativeClientItem source)) continue;
            source.initializeClient(extension -> {
                // Construct on the first render, after Minecraft's render managers exist.
                var renderer = new java.util.concurrent.atomic.AtomicReference<net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer>();
                BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, context, matrices, buffers, light, overlay) -> {
                    var active = renderer.get();
                    if (active == null) {
                        active = extension.getCustomRenderer();
                        if (active == null) return;
                        renderer.set(active);
                    }
                    active.renderByItem(stack, context, matrices, buffers, light, overlay);
                });
            });
        }
    }
}
