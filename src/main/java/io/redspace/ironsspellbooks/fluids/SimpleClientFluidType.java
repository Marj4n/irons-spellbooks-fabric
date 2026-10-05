package io.redspace.ironsspellbooks.fluids;

import net.minecraft.resources.ResourceLocation;
import org.marj4n.irons_spellbooks.fluid.FluidAppearance;
import org.jetbrains.annotations.NotNull;

public class SimpleClientFluidType implements FluidAppearance {
    private final ResourceLocation texture;

    public SimpleClientFluidType(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public @NotNull ResourceLocation getStillTexture() {
        return texture;
    }

    @Override
    public @NotNull ResourceLocation getFlowingTexture() {
        return texture;
    }


}
