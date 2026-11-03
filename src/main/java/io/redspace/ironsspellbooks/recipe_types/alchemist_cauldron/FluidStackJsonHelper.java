package io.redspace.ironsspellbooks.recipe_types.alchemist_cauldron;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;

/**
 * 1.20.1 compatibility reader for Iron's legacy Forge-style FluidStack JSON.
 *
 * Iron's bundled recipes use keys such as FluidName / Amount / Tag. Porting Lib's
 * 1.20.1 FluidStack codec does not safely decode that legacy shape on Fabric and
 * can fail inside DataResult with an NPE while recipes are reloaded. Keep support
 * for that original recipe format here, while retaining codec fallback for modern
 * datapacks that already use Porting Lib's native format.
 */
final class FluidStackJsonHelper {
    private FluidStackJsonHelper() {}

    static FluidStack read(JsonElement element) {
        if (element != null && element.isJsonObject()) {
            JsonObject json = element.getAsJsonObject();
            if (json.has("FluidName")) {
                ResourceLocation id =
                        new ResourceLocation(GsonHelper.getAsString(json, "FluidName"));
                if (!BuiltInRegistries.FLUID.containsKey(id)) {
                    throw new JsonParseException("Unknown fluid '" + id + "'");
                }

                Fluid fluid = BuiltInRegistries.FLUID.get(id);
                int amount = GsonHelper.getAsInt(json, "Amount", 0);
                if (amount < 0) {
                    throw new JsonParseException(
                            "Fluid amount must be non-negative for '" + id + "': " + amount);
                }

                CompoundTag tag = null;
                if (json.has("Tag") && !json.get("Tag").isJsonNull()) {
                    try {
                        // JSON object syntax is accepted by Mojang's SNBT parser and preserves
                        // the potion/bottle metadata used by Iron's alchemist-cauldron recipes.
                        tag = TagParser.parseTag(json.get("Tag").toString());
                    } catch (Exception exception) {
                        throw new JsonParseException(
                                "Invalid fluid Tag for '" + id + "'", exception);
                    }
                }

                return tag == null
                        ? new FluidStack(fluid, amount)
                        : new FluidStack(fluid, amount, tag);
            }
        }

        // Compatibility with external datapacks already authored for Porting Lib.
        return FluidStack.CODEC
                .decode(JsonOps.INSTANCE, element)
                .getOrThrow(false, IronsSpellbooks.LOGGER::error)
                .getFirst();
    }
}
