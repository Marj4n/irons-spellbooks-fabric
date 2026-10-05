package io.redspace.ironsspellbooks.api.backwards_compat;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.alchemy.PotionBrewing;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;

public class FluidHelper {

    public static boolean isSameFluidSameComponents(FluidStack a, FluidStack b) {
        return a.getFluid() == b.getFluid() && java.util.Objects.equals(a.getTag(), b.getTag());
    }

    public static FluidStack copyWithAmount(FluidStack fluidStack, long amount) {
        var stack = fluidStack.copy();
        stack.setAmount(amount);
        return stack;
    }

//    public static boolean hasPotionContentions(FluidStack stack) {
//        return
//    }

    public static boolean hasPotionContents(ItemStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().contains("Potion");
    }

    public static boolean isWater(ItemStack stack) {
        return hasPotionContents(stack) && PotionUtils.getPotion(stack) == Potions.WATER;
    }

    public static boolean isBrewingIngredient(ItemStack stack, Level level) {
        return PotionBrewing.isIngredient(stack);
    }

    /**
     * @param base    Base is the existing item attempting to be transformed (ie water bottle)
     * @param reagent Reagent is the acting brewing ingredient (ie nether wart)
     * @return Returns brewing result (without affecting input itemstacks) or ItemStack.EMPTY
     */
    public static ItemStack getNonDestructiveBrewingResult(ItemStack base, ItemStack reagent, Level level) {
        return PotionBrewing.mix(reagent, base);
    }

    public static boolean hasPotionContents(FluidStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().contains("Potion");
    }

    public static Potion getPotionContents(FluidStack stack) {
        return hasPotionContents(stack) ? PotionUtils.getPotion(stack.getOrCreateTag()) : Potions.EMPTY;
    }

    public static void setPotionContents(FluidStack stack, Potion potion) {
        stack.getOrCreateTag().putString("Potion", BuiltInRegistries.POTION.getKey(potion).toString());
    }

    public static ItemStack createItemStack(Item item, Holder<Potion> potion) {
        var stack = new ItemStack(item);
        PotionUtils.setPotion(stack, potion.value());
        return stack;
    }
}
