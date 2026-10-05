package io.redspace.ironsspellbooks.setup;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.config.ClientConfigs;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.entity.mobs.SummonedHorse;
import io.redspace.ironsspellbooks.entity.mobs.SummonedSkeleton;
import io.redspace.ironsspellbooks.entity.mobs.SummonedVex;
import io.redspace.ironsspellbooks.entity.mobs.SummonedZombie;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingBoss;
import io.redspace.ironsspellbooks.entity.mobs.debug_wizard.DebugWizard;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.entity.mobs.ice_spider.IceSpiderEntity;
import io.redspace.ironsspellbooks.entity.mobs.keeper.KeeperEntity;
import io.redspace.ironsspellbooks.entity.mobs.necromancer.NecromancerEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.alchemist.ApothecaristEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.archevoker.ArchevokerEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.cryomancer.CryomancerEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.cultist.CultistEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.cursed_armor_stand.CursedArmorStandEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.fire_boss.FireBossEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.priest.PriestEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.pyromancer.PyromancerEntity;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import io.redspace.ironsspellbooks.entity.spells.scapegoat.ScapegoatEntity;
import io.redspace.ironsspellbooks.entity.spells.spectral_hammer.SpectralHammer;
import io.redspace.ironsspellbooks.entity.spells.summoned_weapons.SummonedClaymoreEntity;
import io.redspace.ironsspellbooks.entity.spells.summoned_weapons.SummonedRapierEntity;
import io.redspace.ironsspellbooks.entity.spells.summoned_weapons.SummonedSwordEntity;
import io.redspace.ironsspellbooks.entity.spells.void_tentacle.VoidTentacle;
import io.redspace.ironsspellbooks.entity.spells.wisp.WispEntity;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.fml.config.ModConfig;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class CommonSetup {

    public static void onModConfigLoadingEvent(ModConfig config) {
        //IronsSpellbooks.LOGGER.debug("onModConfigLoadingEvent");
        if (config.getType() == ModConfig.Type.SERVER) {
            SpellRegistry.onConfigReload();
            ServerConfigs.onConfigReload();
        } else if (config.getType() == ModConfig.Type.CLIENT) {
            ClientConfigs.onConfigReload();
        }
    }

    public static void onModConfigReloadingEvent(ModConfig config) {
        //IronsSpellbooks.LOGGER.debug("onModConfigReloadingEvent");
        if (config.getType() == ModConfig.Type.SERVER) {
            SpellRegistry.onConfigReload();
            ServerConfigs.onConfigReload();
        } else if (config.getType() == ModConfig.Type.CLIENT) {
            ClientConfigs.onConfigReload();
        }
    }

    //todo: forge sucks
//    @SubscribeEvent
//    public static void registerCapabilitiesEvent(/*RegisterCapabilitiesEvent event*/AttachCapabilitiesEvent<BlockEntity> event) {
//        if (event.getObject() instanceof AlchemistCauldronTile) {
//            var noopHandler =  new MillibucketTank(0);
//            event.addCapability(IronsSpellbooks.id("alchemist_cauldron"), new CapabilityProvider<>(ForgeCapabilities.FLUID_HANDLER, LazyOptional.of(() -> noopHandler)) {
//            });
//        }
////        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockRegistry.ALCHEMIST_CAULDRON_TILE.get(),
////                (be, context) -> {
////                    if (be.fluidCapability == null) {
////                        be.refreshCapabilities();
////                    }
////
////                    return be.fluidCapability;
////                });
//    }

    public static void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(EntityRegistry.DEBUG_WIZARD.get(), DebugWizard.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.PYROMANCER.get(), PyromancerEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.NECROMANCER.get(), NecromancerEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SPECTRAL_STEED.get(), SummonedHorse.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.WISP.get(), WispEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SPECTRAL_HAMMER.get(), SpectralHammer.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SUMMONED_VEX.get(), SummonedVex.createAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SUMMONED_ZOMBIE.get(), SummonedZombie.createAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SUMMONED_SKELETON.get(), SummonedSkeleton.createAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.FROZEN_HUMANOID.get(), FrozenHumanoid.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SUMMONED_POLAR_BEAR.get(), PolarBear.createAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.DEAD_KING.get(), DeadKingBoss.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.DEAD_KING_CORPSE.get(), DeadKingBoss.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.CATACOMBS_ZOMBIE.get(), Zombie.createAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.MAGEHUNTER_VINDICATOR.get(), Vindicator.createAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.ARCHEVOKER.get(), ArchevokerEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.PRIEST.get(), PriestEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.KEEPER.get(), KeeperEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SCULK_TENTACLE.get(), VoidTentacle.createLivingAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.CRYOMANCER.get(), CryomancerEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.ROOT.get(), RootEntity.createLivingAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.FIREFLY_SWARM.get(), WispEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.APOTHECARIST.get(), ApothecaristEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.CULTIST.get(), CultistEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.FIRE_BOSS.get(), FireBossEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.CURSED_ARMOR_STAND.get(), CursedArmorStandEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SUMMONED_SWORD.get(), SummonedSwordEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SUMMONED_CLAYMORE.get(), SummonedClaymoreEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SUMMONED_RAPIER.get(), SummonedRapierEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.ICE_SPIDER.get(), IceSpiderEntity.prepareAttributes().build());
        FabricDefaultAttributeRegistry.register(EntityRegistry.SCAPEGOAT.get(), ScapegoatEntity.prepareAttributes().build());
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(EntityRegistry.NECROMANCER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (type, serverLevelAccessor, spawnType, blockPos, random) -> Utils.checkMonsterSpawnRules(serverLevelAccessor, spawnType, blockPos, random));
    }
}
