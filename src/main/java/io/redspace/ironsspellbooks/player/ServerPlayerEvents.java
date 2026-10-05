package io.redspace.ironsspellbooks.player;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.entity.IOminousEntity;
import io.redspace.ironsspellbooks.api.events.SpellTeleportEvent;
import io.redspace.ironsspellbooks.api.item.CastingImplementData;
import io.redspace.ironsspellbooks.api.item.UpgradeData;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.block.BloodCauldronBlock;
import io.redspace.ironsspellbooks.block.portal_frame.PortalFrameBlockEntity;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.PocketDimensionManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.data.IronsDataStorage;
import io.redspace.ironsspellbooks.datagen.DamageTypeTagGenerator;
import io.redspace.ironsspellbooks.effect.*;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.ice_spider.ICritablePartEntity;
import io.redspace.ironsspellbooks.entity.spells.ice_tomb.IceTombEntity;
import io.redspace.ironsspellbooks.entity.spells.root.PreventDismount;
import io.redspace.ironsspellbooks.item.CastingItem;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.network.EquipmentChangedPacket;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import io.redspace.ironsspellbooks.setup.PacketDistributor;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.ModTags;
import io.redspace.ironsspellbooks.util.UpgradeUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import io.github.fabricators_of_create.porting_lib.entity.events.ProjectileImpactEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingDeathEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents.ChangeTarget.ChangeTargetEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.CriticalHitEvent;
import org.marj4n.irons_spellbooks.events.InteractionEvents;
import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
import org.marj4n.irons_spellbooks.equipment.EquipmentApi;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ServerPlayerEvents {

    //    @SubscribeEvent
//    public static void onPlayerAttack(AttackEntityEvent event) {
//        TODO: this only gets called when the player successfully hits something. we want it to cancel if they even try.
//              granted, the input even should be cancelled already, but better combat skips that due to custom weapon handling.
//        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
//            if (PlayerMagicData.getPlayerMagicData(serverPlayer).isCasting()) {
//                Utils.serverSideCancelCast(serverPlayer);
//            }
//        }
//    }

    public static void onUseItem(InteractionEvents.RightClickItem event) {
        var player = event.getEntity();
        if (player.level().isClientSide) {
            MinecraftInstanceHelper.ifPlayerPresent(localPlayer -> {
                if (ClientMagicData.isCasting() && player.getUUID().equals(localPlayer.getUUID())) {
                    event.setCanceled(true);
                }
            });
        } else {
            var magicData = MagicData.getPlayerMagicData(player);
            if (magicData.isCasting() && event.getItemStack() != magicData.getPlayerCastingItem()) {
                event.setCanceled(true);
            }
        }
        if (event.isCanceled()) {
            return;
        }

        var level = player.level();
        var hand = event.getHand();
        ItemStack itemStack = player.getItemInHand(hand);
        if (CastingImplementData.has(itemStack) && CastingImplementData.get(itemStack)) {
            SpellSelectionManager spellSelectionManager = new SpellSelectionManager(player);
            SpellSelectionManager.SelectionOption selectionOption = spellSelectionManager.getSelection();
            if (selectionOption == null || selectionOption.spellData.equals(SpellData.EMPTY)) {
                //IronsSpellbooks.LOGGER.debug("CastingItem.Use.1 {} {}", level.isClientSide, hand);
                return;
            }
            SpellData spellData = selectionOption.spellData;
            int spellLevel = spellData.getSpell().getLevelFor(spellData.getLevel(), player);
            if (level.isClientSide()) {
                if (ClientMagicData.isCasting()) {
                    //IronsSpellbooks.LOGGER.debug("CastingItem.Use.2 {} {}", level.isClientSide, hand);
                    event.setCancellationResult(InteractionResult.CONSUME);
                } else if (ClientMagicData.getPlayerMana() < spellData.getSpell().getManaCost(spellLevel)
                        || ClientMagicData.getCooldowns().isOnCooldown(spellData.getSpell())
                        || !ClientMagicData.getSyncedSpellData(player).isSpellLearned(spellData.getSpell())) {
                    //IronsSpellbooks.LOGGER.debug("CastingItem.Use.3 {} {}", level.isClientSide, hand);
                    return;
                } else {
                    //IronsSpellbooks.LOGGER.debug("CastingItem.Use.4 {} {}", level.isClientSide, hand);
                    event.setCancellationResult(InteractionResult.CONSUME);
                }
            }

            var castingSlot = hand.ordinal() == 0 ? SpellSelectionManager.MAINHAND : SpellSelectionManager.OFFHAND;

            if (spellData.getSpell().attemptInitiateCast(itemStack, spellLevel, level, player, selectionOption.getCastSource(), true, castingSlot)) {
                event.setCancellationResult(InteractionResult.CONSUME);
            } else {
                //IronsSpellbooks.LOGGER.debug("CastingItem.Use.6 {} {}", level.isClientSide, hand);
                event.setCancellationResult(InteractionResult.FAIL);
            }
            event.setCanceled(true);
        }
    }

    public static void onPlayerDropItem(net.minecraft.world.entity.item.ItemEntity itemEntity, Player player) {
        var itemStack = itemEntity.getItem();
        if (itemStack.getItem() instanceof Scroll) {
            var magicData = MagicData.getPlayerMagicData(player);
            if (magicData.isCasting() && magicData.getCastSource() == CastSource.SCROLL) {
                if (magicData.getCastType() == CastType.CONTINUOUS) {
                    itemStack.shrink(1);
                }
            }
        }
    }

    public static void onLevelLoaded(net.minecraft.world.level.LevelAccessor level) {
        if (level instanceof ServerLevel serverLevel && serverLevel.dimension() == Level.OVERWORLD) {
            IronsDataStorage.init(serverLevel.getDataStorage());
        }
    }

    public static void onServerStoppedEvent(net.minecraft.server.MinecraftServer server) {
        IronsSpellbooks.MCS = null;
        IronsSpellbooks.OVERWORLD = null;
    }

    public static void onServerStarted(net.minecraft.server.MinecraftServer server) {
        IronsSpellbooks.MCS = server;
        IronsSpellbooks.OVERWORLD = IronsSpellbooks.MCS.overworld();
    }

    public static void onLivingEquipmentChangeEvent(LivingEntity entity, EquipmentSlot slot, ItemStack from, ItemStack to) {
        if (entity instanceof ServerPlayer serverPlayer) {
            var playerMagicData = MagicData.getPlayerMagicData(serverPlayer);

            if (playerMagicData.isCasting() && (from.getItem() instanceof CastingItem || to.getItem() instanceof CastingItem)) {
                Utils.serverSideCancelCast(serverPlayer);
                PacketDistributor.sendToPlayer(serverPlayer, new EquipmentChangedPacket());
                return;
            }

            var isFromSpellContainer = ISpellContainer.isSpellContainer(from);
            if (isFromSpellContainer &&
                    ISpellContainer.get(from).getIndexForSpell(playerMagicData.getCastingSpell().getSpell()) >= 0 &&
                    !Utils.isSameItemSameComponentsIgnoreDurability(from, to)) {
                if (playerMagicData.isCasting()) {
                    Utils.serverSideCancelCast(serverPlayer);
                }
                PacketDistributor.sendToPlayer(serverPlayer, new EquipmentChangedPacket());
            } else if (isFromSpellContainer || ISpellContainer.isSpellContainer(to)) {
                PacketDistributor.sendToPlayer(serverPlayer, new EquipmentChangedPacket());
            }
        }
    }

    public static void onPlayerLogOut(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            Utils.serverSideCancelCast(serverPlayer);
        }
    }

    public static void onPlayerOpenContainer(Player player) {
        if (player.level().isClientSide) {
            return;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            var playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
            if (playerMagicData.isCasting()) {
                Utils.serverSideCancelCast(serverPlayer);
            }
        }
    }

    public static int onExperienceDroppedEvent(int experience, Player player, LivingEntity entity) {
        if (player != null) {
            int ringCount = EquipmentApi.getCuriosInventory(player).map(inventory -> inventory.findCurios(ItemRegistry.EMERALD_STONEPLATE_RING.get()).size()).orElse(0);
            for (int i = 0; i < ringCount; i++) {
                experience = (int) (experience * 1.25);
            }
        }
        return experience;
    }

    public static void onStartTracking(Player player, Entity target) {
        if (player instanceof ServerPlayer serverPlayer && target instanceof ServerPlayer targetPlayer) {
            MagicData.getPlayerMagicData(serverPlayer).getSyncedData().syncToPlayer(targetPlayer);
        }
    }

    public static void onPlayerLoggedIn(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            var playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
            playerMagicData.getPlayerCooldowns().syncToPlayer(serverPlayer);
            playerMagicData.getPlayerRecasts().syncAllToPlayer();
            playerMagicData.getSyncedData().syncToPlayer(serverPlayer);
            PacketDistributor.sendToPlayer(serverPlayer, new SyncManaPacket(playerMagicData));
            CameraShakeManager.doSync(serverPlayer);
        }
    }

    public static void onPlayerStartTrackingEntity(Player player, Entity target) {
        if (player instanceof ServerPlayer serverPlayerRecipient) {
            if (target instanceof LivingEntity livingEntity) {
                for (var inst : livingEntity.getActiveEffects()) {
                    if (inst.getEffect() instanceof ISyncedMobEffect) {
                        serverPlayerRecipient.connection.send(new ClientboundUpdateMobEffectPacket(livingEntity.getId(), inst));
                    }
                }
            }
        }
    }

    public static void onLivingDeathEvent(LivingDeathEvent event) {
        var entity = event.getEntity();
        if (!entity.level().isClientSide) {
            if (entity instanceof ServerPlayer serverPlayer) {
                Utils.serverSideCancelCast(serverPlayer);
                MagicData.getPlayerMagicData(serverPlayer).getPlayerRecasts().removeAll(RecastResult.DEATH);
            }
            entity.getActiveEffects().forEach(mobEffectInstance -> {
                if (mobEffectInstance.getEffect() instanceof IMobEffectEndCallback callback) {
                    callback.onEffectRemoved(entity, mobEffectInstance.getAmplifier());
                }
            });
        }
    }

    public static void onSpellTeleport(SpellTeleportEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (ItemRegistry.TELEPORTATION_AMULET.get().isEquippedBy(livingEntity)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.EVASION.get(), 3 * 20, 0, false, false, true));
            }
        }
    }

    public static void onPlayerCloned(Player original, Player replacement) {
        if (replacement instanceof ServerPlayer newServerPlayer) {
            original.getActiveEffects().forEach((effect -> {
                //IronsSpellbooks.LOGGER.debug("{}", effect.getEffect().getDisplayName().getString());
                if (effect.getEffect() instanceof SummonTimer) {
                    newServerPlayer.addEffect(effect, newServerPlayer);
                }
            }));

            MagicData oldMagicData = MagicData.getPlayerMagicData(original);
            MagicData newMagicData = MagicData.getPlayerMagicData(newServerPlayer);
            newMagicData.setSyncedData(oldMagicData.getSyncedData().getPersistentData(newServerPlayer));
            oldMagicData.getPlayerCooldowns().getSpellCooldowns().forEach((spellId, cooldown) -> newMagicData.getPlayerCooldowns().getSpellCooldowns().put(spellId, cooldown));
            //newMagicData.getSyncedData().syncToPlayer(newServerPlayer);
        }
    }

    public static void onPlayerChangedDimension(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            Utils.serverSideCancelCast(serverPlayer);
        }
    }

    public static void onPlayerRespawn(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {

            //Clear fire and frozen
            serverPlayer.clearFire();
            serverPlayer.setTicksFrozen(0);
            var data = serverPlayer.getEntityData().packDirty();
            if (data != null) {
                serverPlayer.connection.send(new ClientboundSetEntityDataPacket(serverPlayer.getId(), data));
            }

            //Cancel casting
            Utils.serverSideCancelCast(serverPlayer);

            //Set respawn mana
            MagicData.getPlayerMagicData(serverPlayer).setMana((int) (serverPlayer.getAttributeValue(AttributeRegistry.MAX_MANA.get()) * ServerConfigs.MANA_SPAWN_PERCENT.get()));
        }
    }

    public static void fixDragonCrits(CriticalHitEvent event) {
        if (event.getTarget().level().isClientSide) {
            return;
        }
        // Crits require the target to be a LivingEntity, meaning dragon parts cannot be critically struck
        // Re-evaluate default crit criteria (without the living entity check, ofc)
        if (event.getTarget() instanceof ICritablePartEntity dragonPartEntity) {
            var part = (Entity) dragonPartEntity;
            var attacker = event.getEntity();
            var defaultShouldCrit = attacker.getAttackStrengthScale(0.5f) > .9
                    && attacker.fallDistance > 0.0F
                    && !attacker.onGround()
                    && !attacker.onClimbable()
                    && !attacker.isInWater()
                    && !attacker.hasEffect(MobEffects.BLINDNESS)
                    && !attacker.isPassenger()
                    && !attacker.isSprinting();
            if (defaultShouldCrit) {
                if (event.getDamageModifier() == 1) {
                    event.setDamageModifier(1.5f);
                }
                // crit particles won't play on nonliving entities, do them manually
                var boundingBox = part.getBoundingBox();
                Vec3 vec3 = boundingBox.getCenter();
                MagicManager.spawnParticles(event.getEntity().level(), ParticleTypes.CRIT, vec3.x, vec3.y, vec3.z, 25, boundingBox.getXsize() * .6, boundingBox.getYsize() * .6, boundingBox.getZsize() * .6, 0, false);
            }
        }
    }

    public static void onLivingIncomingDamage(LivingAttackEvent event) {
        var livingEntity = event.getEntity();
        //irons_spellbooks.LOGGER.debug("onLivingAttack.1: {}", livingEntity);
        if (event.getSource().getEntity() != null && livingEntity.getVehicle() instanceof IceTombEntity iceTomb && !DamageSources.isFriendlyFireBetween(event.getSource().getEntity(), livingEntity)) {
            // redirect entity-caused damage away from entombed players into the tomb
            event.setCanceled(true);
            iceTomb.hurt(event.getSource(), event.getAmount());
            return;
        }
        if ((livingEntity instanceof ServerPlayer) || (livingEntity instanceof IMagicEntity)) {
            if (ItemRegistry.FIREWARD_RING.get().isEquippedBy(livingEntity) && event.getSource().is(DamageTypeTags.IS_FIRE)) {
                event.getEntity().clearFire();
                event.setCanceled(true);
                return;
            }
            var playerMagicData = MagicData.getPlayerMagicData(livingEntity);
            if (livingEntity.hasEffect(MobEffectRegistry.EVASION.get())) {
                if (EvasionEffect.doEffect(livingEntity, event.getSource())) {
                    event.setCanceled(true);
                    return;
                }
            } else if (livingEntity.hasEffect(MobEffectRegistry.ABYSSAL_SHROUD.get())) {
                if (AbyssalShroudEffect.doEffect(livingEntity, event.getSource())) {
                    event.setCanceled(true);
                    return;
                }
            }

            if (livingEntity instanceof ServerPlayer serverPlayer) {
                if (playerMagicData.isCasting() &&
                        playerMagicData.getCastingSpell().getSpell().canBeInterrupted(serverPlayer) &&
                        playerMagicData.getCastDurationRemaining() > 0 &&
                        !event.getSource().is(DamageTypeTagGenerator.LONG_CAST_IGNORE) &&
                        !playerMagicData.popMarkedPoison()) {
                    Utils.serverSideCancelCast(serverPlayer);
                }
            }
        }
        if (ServerConfigs.BETTER_CREEPER_THUNDERHIT.get() && event.getSource().is(DamageTypeTags.IS_FIRE) && event.getEntity() instanceof Creeper creeper && creeper.isPowered()) {
            event.setCanceled(true);
            return;
        }
    }

    public static void onBeforeDamageTaken(LivingDamageEvent event) {
        var livingEntity = event.getEntity();
        if (livingEntity instanceof IMagicEntity || livingEntity instanceof ServerPlayer) {
            var playerMagicData = MagicData.getPlayerMagicData(livingEntity);
            if (livingEntity.hasEffect(MobEffectRegistry.HEARTSTOP.get())) {
                playerMagicData.getSyncedData().addHeartstopDamage(event.getAmount() * .5f);
                event.setAmount(0);
            }
        }
        if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC) && event.getSource().getEntity() instanceof LivingEntity livingAttacker) {
            if (livingAttacker.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.INFERNAL_SORCERER_CHESTPLATE.get()) && (!(livingAttacker instanceof Player player) || !player.getCooldowns().isOnCooldown(ItemRegistry.INFERNAL_SORCERER_CHESTPLATE.get()))) {
                ImmolateEffect.addImmolateStack(livingEntity, livingAttacker);
//                if (livingAttacker instanceof Player player) {
//                    player.getCooldowns().addCooldown(ItemRegistry.INFERNAL_SORCERER_CHESTPLATE.get(), Utils.applyCooldownReduction(InfernalSorcererArmorItem.COOLDOWN_TICKS, player));
//                }
            }
        }
    }

    public static void onLivingChangeTarget(ChangeTargetEvent event) {
        var newTarget = event.getNewTarget();
        var entity = event.getEntity();
        if (newTarget != null) {
            //Prevent Village allies (ie preists/iron golems) from aggroing eachother
            if (newTarget.getType().is(ModTags.VILLAGE_ALLIES) && entity.getType().is(ModTags.VILLAGE_ALLIES)) {
                event.setCanceled(true);
                return;
            }
            //Prevent mobs who auto-target hostile mobs from targeting "enemy" summons, unless they are actually fighting
            if (newTarget instanceof IMagicSummon summon && summon instanceof Enemy && !(entity.equals(((Mob) newTarget).getTarget()))) {
                event.setCanceled(true);
                return;
            }
            if (newTarget.hasEffect(MobEffectRegistry.TRUE_INVISIBILITY.get())) {
                event.setCanceled(true);
                return;
            }
        }
    }

    public static boolean preventDismount(Entity entity, Entity mount, boolean mounting) {
        if (!entity.level().isClientSide && !mounting && mount instanceof PreventDismount preventDismount
                && !mount.isRemoved() && !entity.isRemoved()) {
            if (!preventDismount.canEntityDismount(entity)) {
                return false;
            }
        }
        return true;
    }

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof EntityHitResult entityHitResult) {
            var victim = entityHitResult.getEntity();
            if (victim instanceof IMagicEntity || victim instanceof Player) {
                var livingEntity = (LivingEntity) victim;
                if (livingEntity.hasEffect(MobEffectRegistry.EVASION.get())) {
                    if (EvasionEffect.doEffect(livingEntity, victim.damageSources().indirectMagic(event.getProjectile(), event.getProjectile().getOwner()))) {
                        event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                    }
                } else if (livingEntity.hasEffect(MobEffectRegistry.ABYSSAL_SHROUD.get())) {
                    if (AbyssalShroudEffect.doEffect(livingEntity, victim.damageSources().indirectMagic(event.getProjectile(), event.getProjectile().getOwner()))) {
                        event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                    }
                }
            }
        }
    }

    public static void useOnEntityEvent(InteractionEvents.EntityInteractSpecific event) {
        if (event.getTarget() instanceof Creeper creeper) {
            var player = event.getEntity();
            var hand = event.getHand();
            var useItem = player.getItemInHand(hand);
            if (useItem.is(Items.GLASS_BOTTLE) && creeper.isPowered()) {
                creeper.hurt(creeper.damageSources().generic(), 5);
                player.level().playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
                player.swing(hand);
                player.setItemInHand(hand, ItemUtils.createFilledResult(useItem, player, new ItemStack(ItemRegistry.LIGHTNING_BOTTLE.get())));
                event.setCancellationResult(InteractionResultHolder.consume(player.getItemInHand(hand)).getResult());
                event.setCanceled(true);
            }
        }
    }

    public static void handleResistanceAttributesOnSpawn(Mob mob) {
        //Attributes should never be null because all living entities have these attributes
        if (mob.getMobType() == MobType.UNDEAD) {
            //Undead take extra holy damage, and less blood (necromantic) damage
            setIfNonNull(mob, AttributeRegistry.HOLY_MAGIC_RESIST, 0.5);
            setIfNonNull(mob, AttributeRegistry.BLOOD_MAGIC_RESIST, 1.5);
        } else if (mob.getMobType() == MobType.WATER) {
            //Water mobs take extra lightning damage
            setIfNonNull(mob, AttributeRegistry.LIGHTNING_MAGIC_RESIST, 0.5);
        }
        if (mob.fireImmune()) {
            //Fire immune (blazes, pyromancer, etc) take 50% fire damage
            setIfNonNull(mob, AttributeRegistry.FIRE_MAGIC_RESIST, 1.5);
        }
        //TODO: replace this with "fire_elemental" entity tag for all fiery mobs (blaze, magma cubes, modded mobs)
        if (mob.getType() == EntityType.BLAZE) {
            setIfNonNull(mob, AttributeRegistry.ICE_MAGIC_RESIST, 0.5);
        }
    }

    private static void setIfNonNull(LivingEntity mob, Supplier<Attribute> attribute, double value) {
        var instance = mob.getAttributes().getInstance(attribute.get());
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }

    public static void onLivingTick(LivingEntityEvents.LivingTickEvent event) {
        var entity = event.getEntity();
        var level = entity.level();
        if (!level.isClientSide) {
            if (entity.tickCount % 40 == 0) {
                BlockPos pos = entity.blockPosition();
                BlockState blockState = entity.level().getBlockState(pos);
                if (blockState.is(Blocks.CAULDRON)) {
                    BloodCauldronBlock.attemptCookEntity(blockState, entity.level(), pos, entity, () -> {
                        level.setBlockAndUpdate(pos, BlockRegistry.BLOOD_CAULDRON_BLOCK.get().defaultBlockState());
                        level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
                    });
                }
            }
        }
    }

    // uses mixin for 1.20.1
//    @SubscribeEvent
//    public static void registerPatrolSpawners(ModifyCustomSpawnersEvent event) {
//        if (event.getLevel().dimension().equals(Level.OVERWORLD)) {
//            event.addCustomSpawner(new IceSpiderPatrolSpawner());
//        }
//    }

    public static void onBlockBreak(BlockEvents.BreakEvent event) {
        if (ServerConfigs.PORTAL_FRAME_RESTRICT_BREAKING.get()) {
            if (event.getState().is(BlockRegistry.PORTAL_FRAME.get())) {
                var player = event.getPlayer();
                if (event.getLevel().getBlockEntity(event.getPos()) instanceof PortalFrameBlockEntity portalFrameBlockEntity && portalFrameBlockEntity.getOwnerUUID() != null && !player.getUUID().equals(portalFrameBlockEntity.getOwnerUUID())) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.portal_break_failure").withStyle(ChatFormatting.RED)));
                    }
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void preventBlockPlacement(InteractionEvents.RightClickBlock event) {
        var level = event.getLevel();
        if (level.dimension().equals(PocketDimensionManager.POCKET_DIMENSION)) {
            if (event.getItemStack().getItem() instanceof BlockItem blockItem && blockItem.getBlock().builtInRegistryHolder().is(ModTags.PREVENT_POCKET_DIMENSION_PLACEMENT)) {
                event.setCanceled(true);
                if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                    serverPlayer.displayClientMessage(Component.translatable("ui.irons_spellbooks.error_place_block_dimension").withStyle(ChatFormatting.RED), true);
                }
            }
        }
    }

    public static boolean isPocketTeleportBlocked(Entity entity) {
        return entity != null && entity.level() instanceof ServerLevel level
                && level.dimension().equals(PocketDimensionManager.POCKET_DIMENSION);
    }

    public static void changeBreedOutcome(net.minecraft.world.entity.AgeableMob child,
            net.minecraft.world.entity.animal.Animal first, net.minecraft.world.entity.animal.Animal second) {
        if (ServerConfigs.HOGLIN_OFFSPRING_PROTECTION.get()
                && child instanceof Hoglin baby && first instanceof Hoglin parent1 && second instanceof Hoglin parent2) {
            double chance = (parent1.isImmuneToZombification() ? .5 : 0)
                    + (parent2.isImmuneToZombification() ? .5 : 0);
            if (Utils.random.nextFloat() < chance) baby.setImmuneToZombification(true);
        }
    }

    public static void handleOminousEntities(Entity entity, Level level, boolean loadedFromDisk) {
        if (!(level instanceof ServerLevel serverLevel) || loadedFromDisk) {
            return;
        }
        if (entity instanceof IOminousEntity ominousSettings && !ominousSettings.isOminous() && ominousSettings.canTriggerOminous()) {
            float rangeSqr = ominousSettings.ominousTriggerRange();
            rangeSqr *= rangeSqr;
            Vec3 center = entity.position();
            List<Player> ominousPlayers = new ArrayList<>();
            for (Player player : serverLevel.players()) {
                if (player.isCreative() || player.isSpectator() || player.distanceToSqr(center) > rangeSqr) {
                    continue;
                }
                if (player.hasEffect(MobEffectRegistry.TRIAL_OMEN.get())) {
                    ominousPlayers.add(player);
                } else if (player.hasEffect(MobEffects.BAD_OMEN)) {
                    ominousPlayers.add(player);
                    MobEffectInstance mobeffectinstance = player.getEffect(MobEffects.BAD_OMEN);
                    int i = mobeffectinstance.getAmplifier() + 1;
                    int j = 18000 * i;
                    player.removeEffect(MobEffects.BAD_OMEN);
                    player.addEffect(new MobEffectInstance(MobEffectRegistry.TRIAL_OMEN.get(), j, 0, false, false, true));
                    MagicManager.spawnParticles(serverLevel, ParticleTypes.SOUL_FIRE_FLAME, player.getX(), player.getY(0.5), player.getZ(), 25, 0.1, 0.2, 0.1, 0.2, false);
                    MagicManager.spawnParticles(serverLevel, ParticleHelper.TRIAL_OMEN, player.getX(), player.getY(0.5), player.getZ(), 25, 0.1, 0.2, 0.1, 0.2, false);
                }
            }
            if (!ominousPlayers.isEmpty()) {
                ominousSettings.onOminousTrigger();
                serverLevel.playSound(null, BlockPos.containing(center), SoundRegistry.TRIAL_SPAWNER_OMINOUS_ACTIVATE.get(), SoundSource.BLOCKS, 4, 1.0F);
            }
        }
    }

    public static boolean onChangeDimensions(Entity entity) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return true;
        }
        /*
         * Disallow summons to change dimensions
         */
        var owner = SummonManager.getOwner(entity);
        if (owner != null) {
            return false;
        }
        /*
         * Destroy all of our summons when we teleport. We don't have enough context to bring them with us, and we cannot leave them, so they must die
         */
        var summons = SummonManager.getSummons(entity);
        if (!summons.isEmpty()) {
            for (UUID uuid : summons) {
                var summon = serverLevel.getEntity(uuid);
                if (summon instanceof IMagicSummon magicSummon) {
                    magicSummon.onUnSummon();
                } else if (summon != null) {
                    SummonManager.removeSummon(summon);
                }
            }
        }
        return true;
    }

    public static void onDataLoaded() {
        // tags only bound on data loaded, so we must wait until now to (dynamically) resolve cauldron interactions
        var map = CauldronInteraction.WATER;
        for (var item : ItemRegistry.getIronsItems()) {
            if (item.get() instanceof DyeableLeatherItem) {
                map.put(item.get(), CauldronInteraction.DYED_ITEM);
            }
        }
    }
}
