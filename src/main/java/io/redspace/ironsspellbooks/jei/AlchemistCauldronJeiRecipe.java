package io.redspace.ironsspellbooks.jei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;

import java.util.List;

public record AlchemistCauldronJeiRecipe(Ingredient itemIn, FluidStack fluidIn, List<FluidStack> results,
                                         ItemStack resultByproduct) {

}
