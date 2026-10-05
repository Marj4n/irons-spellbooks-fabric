package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.Potions;



import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;


public class PotionRegistry {
    public static final DeferredRegistry<Potion> POTIONS = DeferredRegistry.create(Registries.POTION, IronsSpellbooks.MODID);

    public static void register() {
        POTIONS.register();
        addRecipes();
    }

    public static final RegistryEntry<Potion> INSTANT_MANA_ONE = POTIONS.register("instant_mana_one", () -> new Potion("mana", new MobEffectInstance(MobEffectRegistry.INSTANT_MANA.get())));
    public static final RegistryEntry<Potion> INSTANT_MANA_TWO = POTIONS.register("instant_mana_two", () -> new Potion("mana", new MobEffectInstance(MobEffectRegistry.INSTANT_MANA.get(), 0, 1)));
    public static final RegistryEntry<Potion> INSTANT_MANA_THREE = POTIONS.register("instant_mana_three", () -> new Potion("mana", new MobEffectInstance(MobEffectRegistry.INSTANT_MANA.get(), 0, 2)));
    public static final RegistryEntry<Potion> INSTANT_MANA_FOUR = POTIONS.register("instant_mana_four", () -> new Potion("mana", new MobEffectInstance(MobEffectRegistry.INSTANT_MANA.get(), 0, 3)));

    private static void addRecipes() {
        //IronsSpellbooks.LOGGER.debug("adding potion recipes");


            FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(ItemRegistry.ARCANE_ESSENCE.get()), PotionRegistry.INSTANT_MANA_ONE.get());
            FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.INSTANT_MANA_ONE.get(), Ingredient.of(Items.GLOWSTONE_DUST), PotionRegistry.INSTANT_MANA_TWO.get());
            FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.INSTANT_MANA_TWO.get(), Ingredient.of(Items.AMETHYST_SHARD), PotionRegistry.INSTANT_MANA_THREE.get());
            FabricBrewingRecipeRegistry.registerPotionRecipe(PotionRegistry.INSTANT_MANA_THREE.get(), Ingredient.of(Items.AMETHYST_CLUSTER), PotionRegistry.INSTANT_MANA_FOUR.get());
//            addContainerMix(ItemRegistry.BLOOD_VIAL.get(), ItemRegistry.HOGSKIN.get(), ItemRegistry.ARCANE_ESSENCE.get());

    }
}
