package org.marj4n.irons_spellbooks.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Supplier;

/** A stable lazy reference shared by registration and dependent content. */
public final class RegistryEntry<T> implements Supplier<T> {
    private final ResourceLocation id;
    private final ResourceKey<T> key;
    private final Supplier<? extends T> factory;
    private T value;
    private boolean constructing;

    RegistryEntry(ResourceLocation id, ResourceKey<? extends Registry<T>> registry,
                  Supplier<? extends T> factory) {
        this.id = Objects.requireNonNull(id);
        this.key = ResourceKey.create(registry, id);
        this.factory = Objects.requireNonNull(factory);
    }

    public ResourceLocation getId() { return id; }
    public ResourceKey<T> getKey() { return key; }
    public boolean isPresent() { return value != null; }

    @Override
    public synchronized T get() {
        if (value != null) return value;
        if (constructing) throw new IllegalStateException("Circular content construction: " + id);
        constructing = true;
        try {
            value = Objects.requireNonNull(factory.get(), "Registry factory returned null: " + id);
            return value;
        } finally {
            constructing = false;
        }
    }
}
