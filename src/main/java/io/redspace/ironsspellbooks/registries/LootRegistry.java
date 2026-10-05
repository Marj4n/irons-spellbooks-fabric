package io.redspace.ironsspellbooks.registries;

import com.mojang.serialization.Codec;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.loot.AppendLootModifier;
import io.redspace.ironsspellbooks.loot.FurledMapLootFunction;
import io.redspace.ironsspellbooks.loot.RandomizeRingEnhancementFunction;
import io.redspace.ironsspellbooks.loot.RandomizeSpellFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;

import java.util.function.Supplier;


public class LootRegistry {
    public static final DeferredRegistry<LootItemFunctionType> LOOT_FUNCTIONS = DeferredRegistry.create(Registries.LOOT_FUNCTION_TYPE, IronsSpellbooks.MODID);
    public static final DeferredRegistry<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegistry.create(io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS_KEY, IronsSpellbooks.MODID);

    public static void register() {
        LOOT_FUNCTIONS.register();
        LOOT_MODIFIER_SERIALIZERS.register();
    }

    public static final RegistryEntry<LootItemFunctionType> RANDOMIZE_SPELL_FUNCTION = LOOT_FUNCTIONS.register("randomize_spell",
            () -> new LootItemFunctionType(new RandomizeSpellFunction.Serializer()));
    public static final RegistryEntry<LootItemFunctionType> RANDOMIZE_SPELL_RING_FUNCTION = LOOT_FUNCTIONS.register("randomize_ring_enhancement",
            () -> new LootItemFunctionType(new RandomizeRingEnhancementFunction.Serializer()));
    public static final RegistryEntry<LootItemFunctionType> SET_FURLED_MAP_FUNCTION = LOOT_FUNCTIONS.register("set_furled_map",
            () -> new LootItemFunctionType(new FurledMapLootFunction.Serializer()));

    public static final Supplier<Codec<? extends IGlobalLootModifier>> APPEND_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("append_loot", AppendLootModifier.CODEC);
//    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> REPLACE_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("replace_loot", ReplaceLootModifier.CODEC);
}
