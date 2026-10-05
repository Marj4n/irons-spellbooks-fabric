package org.marj4n.irons_spellbooks.fluid;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class FluidAppearances {
    private static final Map<ResourceLocation, Supplier<FluidAppearance>> APPEARANCES = new ConcurrentHashMap<>();
    private static java.util.function.Function<Fluid, FluidAppearance> fallback;
    private FluidAppearances() {}
    public static void register(ResourceLocation id, Supplier<FluidAppearance> appearance) {
        if (APPEARANCES.putIfAbsent(id, appearance) != null) throw new IllegalStateException("Duplicate fluid appearance " + id);
    }
    public static void setFallback(java.util.function.Function<Fluid, FluidAppearance> provider) { fallback = provider; }
    public static FluidAppearance of(Fluid fluid) {
        var supplier = APPEARANCES.get(BuiltInRegistries.FLUID.getKey(fluid));
        if (supplier != null) return supplier.get();
        if (fallback != null) return fallback.apply(fluid);
        throw new IllegalStateException("Fluid appearance requested before client initialization");
    }
}
