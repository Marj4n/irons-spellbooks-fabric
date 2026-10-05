package io.redspace.ironsspellbooks.registries;

import com.mojang.serialization.Codec;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.particle.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;

import java.util.function.Supplier;


public class ParticleRegistry {
    public static final DeferredRegistry<ParticleType<?>> PARTICLE_TYPES = DeferredRegistry.create(Registries.PARTICLE_TYPE, IronsSpellbooks.MODID);

    public static void register() {
        PARTICLE_TYPES.register();
    }

    /*
    To Create Particle:
    - textures + json
    - particle class
    - register it here
    - add it to particle helper
    - register it in client setup
     */

    public static final Supplier<SimpleParticleType> BLOOD_PARTICLE = PARTICLE_TYPES.register("blood", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> WISP_PARTICLE = PARTICLE_TYPES.register("wisp", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> BLOOD_GROUND_PARTICLE = PARTICLE_TYPES.register("blood_ground", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> SNOWFLAKE_PARTICLE = PARTICLE_TYPES.register("snowflake", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> ELECTRICITY_PARTICLE = PARTICLE_TYPES.register("electricity", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> UNSTABLE_ENDER_PARTICLE = PARTICLE_TYPES.register("unstable_ender", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> DRAGON_FIRE_PARTICLE = PARTICLE_TYPES.register("dragon_fire", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> FIRE_PARTICLE = PARTICLE_TYPES.register("fire", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> EMBER_PARTICLE = PARTICLE_TYPES.register("embers", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> SIPHON_PARTICLE = PARTICLE_TYPES.register("spell", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> ACID_PARTICLE = PARTICLE_TYPES.register("acid", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> ACID_BUBBLE_PARTICLE = PARTICLE_TYPES.register("acid_bubble", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> SNOW_DUST = PARTICLE_TYPES.register("snow_dust", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> RING_SMOKE_PARTICLE = PARTICLE_TYPES.register("ring_smoke", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final RegistryEntry<ParticleType<FogParticleOptions>> FOG_PARTICLE = PARTICLE_TYPES.register("fog", () -> new ParticleType<FogParticleOptions>(true, FogParticleOptions.DESERIALIZER) {
        public Codec<FogParticleOptions> codec() {
            return FogParticleOptions.CODEC;
        }
    });
    public static final RegistryEntry<ParticleType<ShockwaveParticleOptions>> SHOCKWAVE_PARTICLE = PARTICLE_TYPES.register("shockwave", () -> new ParticleType<>(false, ShockwaveParticleOptions.DESERIALIZER) {
        public Codec<ShockwaveParticleOptions> codec() {
            return ShockwaveParticleOptions.CODEC;
        }
    });
    public static final RegistryEntry<ParticleType<ZapParticleOption>> ZAP_PARTICLE = PARTICLE_TYPES.register("zap", () -> new ParticleType<ZapParticleOption>(false, ZapParticleOption.DESERIALIZER) {
        public Codec<ZapParticleOption> codec() {
            return ZapParticleOption.CODEC;
        }
    });
    public static final Supplier<SimpleParticleType> FIREFLY_PARTICLE = PARTICLE_TYPES.register("firefly", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> PORTAL_FRAME_PARTICLE = PARTICLE_TYPES.register("portal_frame", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final RegistryEntry<ParticleType<BlastwaveParticleOptions>> BLASTWAVE_PARTICLE = PARTICLE_TYPES.register("blastwave", () -> new ParticleType<BlastwaveParticleOptions>(true, BlastwaveParticleOptions.DESERIALIZER) {
        public Codec<BlastwaveParticleOptions> codec() {
            return BlastwaveParticleOptions.CODEC;
        }
    });
    public static final RegistryEntry<ParticleType<SparkParticleOptions>> SPARK_PARTICLE = PARTICLE_TYPES.register("spark", () -> new ParticleType<SparkParticleOptions>(true, SparkParticleOptions.DESERIALIZER) {
        public Codec<SparkParticleOptions> codec() {
            return SparkParticleOptions.CODEC;
        }
    });
    public static final Supplier<SimpleParticleType> CLEANSE_PARTICLE = PARTICLE_TYPES.register("cleanse", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<ParticleType<FlameStrikeParticleOptions>> FLAME_STRIKE_PARTICLE = PARTICLE_TYPES.register("flame_strike", () -> new ParticleType<>(true, FlameStrikeParticleOptions.DESERIALIZER) {
        public Codec<FlameStrikeParticleOptions> codec() {
            return FlameStrikeParticleOptions.CODEC;
        }
    });
    public static final Supplier<SimpleParticleType> EMBEROUS_ASH_PARTICLE = PARTICLE_TYPES.register("emberous_ash", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> FIERY_SMOKE_PARTICLE = PARTICLE_TYPES.register("fiery_smoke", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(true));
    public static final Supplier<ParticleType<EnderSlashParticleOptions>> ENDER_SLASH_PARTICLE = PARTICLE_TYPES.register("ender_slash", () -> new ParticleType<>(true, EnderSlashParticleOptions.DESERIALIZER) {
        public Codec<EnderSlashParticleOptions> codec() {
            return EnderSlashParticleOptions.CODEC;
        }
    });

    public static final Supplier<ParticleType<TraceParticleOptions>> TRACE_PARTICLE = PARTICLE_TYPES.register("trace", () -> new ParticleType<>(true, TraceParticleOptions.DESERIALIZER) {
        public Codec<TraceParticleOptions> codec() {
            return TraceParticleOptions.CODEC;
        }

    });
    public static final Supplier<SimpleParticleType> FALLING_SPARKLE_PARTICLE = PARTICLE_TYPES.register("falling_sparkle", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(true));

    public static final Supplier<ParticleType<FallingBlockParticleOption>> FALLING_BLOCK_PARTICLE = PARTICLE_TYPES.register("falling_block", () -> new ParticleType<>(true, FallingBlockParticleOption.DESERIALIZER) {
        public Codec<FallingBlockParticleOption> codec() {
            return FallingBlockParticleOption.codec((ParticleType<FallingBlockParticleOption>) this);
        }
    });

    public static final Supplier<ParticleType<SwirlingParticleOptions>> SWIRLING_PARTICLE = PARTICLE_TYPES.register("swirling_particle", () -> new ParticleType<>(false, SwirlingParticleOptions.DESERIALIZER) {
        public Codec<SwirlingParticleOptions> codec() {
            return SwirlingParticleOptions.MAP_CODEC;
        }
    });

    public static final Supplier<ParticleType<SoulfireRayParticleOptions>> SOULFIRE_RAY_PARTICLE = PARTICLE_TYPES.register("soulfire_ray", () -> new ParticleType<>(false, SoulfireRayParticleOptions.DESERIALIZER) {
        @Override
        public Codec<SoulfireRayParticleOptions> codec() {
            return SoulfireRayParticleOptions.CODEC;
        }
    });
    public static final Supplier<SimpleParticleType> SOUL_FIRE_PARTICLE = PARTICLE_TYPES.register("soul_fire", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<SimpleParticleType> TRIAL_OMEN_PARTICLE = PARTICLE_TYPES.register("trial_omen", () -> net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(false));
    public static final Supplier<ParticleType<TintedBubblePopParticleOptions>> TINTED_BUBBLE_POP_PARTICLE = PARTICLE_TYPES.register("tinted_bubble_pop", () -> new ParticleType<>(false, TintedBubblePopParticleOptions.DESERIALIZER) {
        public Codec<TintedBubblePopParticleOptions> codec() {
            return TintedBubblePopParticleOptions.CODEC;
        }
    });

}
