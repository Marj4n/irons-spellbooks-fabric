package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.recipe_types.alchemist_cauldron.BrewAlchemistCauldronRecipe;
import io.redspace.ironsspellbooks.recipe_types.alchemist_cauldron.EmptyAlchemistCauldronRecipe;
import io.redspace.ironsspellbooks.recipe_types.alchemist_cauldron.FillAlchemistCauldronRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;

public class RecipeRegistry {
    private static final DeferredRegistry<RecipeType<?>> RECIPE_TYPES = DeferredRegistry.create(Registries.RECIPE_TYPE, IronsSpellbooks.MODID);
    private static final DeferredRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegistry.create(Registries.RECIPE_SERIALIZER, IronsSpellbooks.MODID);

    public static void register() {
        RECIPE_TYPES.register();
        RECIPE_SERIALIZERS.register();
    }

    public static final RegistryEntry<RecipeSerializer<?>> ALCHEMIST_CAULDRON_FILL_SERIALIZER = RECIPE_SERIALIZERS.register("alchemist_cauldron_fill", FillAlchemistCauldronRecipe.Serializer::new);
    public static final RegistryEntry<RecipeType<FillAlchemistCauldronRecipe>> ALCHEMIST_CAULDRON_FILL_TYPE = RECIPE_TYPES.register("alchemist_cauldron_fill",
            () -> new RecipeType<FillAlchemistCauldronRecipe>() {
                @Override
                public String toString() {
//                    return registry.toString();
                    return "irons_spellbooks:alchemist_cauldron_fill";
                }
            });

    public static final RegistryEntry<RecipeSerializer<?>> ALCHEMIST_CAULDRON_EMPTY_SERIALIZER = RECIPE_SERIALIZERS.register("alchemist_cauldron_empty", EmptyAlchemistCauldronRecipe.Serializer::new);
    public static final RegistryEntry<RecipeType<EmptyAlchemistCauldronRecipe>> ALCHEMIST_CAULDRON_EMPTY_TYPE = RECIPE_TYPES.register("alchemist_cauldron_empty",
            () -> new RecipeType<EmptyAlchemistCauldronRecipe>() {
                @Override
                public String toString() {
                    return "irons_spellbooks:alchemist_cauldron_empty";
                }
            });

    public static final RegistryEntry<RecipeSerializer<?>> ALCHEMIST_CAULDRON_BREW_SERIALIZER
            = RECIPE_SERIALIZERS.register("alchemist_cauldron_brew", BrewAlchemistCauldronRecipe.Serializer::new);
    public static final RegistryEntry<RecipeType<BrewAlchemistCauldronRecipe>> ALCHEMIST_CAULDRON_BREW_TYPE
            = RECIPE_TYPES.register("alchemist_cauldron_brew", () -> new RecipeType<BrewAlchemistCauldronRecipe>() {
        @Override
        public String toString() {
            return "irons_spellbooks:alchemist_cauldron_brew";
//            return registry.toString();
        }
    });
//    public static final RegistryEntry<RecipeSerializer<?>> SMITHING_TRANSFORM_NO_ADDITION_SERIALIZER =
//            RECIPE_SERIALIZERS.register("smithing_transform_no_addition", NoAdditionSmithingTransformRecipe.Serializer::new);

}
