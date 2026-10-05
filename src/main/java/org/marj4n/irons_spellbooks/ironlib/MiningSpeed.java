package org.marj4n.irons_spellbooks.ironlib;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.core.registries.Registries;
import io.redspace.ironsspellbooks.api.attribute.MagicPercentAttribute;

public final class MiningSpeed {
    private static final DeferredRegistry<Attribute> REGISTRY=DeferredRegistry.create(Registries.ATTRIBUTE,"irons_lib");
    public static final RegistryEntry<Attribute> MINING_SPEED=REGISTRY.register("mining_speed",
            () -> new MagicPercentAttribute("attribute.name.irons_lib.mining_speed",1,0,1024).setSyncable(true));
    public static void register() { REGISTRY.register(); }
}
