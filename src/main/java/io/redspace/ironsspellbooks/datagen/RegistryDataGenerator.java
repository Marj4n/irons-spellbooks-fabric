package io.redspace.ironsspellbooks.datagen;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends io.github.fabricators_of_create.porting_lib.data.DatapackBuiltinEntriesProvider {
    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output,provider,Set.of(IronsSpellbooks.MODID));
    }
}
