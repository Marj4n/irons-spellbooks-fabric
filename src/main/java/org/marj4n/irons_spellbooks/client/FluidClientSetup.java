package org.marj4n.irons_spellbooks.client;

import io.github.fabricators_of_create.porting_lib.fluids.BaseFlowingFluid;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import org.marj4n.irons_spellbooks.fluid.FluidAppearance;
import org.marj4n.irons_spellbooks.fluid.FluidAppearances;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;

public final class FluidClientSetup {
    private FluidClientSetup() {}
    public static void register() {
        for (var fluid : BuiltInRegistries.FLUID) {
            if (!BuiltInRegistries.FLUID.getKey(fluid).getNamespace().equals("irons_spellbooks")) continue;
            FluidAppearance appearance = FluidAppearance.of(fluid);
            FluidRenderHandlerRegistry.INSTANCE.register(fluid, new SimpleFluidRenderHandler(
                    appearance.getStillTexture(), appearance.getFlowingTexture(), appearance.getTintColor()));
            FluidVariantRendering.register(fluid, new FluidVariantRenderHandler() {
                @Override public int getColor(FluidVariant variant, BlockAndTintGetter level, BlockPos pos) {
                    return appearance.getTintColor(new FluidStack(variant, 1));
                }
            });
        }
        FluidAppearances.setFallback(fluid -> new FluidAppearance() {
            @Override public ResourceLocation getStillTexture() {
                return FluidVariantRendering.getSprites(FluidVariant.of(fluid))[0].contents().name();
            }
            @Override public ResourceLocation getFlowingTexture() {
                var sprites = FluidVariantRendering.getSprites(FluidVariant.of(fluid));
                return sprites[Math.min(1, sprites.length - 1)].contents().name();
            }
            @Override public int getTintColor(FluidStack stack) {
                return FluidVariantRendering.getColor(FluidVariant.of(fluid, stack.getTag()));
            }
            @Override public int getTintColor(FluidState state, BlockAndTintGetter level, BlockPos pos) {
                return FluidVariantRendering.getColor(FluidVariant.of(fluid), level, pos);
            }
        });
    }
}
