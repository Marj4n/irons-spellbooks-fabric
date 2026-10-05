package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.worldgen.IndividualTerrainStructurePoolElement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;

import java.util.function.Supplier;


public class StructureElementRegistry {

    public static final DeferredRegistry<StructurePoolElementType<?>> STRUCTURE_POOL_ELEMENT_DEFERRED_REGISTER = DeferredRegistry.create(Registries.STRUCTURE_POOL_ELEMENT, IronsSpellbooks.MODID);

    public static void register() {
        STRUCTURE_POOL_ELEMENT_DEFERRED_REGISTER.register();
    }

    public static final Supplier<StructurePoolElementType<IndividualTerrainStructurePoolElement>> INDIVIDUAL_TERRAIN_ELEMENT = STRUCTURE_POOL_ELEMENT_DEFERRED_REGISTER.register("individual_terrain_element", () -> () -> IndividualTerrainStructurePoolElement.CODEC);
}
