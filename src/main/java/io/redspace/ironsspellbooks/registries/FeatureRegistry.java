package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;

import java.util.List;

public class FeatureRegistry {

    public static void register() {
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addFeature(
                net.fabricmc.fabric.api.biome.v1.BiomeSelectors.tag(BiomeTags.IS_OVERWORLD),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registries.PLACED_FEATURE, IronsSpellbooks.id("ore_mithril_placement")));
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn(
                net.fabricmc.fabric.api.biome.v1.BiomeSelectors.tag(BiomeTags.IS_OVERWORLD),
                net.minecraft.world.entity.MobCategory.MONSTER, EntityRegistry.NECROMANCER.get(), 18, 1, 1);
    }

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARCANE_DEBRIS_FEATURE = configuredFeatureResourceKey("ore_arcane_debris");
    public static final ResourceKey<PlacedFeature> ARCANE_DEBRIS_PLACEMENT = placedFeatureResourceKey("ore_arcane_debris");

    public static void bootstrapConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruleTestArcaneDebris = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> arcaneDebrisList = List.of(OreConfiguration.target(ruleTestArcaneDebris, BlockRegistry.ARCANE_DEBRIS.get().defaultBlockState()));
        FeatureUtils.register(context, ARCANE_DEBRIS_FEATURE, Feature.ORE, new OreConfiguration(arcaneDebrisList, 3, 1.0f));
    }

    public static void bootstrapPlacedFeature(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holderArcaneDebris = holdergetter.getOrThrow(ARCANE_DEBRIS_FEATURE);
        List<PlacementModifier> list = List.of(CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(-38)), BiomeFilter.biome());
        PlacementUtils.register(context, ARCANE_DEBRIS_PLACEMENT, holderArcaneDebris, list);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(final String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(IronsSpellbooks.MODID, name));
    }

    private static ResourceKey<PlacedFeature> placedFeatureResourceKey(final String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(IronsSpellbooks.MODID, name));
    }

    private static HolderSet<Biome> tag(final HolderGetter<Biome> holderGetter, final TagKey<Biome> key) {
        return holderGetter.getOrThrow(key);
    }

    private static HolderSet<PlacedFeature> feature(final HolderGetter<PlacedFeature> holderGetter, final ResourceKey<PlacedFeature> feature) {
        return HolderSet.direct(holderGetter.getOrThrow(feature));
    }
}