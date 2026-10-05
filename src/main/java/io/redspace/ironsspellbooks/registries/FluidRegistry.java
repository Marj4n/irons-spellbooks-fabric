package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.fluids.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.BaseFlowingFluid;
import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluidRegistry {
    private static final DeferredRegistry<Fluid> FLUIDS = DeferredRegistry.create(Registries.FLUID, IronsSpellbooks.MODID);
    private static final DeferredRegistry<FluidType> FLUID_TYPES = DeferredRegistry.create(PortingLibFluids.FLUID_TYPES, IronsSpellbooks.MODID);

    public static void register() {
        FLUID_TYPES.register();
        FLUIDS.register();
    }

    public static final RegistryEntry<FluidType> BLOOD_TYPE = registerType("blood", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleClientFluidType(IronsSpellbooks.id("block/blood")));

    public static final RegistryEntry<FluidType> TIMELESS_SLURRY_TYPE = registerType("timeless_slurry", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleClientFluidType(IronsSpellbooks.id("block/timeless_slurry")));

    public static final RegistryEntry<FluidType> COMMON_INK_TYPE = registerType("common_ink", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("irons_spellbooks", "block/milk_still"), 0xFF222222));

    public static final RegistryEntry<FluidType> UNCOMMON_INK_TYPE = registerType("uncommon_ink", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("irons_spellbooks", "block/milk_still"), 0xFF124300));

    public static final RegistryEntry<FluidType> RARE_INK_TYPE = registerType("rare_ink", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("irons_spellbooks", "block/milk_still"), 0xFF0f3844));

    public static final RegistryEntry<FluidType> EPIC_INK_TYPE = registerType("epic_ink", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("irons_spellbooks", "block/milk_still"), 0xFFa52ea0));

    public static final RegistryEntry<FluidType> LEGENDARY_INK_TYPE = registerType("legendary_ink", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("irons_spellbooks", "block/milk_still"), 0xFFfcaf1c));

    public static final RegistryEntry<FluidType> POTION_FLUID_TYPE = registerType("potion", () -> new PotionFluidType(FluidType.Properties.create()), () -> new PotionClientFluidType(new ResourceLocation("block/water_still")));

    public static final RegistryEntry<FluidType> OAKSKIN_ELIXIR_TYPE = registerType("oakskin_elixir", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("block/water_still"), MobEffectRegistry.OAKSKIN.get().getColor()));

    public static final RegistryEntry<FluidType> GREATER_OAKSKIN_ELIXIR_TYPE = registerType("greater_oakskin_elixir", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("block/water_still"), MobEffectRegistry.OAKSKIN.get().getColor()));

    public static final RegistryEntry<FluidType> EVASION_ELIXIR_TYPE = registerType("evasion_elixir", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("block/water_still"), MobEffectRegistry.EVASION.get().getColor()));

    public static final RegistryEntry<FluidType> GREATER_EVASION_ELIXIR_TYPE = registerType("greater_evasion_elixir", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("block/water_still"), MobEffectRegistry.EVASION.get().getColor()));

    public static final RegistryEntry<FluidType> INVISIBILITY_ELIXIR_TYPE = registerType("invisibility_elixir", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("block/water_still"), MobEffectRegistry.TRUE_INVISIBILITY.get().getColor()));

    public static final RegistryEntry<FluidType> GREATER_INVISIBILITY_ELIXIR_TYPE = registerType("greater_invisibility_elixir", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("block/water_still"), MobEffectRegistry.TRUE_INVISIBILITY.get().getColor()));

    public static final RegistryEntry<FluidType> GREATER_HEALING_ELIXIR_TYPE = registerType("greater_healing_elixir", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("block/water_still"), MobEffects.HEAL.getColor()));

    public static final RegistryEntry<FluidType> ICE_VENOM_TYPE = registerType("ice_venom", () -> new FluidType(FluidType.Properties.create()), () -> new SimpleTintedClientFluidType(new ResourceLocation("irons_spellbooks", "block/milk_still"), 0x73baba));



    public static final RegistryEntry<Fluid> BLOOD = registerNoop("blood", BLOOD_TYPE);
    public static final RegistryEntry<Fluid> COMMON_INK = registerNoop("common_ink", COMMON_INK_TYPE);
    public static final RegistryEntry<Fluid> UNCOMMON_INK = registerNoop("uncommon_ink", UNCOMMON_INK_TYPE);
    public static final RegistryEntry<Fluid> RARE_INK = registerNoop("rare_ink", RARE_INK_TYPE);
    public static final RegistryEntry<Fluid> EPIC_INK = registerNoop("epic_ink", EPIC_INK_TYPE);
    public static final RegistryEntry<Fluid> LEGENDARY_INK = registerNoop("legendary_ink", LEGENDARY_INK_TYPE);
    public static final RegistryEntry<Fluid> POTION_FLUID = registerNoop("potion", POTION_FLUID_TYPE);
    public static final RegistryEntry<Fluid> OAKSKIN_ELIXIR_FLUID = registerNoop("oakskin_elixir", OAKSKIN_ELIXIR_TYPE);
    public static final RegistryEntry<Fluid> GREATER_OAKSKIN_ELIXIR_FLUID = registerNoop("greater_oakskin_elixir", GREATER_OAKSKIN_ELIXIR_TYPE);
    public static final RegistryEntry<Fluid> INVISIBILITY_ELIXIR_FLUID = registerNoop("invisibility_elixir", INVISIBILITY_ELIXIR_TYPE);
    public static final RegistryEntry<Fluid> GREATER_INVISIBILITY_ELIXIR_FLUID = registerNoop("greater_invisibility_elixir", GREATER_INVISIBILITY_ELIXIR_TYPE);
    public static final RegistryEntry<Fluid> EVASION_ELIXIR_FLUID = registerNoop("evasion_elixir", EVASION_ELIXIR_TYPE);
    public static final RegistryEntry<Fluid> GREATER_EVASION_ELIXIR_FLUID = registerNoop("greater_evasion_elixir", GREATER_EVASION_ELIXIR_TYPE);
    public static final RegistryEntry<Fluid> GREATER_HEALING_ELIXIR_FLUID = registerNoop("greater_healing_elixir", GREATER_HEALING_ELIXIR_TYPE);
    public static final RegistryEntry<Fluid> TIMELESS_SLURRY_FLUID = registerNoop("timeless_slurry", TIMELESS_SLURRY_TYPE);
    public static final RegistryEntry<Fluid> ICE_VENOM_FLUID = registerNoop("ice_venom", ICE_VENOM_TYPE);

    private static RegistryEntry<FluidType> registerType(String name, Supplier<FluidType> type,
            Supplier<org.marj4n.irons_spellbooks.fluid.FluidAppearance> appearance) {
        org.marj4n.irons_spellbooks.fluid.FluidAppearances.register(IronsSpellbooks.id(name), appearance);
        return FLUID_TYPES.register(name, type);
    }

    private static RegistryEntry<Fluid> registerNoop(String name, Supplier<FluidType> fluidType) {
        Supplier<Fluid> source = () -> net.minecraft.core.registries.BuiltInRegistries.FLUID.get(IronsSpellbooks.id(name));
        return FLUIDS.register(name, () -> new NoopFluid(
                new BaseFlowingFluid.Properties(fluidType, source, source).bucket(() -> Items.AIR)));
    }
}
