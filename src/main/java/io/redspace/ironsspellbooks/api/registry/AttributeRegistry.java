package io.redspace.ironsspellbooks.api.registry;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.attribute.MagicPercentAttribute;
import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;


public class AttributeRegistry {

    private static final DeferredRegistry<Attribute> ATTRIBUTES = DeferredRegistry.create(Registries.ATTRIBUTE, IronsSpellbooks.MODID);

    public static void register() {
        ATTRIBUTES.register();
    }

    public static final RegistryEntry<Attribute> MAX_MANA = ATTRIBUTES.register("max_mana", () -> (new MagicRangedAttribute("attribute.irons_spellbooks.max_mana", 100.0D, 0.0D, 1000000.0D).setSyncable(true)));
    public static final RegistryEntry<Attribute> MANA_REGEN = ATTRIBUTES.register("mana_regen", () -> (new MagicPercentAttribute("attribute.irons_spellbooks.mana_regen", 1.0D, 0.0D, 100.0D).setSyncable(true)));
    public static final RegistryEntry<Attribute> COOLDOWN_REDUCTION = ATTRIBUTES.register("cooldown_reduction", () -> (new MagicPercentAttribute("attribute.irons_spellbooks.cooldown_reduction", 1.0D, -100.0D, 100.0D).setSyncable(true)));
    public static final RegistryEntry<Attribute> SPELL_POWER = ATTRIBUTES.register("spell_power", () -> (new MagicPercentAttribute("attribute.irons_spellbooks.spell_power", 1.0D, -100, 100.0D).setSyncable(true)));
    public static final RegistryEntry<Attribute> SPELL_RESIST = ATTRIBUTES.register("spell_resist", () -> (new MagicPercentAttribute("attribute.irons_spellbooks.spell_resist", 1.0D, -100, 100.0D).setSyncable(true)));
    public static final RegistryEntry<Attribute> CAST_TIME_REDUCTION = ATTRIBUTES.register("cast_time_reduction", () -> (new MagicPercentAttribute("attribute.irons_spellbooks.cast_time_reduction", 1.0D, -100, 100.0D).setSyncable(true)));
    public static final RegistryEntry<Attribute> SUMMON_DAMAGE = ATTRIBUTES.register("summon_damage", () -> (new MagicPercentAttribute("attribute.irons_spellbooks.summon_damage", 1.0D, -100, 100.0D).setSyncable(true)));
    public static final RegistryEntry<Attribute> CASTING_MOVESPEED = ATTRIBUTES.register("casting_movespeed", () -> (new MagicPercentAttribute("attribute.irons_spellbooks.casting_movespeed", 1, 0, 100.0D).setSyncable(true)));

    public static final RegistryEntry<Attribute> FIRE_MAGIC_RESIST = newResistanceAttribute("fire");
    public static final RegistryEntry<Attribute> ICE_MAGIC_RESIST = newResistanceAttribute("ice");
    public static final RegistryEntry<Attribute> LIGHTNING_MAGIC_RESIST = newResistanceAttribute("lightning");
    public static final RegistryEntry<Attribute> HOLY_MAGIC_RESIST = newResistanceAttribute("holy");
    public static final RegistryEntry<Attribute> ENDER_MAGIC_RESIST = newResistanceAttribute("ender");
    public static final RegistryEntry<Attribute> BLOOD_MAGIC_RESIST = newResistanceAttribute("blood");
    public static final RegistryEntry<Attribute> EVOCATION_MAGIC_RESIST = newResistanceAttribute("evocation");
    public static final RegistryEntry<Attribute> NATURE_MAGIC_RESIST = newResistanceAttribute("nature");
    public static final RegistryEntry<Attribute> ELDRITCH_MAGIC_RESIST = newResistanceAttribute("eldritch");

    public static final RegistryEntry<Attribute> FIRE_SPELL_POWER = newPowerAttribute("fire");
    public static final RegistryEntry<Attribute> ICE_SPELL_POWER = newPowerAttribute("ice");
    public static final RegistryEntry<Attribute> LIGHTNING_SPELL_POWER = newPowerAttribute("lightning");
    public static final RegistryEntry<Attribute> HOLY_SPELL_POWER = newPowerAttribute("holy");
    public static final RegistryEntry<Attribute> ENDER_SPELL_POWER = newPowerAttribute("ender");
    public static final RegistryEntry<Attribute> BLOOD_SPELL_POWER = newPowerAttribute("blood");
    public static final RegistryEntry<Attribute> EVOCATION_SPELL_POWER = newPowerAttribute("evocation");
    public static final RegistryEntry<Attribute> NATURE_SPELL_POWER = newPowerAttribute("nature");
    public static final RegistryEntry<Attribute> ELDRITCH_SPELL_POWER = newPowerAttribute("eldritch");

    public static net.minecraft.world.entity.ai.attributes.AttributeSupplier createDefaults() {
        var builder = net.minecraft.world.entity.ai.attributes.AttributeSupplier.builder();
        ATTRIBUTES.getEntries().forEach(attribute -> builder.add(attribute.get()));
        builder.add(org.marj4n.irons_spellbooks.ironlib.MiningSpeed.MINING_SPEED.get());
        return builder.build();
    }

    private static RegistryEntry<Attribute> newResistanceAttribute(String id) {
        return (RegistryEntry<Attribute>) ATTRIBUTES.register(id + "_magic_resist", () -> (new MagicPercentAttribute("attribute.irons_spellbooks." + id + "_magic_resist", 1.0D, -100, 100).setSyncable(true)));
    }

    private static RegistryEntry<Attribute> newPowerAttribute(String id) {
        return ATTRIBUTES.register(id + "_spell_power", () -> (new MagicPercentAttribute("attribute.irons_spellbooks." + id + "_spell_power", 1.0D, -100, 100).setSyncable(true)));
    }
}
