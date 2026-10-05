package io.redspace.ironsspellbooks.api.registry;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.spells.NoneSpell;
import io.redspace.ironsspellbooks.spells.blood.*;
import io.redspace.ironsspellbooks.spells.eldritch.*;
import io.redspace.ironsspellbooks.spells.ender.*;
import io.redspace.ironsspellbooks.spells.evocation.*;
import io.redspace.ironsspellbooks.spells.fire.*;
import io.redspace.ironsspellbooks.spells.holy.*;
import io.redspace.ironsspellbooks.spells.ice.*;
import io.redspace.ironsspellbooks.spells.lightning.*;
import io.redspace.ironsspellbooks.spells.nature.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;


import org.marj4n.irons_spellbooks.registry.RegistryEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SpellRegistry {
    public static final ResourceKey<Registry<AbstractSpell>> SPELL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(IronsSpellbooks.MODID, "spells"));
    private static final DeferredRegistry<AbstractSpell> SPELLS = DeferredRegistry.create(SPELL_REGISTRY_KEY, IronsSpellbooks.MODID);

    public static final Supplier<Registry<AbstractSpell>> REGISTRY = SPELLS.makeRegistry(AbstractSpell.class);

    private static final NoneSpell noneSpell = new NoneSpell();
    private static final Map<SchoolType, List<AbstractSpell>> SCHOOLS_TO_SPELLS = new HashMap<>();

    public static void register() {
        SPELLS.register();
    }

//    public static void registerRegistry(NewRegistryEvent event) {
//        IronsSpellbooks.LOGGER.debug("SpellRegistry.registerRegistry");
//        event.register(REGISTRY);
//    }

    public static NoneSpell none() {
        return noneSpell;
    }

    private static RegistryEntry<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static AbstractSpell getSpell(String spellId) {
        return getSpell(new ResourceLocation(spellId));
    }

    public static List<AbstractSpell> getEnabledSpells() {
        return SpellRegistry.REGISTRY.get().stream().toList()
                .stream()
                .filter(AbstractSpell::isEnabled)
                .toList();
    }

    public static List<AbstractSpell> getSpellsForSchool(SchoolType schoolType) {
        return SCHOOLS_TO_SPELLS.computeIfAbsent(schoolType, (school) -> SpellRegistry.REGISTRY.get().stream().toList()
                .stream()
                .filter(spell -> spell.getSchoolType() == school).collect(Collectors.toList()));
    }

    public static AbstractSpell getSpell(ResourceLocation resourceLocation) {
        var spell = REGISTRY.get().get(resourceLocation);
        if (spell == null) {
            return noneSpell;
        }
        return spell;
    }

    public static void onConfigReload() {
        SCHOOLS_TO_SPELLS.clear();
    }

    //TODO: should the none spell be registered?

    // BLOOD
    public static final RegistryEntry<AbstractSpell> ACUPUNCTURE_SPELL = registerSpell(new AcupunctureSpell());
    public static final RegistryEntry<AbstractSpell> BLOOD_NEEDLES_SPELL = registerSpell(new BloodNeedlesSpell());
    public static final RegistryEntry<AbstractSpell> BLOOD_SLASH_SPELL = registerSpell(new BloodSlashSpell());
    public static final RegistryEntry<AbstractSpell> BLOOD_STEP_SPELL = registerSpell(new BloodStepSpell());
    public static final RegistryEntry<AbstractSpell> DEVOUR_SPELL = registerSpell(new DevourSpell());
    public static final RegistryEntry<AbstractSpell> HEARTSTOP_SPELL = registerSpell(new HeartstopSpell());
    public static final RegistryEntry<AbstractSpell> RAISE_DEAD_SPELL = registerSpell(new RaiseDeadSpell());
    public static final RegistryEntry<AbstractSpell> RAY_OF_SIPHONING_SPELL = registerSpell(new RayOfSiphoningSpell());
    public static final RegistryEntry<AbstractSpell> WITHER_SKULL_SPELL = registerSpell(new WitherSkullSpell());
    public static final RegistryEntry<AbstractSpell> SACRIFICE_SPELL = registerSpell(new SacrificeSpell());

    // ENDER
    public static final RegistryEntry<AbstractSpell> COUNTERSPELL_SPELL = registerSpell(new CounterspellSpell());
    public static final RegistryEntry<AbstractSpell> DRAGON_BREATH_SPELL = registerSpell(new DragonBreathSpell());
    public static final RegistryEntry<AbstractSpell> EVASION_SPELL = registerSpell(new EvasionSpell());
    public static final RegistryEntry<AbstractSpell> MAGIC_ARROW_SPELL = registerSpell(new MagicArrowSpell());
    public static final RegistryEntry<AbstractSpell> MAGIC_MISSILE_SPELL = registerSpell(new MagicMissileSpell());
    public static final RegistryEntry<AbstractSpell> STARFALL_SPELL = registerSpell(new StarfallSpell());
    public static final RegistryEntry<AbstractSpell> TELEPORT_SPELL = registerSpell(new TeleportSpell());
    public static final RegistryEntry<AbstractSpell> SUMMON_ENDER_CHEST_SPELL = registerSpell(new SummonEnderChestSpell());
    public static final RegistryEntry<AbstractSpell> RECALL_SPELL = registerSpell(new RecallSpell());
    public static final RegistryEntry<AbstractSpell> PORTAL_SPELL = registerSpell(new PortalSpell());
    public static final RegistryEntry<AbstractSpell> ECHOING_STRIKES_SPELL = registerSpell(new EchoingStrikesSpell());
    public static final RegistryEntry<AbstractSpell> BLACK_HOLE_SPELL = registerSpell(new BlackHoleSpell());
    public static final RegistryEntry<AbstractSpell> SUMMON_SWORDS = registerSpell(new SummonSwordsSpell());
    public static final RegistryEntry<AbstractSpell> SHADOW_SLASH = registerSpell(new ShadowSlashSpell());
    public static final RegistryEntry<AbstractSpell> ARCANE_SHACKLE_SPELL = registerSpell(new ArcaneShackleSpell());
    public static final RegistryEntry<AbstractSpell> GRAVITY_FISSURE = registerSpell(new GravityFissureSpell());

    // EVOCATION
    public static final RegistryEntry<AbstractSpell> CHAIN_CREEPER_SPELL = registerSpell(new ChainCreeperSpell());
    public static final RegistryEntry<AbstractSpell> FANG_STRIKE_SPELL = registerSpell(new FangStrikeSpell());
    public static final RegistryEntry<AbstractSpell> FANG_WARD_SPELL = registerSpell(new FangWardSpell());
    public static final RegistryEntry<AbstractSpell> FIRECRACKER_SPELL = registerSpell(new FirecrackerSpell());
    public static final RegistryEntry<AbstractSpell> GUST_SPELL = registerSpell(new GustSpell());
    public static final RegistryEntry<AbstractSpell> INVISIBILITY_SPELL = registerSpell(new InvisibilitySpell());
    public static final RegistryEntry<AbstractSpell> LOB_CREEPER_SPELL = registerSpell(new LobCreeperSpell());
    public static final RegistryEntry<AbstractSpell> SHIELD_SPELL = registerSpell(new ShieldSpell());
    public static final RegistryEntry<AbstractSpell> SPECTRAL_HAMMER_SPELL = registerSpell(new SpectralHammerSpell());
    public static final RegistryEntry<AbstractSpell> SUMMON_HORSE_SPELL = registerSpell(new SummonHorseSpell());
    public static final RegistryEntry<AbstractSpell> SUMMON_VEX_SPELL = registerSpell(new SummonVexSpell());
    public static final RegistryEntry<AbstractSpell> SLOW_SPELL = registerSpell(new SlowSpell());
    public static final RegistryEntry<AbstractSpell> ARROW_VOLLEY_SPELL = registerSpell(new ArrowVolleySpell());
    public static final RegistryEntry<AbstractSpell> WOLOLO_SPELL = registerSpell(new WololoSpell());
    public static final RegistryEntry<AbstractSpell> THROW_SPELL = registerSpell(new ThrowSpell());
    public static final RegistryEntry<AbstractSpell> FANG_SWIRL_SPELL = registerSpell(new FangSwirlSpell());
    public static final RegistryEntry<AbstractSpell> SCAPEGOAT_SPELL = registerSpell(new ScapegoatSpell());

    // FIRE
    public static final RegistryEntry<AbstractSpell> BLAZE_STORM_SPELL = registerSpell(new BlazeStormSpell());
    public static final RegistryEntry<AbstractSpell> BURNING_DASH_SPELL = registerSpell(new BurningDashSpell());
    public static final RegistryEntry<AbstractSpell> FIREBALL_SPELL = registerSpell(new FireballSpell());
    public static final RegistryEntry<AbstractSpell> FIREBOLT_SPELL = registerSpell(new FireboltSpell());
    public static final RegistryEntry<AbstractSpell> FIRE_BREATH_SPELL = registerSpell(new FireBreathSpell());
    public static final RegistryEntry<AbstractSpell> MAGMA_BOMB_SPELL = registerSpell(new MagmaBombSpell());
    public static final RegistryEntry<AbstractSpell> WALL_OF_FIRE_SPELL = registerSpell(new WallOfFireSpell());
    public static final RegistryEntry<AbstractSpell> HEAT_SURGE_SPELL = registerSpell(new HeatSurgeSpell());
    public static final RegistryEntry<AbstractSpell> FLAMING_STRIKE_SPELL = registerSpell(new FlamingStrikeSpell());
    public static final RegistryEntry<AbstractSpell> SCORCH_SPELL = registerSpell(new ScorchSpell());
    public static final RegistryEntry<AbstractSpell> FLAMING_BARRAGE_SPELL = registerSpell(new FlamingBarrageSpell());
    public static final RegistryEntry<AbstractSpell> FIRE_ARROW_SPELL = registerSpell(new FireArrowSpell());
    public static final RegistryEntry<AbstractSpell> RAISE_HELL_SPELL = registerSpell(new RaiseHellSpell());


    // HOLY
    public static final RegistryEntry<AbstractSpell> ANGEL_WINGS_SPELL = registerSpell(new AngelWingsSpell());
    public static final RegistryEntry<AbstractSpell> BLESSING_OF_LIFE_SPELL = registerSpell(new BlessingOfLifeSpell());
    public static final RegistryEntry<AbstractSpell> CLOUD_OF_REGENERATION_SPELL = registerSpell(new CloudOfRegenerationSpell());
    public static final RegistryEntry<AbstractSpell> FORTIFY_SPELL = registerSpell(new FortifySpell());
    public static final RegistryEntry<AbstractSpell> GREATER_HEAL_SPELL = registerSpell(new GreaterHealSpell());
    public static final RegistryEntry<AbstractSpell> GUIDING_BOLT_SPELL = registerSpell(new GuidingBoltSpell());
    public static final RegistryEntry<AbstractSpell> HEALING_CIRCLE_SPELL = registerSpell(new HealingCircleSpell());
    public static final RegistryEntry<AbstractSpell> HEAL_SPELL = registerSpell(new HealSpell());
    public static final RegistryEntry<AbstractSpell> SUNBEAM_SPELL = registerSpell(new SunbeamSpell());
    public static final RegistryEntry<AbstractSpell> WISP_SPELL = registerSpell(new WispSpell());
    public static final RegistryEntry<AbstractSpell> DIVINE_SMITE_SPELL = registerSpell(new DivineSmiteSpell());
    public static final RegistryEntry<AbstractSpell> HASTE_SPELL = registerSpell(new HasteSpell());
    public static final RegistryEntry<AbstractSpell> CLEANSE_SPELL = registerSpell(new CleanseSpell());

    // ICE
    public static final RegistryEntry<AbstractSpell> CONE_OF_COLD_SPELL = registerSpell(new ConeOfColdSpell());
    //    public static final RegistryEntry<AbstractSpell> FROSTBITE_SPELL = registerSpell(new FrostbiteSpell());
    public static final RegistryEntry<AbstractSpell> FROST_STEP_SPELL = registerSpell(new FrostStepSpell());
    public static final RegistryEntry<AbstractSpell> ICE_BLOCK_SPELL = registerSpell(new IceBlockSpell());
    public static final RegistryEntry<AbstractSpell> ICICLE_SPELL = registerSpell(new IcicleSpell());
    public static final RegistryEntry<AbstractSpell> SUMMON_POLAR_BEAR_SPELL = registerSpell(new SummonPolarBearSpell());
    public static final RegistryEntry<AbstractSpell> RAY_OF_FROST_SPELL = registerSpell(new RayOfFrostSpell());
    public static final RegistryEntry<AbstractSpell> FROSTWAVE_SPELL = registerSpell(new FrostwaveSpell());
    public static final RegistryEntry<AbstractSpell> ICE_SPIKES_SPELL = registerSpell(new IceSpikesSpell());
    public static final RegistryEntry<AbstractSpell> ICE_TOMB_SPELL = registerSpell(new IceTombSpell());
    public static final RegistryEntry<AbstractSpell> SNOWBALL_SPELL = registerSpell(new SnowballSpell());
    public static final RegistryEntry<AbstractSpell> FROSTBITE_SPELL = registerSpell(new FrostbiteSpell());
    public static final RegistryEntry<AbstractSpell> BLIZZARD_SPELL = registerSpell(new BlizzardSpell());

    // LIGHTNING
    public static final RegistryEntry<AbstractSpell> ASCENSION_SPELL = registerSpell(new AscensionSpell());
    public static final RegistryEntry<AbstractSpell> CHAIN_LIGHTNING_SPELL = registerSpell(new ChainLightningSpell());
    public static final RegistryEntry<AbstractSpell> CHARGE_SPELL = registerSpell(new ChargeSpell());
    public static final RegistryEntry<AbstractSpell> ELECTROCUTE_SPELL = registerSpell(new ElectrocuteSpell());
    public static final RegistryEntry<AbstractSpell> LIGHTNING_BOLT_SPELL = registerSpell(new LightningBoltSpell());
    public static final RegistryEntry<AbstractSpell> LIGHTNING_LANCE_SPELL = registerSpell(new LightningLanceSpell());
    //    public static final RegistryEntry<AbstractSpell> THUNDER_STEP_SPELL = registerSpell(new ThunderStepSpell());
    public static final RegistryEntry<AbstractSpell> SHOCKWAVE_SPELL = registerSpell(new ShockwaveSpell());
    public static final RegistryEntry<AbstractSpell> THUNDERSTORM_SPELL = registerSpell(new ThunderstormSpell());
    public static final RegistryEntry<AbstractSpell> BALL_LIGHTNING_SPELL = registerSpell(new BallLightningSpell());
    public static final RegistryEntry<AbstractSpell> VOLT_STRIKE_SPELL = registerSpell(new VoltStrikeSpell());

    // NATURE
    public static final RegistryEntry<AbstractSpell> ACID_ORB_SPELL = registerSpell(new AcidOrbSpell());
    public static final RegistryEntry<AbstractSpell> BLIGHT_SPELL = registerSpell(new BlightSpell());
    public static final RegistryEntry<AbstractSpell> POISON_ARROW_SPELL = registerSpell(new PoisonArrowSpell());
    public static final RegistryEntry<AbstractSpell> POISON_BREATH_SPELL = registerSpell(new PoisonBreathSpell());
    public static final RegistryEntry<AbstractSpell> POISON_SPLASH_SPELL = registerSpell(new PoisonSplashSpell());
    public static final RegistryEntry<AbstractSpell> ROOT_SPELL = registerSpell(new RootSpell());
    public static final RegistryEntry<AbstractSpell> SPIDER_ASPECT_SPELL = registerSpell(new SpiderAspectSpell());
    public static final RegistryEntry<AbstractSpell> FIREFLY_SWARM_SPELL = registerSpell(new FireflySwarmSpell());
    public static final RegistryEntry<AbstractSpell> OAKSKIN_SPELL = registerSpell(new OakskinSpell());
    public static final RegistryEntry<AbstractSpell> EARTHQUAKE_SPELL = registerSpell(new EarthquakeSpell());
    public static final RegistryEntry<AbstractSpell> STOMP_SPELL = registerSpell(new StompSpell());
    public static final RegistryEntry<AbstractSpell> GLUTTONY_SPELL = registerSpell(new GluttonySpell());
    public static final RegistryEntry<AbstractSpell> TOUCH_DIG = registerSpell(new TouchDigSpell());

    //VOID
    public static final RegistryEntry<AbstractSpell> ABYSSAL_SHROUD_SPELL = registerSpell(new AbyssalShroudSpell());
    public static final RegistryEntry<AbstractSpell> SCULK_TENTACLES_SPELL = registerSpell(new SculkTentaclesSpell());
    public static final RegistryEntry<AbstractSpell> SONIC_BOOM_SPELL = registerSpell(new SonicBoomSpell());
    public static final RegistryEntry<AbstractSpell> PLANAR_SIGHT_SPELL = registerSpell(new PlanarSightSpell());
    public static final RegistryEntry<AbstractSpell> TELEKINESIS_SPELL = registerSpell(new TelekinesisSpell());
    public static final RegistryEntry<AbstractSpell> ELDRITCH_BLAST_SPELL = registerSpell(new EldritchBlastSpell());
    public static final RegistryEntry<AbstractSpell> POCKET_DIMENSION_SPELL = registerSpell(new PocketDimensionSpell());
}
