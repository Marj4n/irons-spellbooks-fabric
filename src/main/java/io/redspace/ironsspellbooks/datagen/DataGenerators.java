package io.redspace.ironsspellbooks.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public class DataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();
        var registries = pack.addProvider(RegistryDataGenerator::new);
        pack.addProvider((output, lookup) -> new DamageTypeTagGenerator(output, lookup));
        pack.addProvider((net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack.Factory<PackMetadataGenerator>)(output -> new PackMetadataGenerator(output).add(PackMetadataSection.TYPE,
                new PackMetadataSection(Component.literal("Resources for Iron's Spells N Spellbooks"), 15))));
    }
    @Override public void buildRegistry(net.minecraft.core.RegistrySetBuilder builder) {
        builder.add(net.minecraft.core.registries.Registries.CONFIGURED_FEATURE, io.redspace.ironsspellbooks.registries.FeatureRegistry::bootstrapConfiguredFeature)
            .add(net.minecraft.core.registries.Registries.PLACED_FEATURE, io.redspace.ironsspellbooks.registries.FeatureRegistry::bootstrapPlacedFeature)
            .add(net.minecraft.core.registries.Registries.DAMAGE_TYPE, io.redspace.ironsspellbooks.damage.ISSDamageTypes::bootstrap)
            .add(io.redspace.ironsspellbooks.registries.UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, io.redspace.ironsspellbooks.registries.UpgradeOrbTypeRegistry::bootstrap);
    }
}
