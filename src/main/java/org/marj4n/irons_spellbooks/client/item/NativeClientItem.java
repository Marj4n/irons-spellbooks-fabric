package org.marj4n.irons_spellbooks.client.item;

import java.util.function.Consumer;
/** Opt-in client renderer registration called by the Fabric client initializer. */
public interface NativeClientItem {
    default void initializeClient(Consumer<NativeClientItemExtensions> consumer) {}
}
