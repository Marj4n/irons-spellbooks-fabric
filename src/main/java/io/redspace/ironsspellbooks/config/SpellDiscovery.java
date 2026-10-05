package io.redspace.ironsspellbooks.config;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import java.util.List;
/** Fabric discovers registered spells from the synced spell registry, including addons. */
@Deprecated(forRemoval=true)
public final class SpellDiscovery {
    public static List<AbstractSpell> getSpellsForConfig() {
        return io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get().stream().toList();
    }
}
