package org.marj4n.irons_spellbooks.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/** Deterministic vanilla/Fabric content registration, without a Forge event bus. */
public final class DeferredRegistry<T> {
    private final ResourceKey<? extends Registry<T>> key;
    private final String namespace;
    private final Map<ResourceLocation, RegistryEntry<? extends T>> entries = new LinkedHashMap<>();
    private Registry<T> registry;
    private boolean registered;

    private DeferredRegistry(ResourceKey<? extends Registry<T>> key, String namespace) {
        this.key = Objects.requireNonNull(key);
        this.namespace = Objects.requireNonNull(namespace);
    }

    public static <T> DeferredRegistry<T> create(ResourceKey<? extends Registry<T>> key, String namespace) {
        return new DeferredRegistry<>(key, namespace);
    }

    public static <T> DeferredRegistry<T> create(Registry<T> registry, String namespace) {
        DeferredRegistry<T> deferred = new DeferredRegistry<>(registry.key(), namespace);
        deferred.registry = registry;
        return deferred;
    }

    public <R extends T> RegistryEntry<R> register(String path, Supplier<? extends R> factory) {
        if (registered) throw new IllegalStateException("Registry already populated: " + key.location());
        ResourceLocation id = new ResourceLocation(namespace, path);
        @SuppressWarnings("unchecked")
        ResourceKey<? extends Registry<R>> entryKey = (ResourceKey<? extends Registry<R>>) (ResourceKey<?>) key;
        RegistryEntry<R> entry = new RegistryEntry<>(id, entryKey, factory);
        if (entries.putIfAbsent(id, entry) != null) throw new IllegalArgumentException("Duplicate content: " + id);
        return entry;
    }

    public Supplier<Registry<T>> makeRegistry(Class<T> valueType) {
        if (registry != null) throw new IllegalStateException("Registry already resolved: " + key.location());
        if (BuiltInRegistries.REGISTRY.containsKey(key.location())) {
            throw new IllegalStateException("Registry already exists: " + key.location());
        }
        registry = FabricRegistryBuilder.createSimple(valueType, key.location())
                .attribute(RegistryAttribute.SYNCED).buildAndRegister();
        return () -> registry;
    }

    @SuppressWarnings("unchecked")
    private Registry<T> registry() {
        if (registry == null) {
            registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location());
            if (registry == null) throw new IllegalStateException("Registry not available: " + key.location());
        }
        return registry;
    }

    public void register() {
        if (registered) throw new IllegalStateException("Registry registered twice: " + key.location());
        Registry<T> target = registry();
        for (RegistryEntry<? extends T> entry : entries.values()) {
            if (target.containsKey(entry.getId())) throw new IllegalStateException("Content already exists: " + entry.getId());
            Registry.register(target, entry.getId(), entry.get());
        }
        registered = true;
    }

    public Collection<RegistryEntry<? extends T>> getEntries() {
        return Collections.unmodifiableCollection(entries.values());
    }
}
