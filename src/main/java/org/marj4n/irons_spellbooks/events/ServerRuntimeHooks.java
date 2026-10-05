package org.marj4n.irons_spellbooks.events;

import io.github.fabricators_of_create.porting_lib.entity.events.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
import io.redspace.ironsspellbooks.api.events.SpellTeleportEvent;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.wizards.WizardAIEvents;
import io.redspace.ironsspellbooks.player.ServerPlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;

public final class ServerRuntimeHooks {
    private ServerRuntimeHooks() {}

    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(org.marj4n.irons_spellbooks.ironlib.ChronicleData::start);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> org.marj4n.irons_spellbooks.ironlib.ChronicleData.stop());
        ServerPlayConnectionEvents.JOIN.register((handler,sender,server) ->
                io.redspace.ironsspellbooks.setup.PacketDistributor.sendToPlayer(handler.player,
                        new org.marj4n.irons_spellbooks.ironlib.SyncChroniclePacket(org.marj4n.irons_spellbooks.ironlib.ChronicleData.entries())));
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents.ENTITY_UNLOAD.register((entity, level) -> {
            if (entity instanceof WorldRemovalListener listener) listener.onRemovedFromWorld();
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                ServerPlayerEvents.onPlayerLoggedIn(handler.player));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            org.marj4n.irons_spellbooks.equipment.EquipmentSync.forget(handler.player);
            ServerPlayerEvents.onPlayerLogOut(handler.player);
            SummonManager.onPlayerLogout(handler.player);
        });
        net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.COPY_FROM.register(
                (original, replacement, alive) -> ServerPlayerEvents.onPlayerCloned(original, replacement));
        net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.AFTER_RESPAWN.register(
                (original, replacement, alive) -> ServerPlayerEvents.onPlayerRespawn(replacement));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(
                (player, origin, destination) -> ServerPlayerEvents.onPlayerChangedDimension(player));
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents.EQUIPMENT_CHANGE.register(
                ServerPlayerEvents::onLivingEquipmentChangeEvent);
        EntityEvents.START_TRACKING_TAIL.register((target, player) -> {
            ServerPlayerEvents.onStartTracking(player, target);
            ServerPlayerEvents.onPlayerStartTrackingEntity(player, target);
        });
        EntityEvents.ON_JOIN_WORLD.register((entity, level, loadedFromDisk) -> {
            SummonManager.onSummonerLogin(entity);
            io.redspace.ironsspellbooks.item.curios.WickedBoneRingItem.handleAbility(entity, level, loadedFromDisk);
            ServerPlayerEvents.handleOminousEntities(entity, level, loadedFromDisk);
            return true;
        });
        ServerTickEvents.END_SERVER_TICK.register(SummonManager::levelTick);
        ServerTickEvents.END_SERVER_TICK.register(server -> server.getPlayerList().getPlayers().forEach(
                org.marj4n.irons_spellbooks.equipment.EquipmentSync::tick));
        ServerTickEvents.END_SERVER_TICK.register(io.redspace.ironsspellbooks.api.util.CameraShakeManager::serverTick);
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents.ENTITY_LOAD.register(
                io.redspace.ironsspellbooks.effect.guiding_bolt.GuidingBoltManager::onProjectileShot);
        ServerTickEvents.END_WORLD_TICK.register(io.redspace.ironsspellbooks.effect.guiding_bolt.GuidingBoltManager::serverTick);
        LivingEntityEvents.LivingTickEvent.TICK.register(
                io.redspace.ironsspellbooks.effect.guiding_bolt.GuidingBoltManager::livingTick);
        ServerLifecycleEvents.SERVER_STARTED.register(io.redspace.ironsspellbooks.api.backwards_compat.UpgradeTypeCache::onServerStart);
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(io.redspace.ironsspellbooks.api.backwards_compat.UpgradeTypeCache::onDatapackLoad);
        ServerLifecycleEvents.SERVER_STOPPING.register(SummonManager::onServerStopping);
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> ServerPlayerEvents.onDataLoaded());

        UseItemCallback.EVENT.register((player, level, hand) -> {
            var event = new InteractionEvents.RightClickItem(player, hand);
            ServerPlayerEvents.onUseItem(event);
            event.post();
            return event.isCanceled() ? new InteractionResultHolder<>(
                    (event.getCancellationResult() == InteractionResult.PASS ? InteractionResult.FAIL : event.getCancellationResult()), player.getItemInHand(hand))
                    : InteractionResultHolder.pass(player.getItemInHand(hand));
        });
        UseBlockCallback.EVENT.register((player, level, hand, hit) -> {
            var event = new InteractionEvents.RightClickBlock(player, hand, hit);
            ServerPlayerEvents.preventBlockPlacement(event);
            if (!event.isCanceled()) WizardAIEvents.onBlockUsed(event);
            event.post();
            return event.isCanceled() ? (event.getCancellationResult() == InteractionResult.PASS
                    ? InteractionResult.FAIL : event.getCancellationResult()) : InteractionResult.PASS;
        });
        PlayerInteractionEvents.INTERACT_ENTITY_POSITIONED.register((player, target, position, hand) -> {
            var event = new InteractionEvents.EntityInteractSpecific(player, hand, target);
            ServerPlayerEvents.useOnEntityEvent(event);
            event.post();
            return event.isCanceled() ? event.getCancellationResult() : null;
        });

        LivingAttackEvent.ATTACK.register(event -> {
            if (!event.isCanceled()) ServerPlayerEvents.onLivingIncomingDamage(event);
        });
        LivingDamageEvent.DAMAGE.register(event -> {
            if (!event.isCanceled()) ServerPlayerEvents.onBeforeDamageTaken(event);
        });
        LivingHurtEvent.HURT.register(event -> {
            if (!event.isCanceled()) DamageSources.postHitEffects(event);
            if (!event.isCanceled()) io.redspace.ironsspellbooks.effect.EchoingStrikesEffect.createEcho(event);
        });
        LivingEntityEvents.DROPS.register((entity, source, drops, looting, recentlyHit) -> {
            io.redspace.ironsspellbooks.entity.mobs.ice_spider.IceSpiderEntity.onPreyKilled(entity, source, drops);
            return false;
        });
        SpellEvents.register(io.redspace.ironsspellbooks.api.events.SetSummonOwnerEvent.class,
                io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingBoss::sacrificialMarkHandler);
        LivingDamageEvent.DAMAGE.register(event -> {
            if (!event.isCanceled()) {
                io.redspace.ironsspellbooks.item.curios.LurkerRing.handleAbility(event);
                io.redspace.ironsspellbooks.item.curios.BetrayerSignetRingItem.handleAbility(event);
            }
        });
        LivingDeathEvent.DEATH.register(event -> {
            if (!event.isCanceled()) ServerPlayerEvents.onLivingDeathEvent(event);
        });
        LivingHurtEvent.HURT.register(event -> {
            if (!event.isCanceled()) {
                io.redspace.ironsspellbooks.effect.BlightEffect.reduceDamageOutput(event);
                io.redspace.ironsspellbooks.effect.OakskinEffect.reduceDamage(event);
                io.redspace.ironsspellbooks.effect.TrueInvisibilityEffect.onDealDamage(event);
            }
        });
        LivingDamageEvent.DAMAGE.register(event -> {
            if (!event.isCanceled()) io.redspace.ironsspellbooks.effect.SpiderAspectEffect.increaseDamage(event);
        });
        LivingDeathEvent.DEATH.register(event -> {
            if (!event.isCanceled()) {
                io.redspace.ironsspellbooks.effect.FrostbiteEffect.handleFrostbiteDeathEffects(event);
                io.redspace.ironsspellbooks.effect.SacrificialMarkEffect.onLivingDeath(event);
            }
        });
        SpellEvents.register(LivingHealEvent.class, io.redspace.ironsspellbooks.effect.BlightEffect::reduceHealing);
        SpellEvents.register(LivingHealEvent.class, io.redspace.ironsspellbooks.effect.SoulBurnMobEffect::soulBurnReduceHealing);
        SpellEvents.register(LivingKnockBackEvent.class, DamageSources::cancelKnockback);
        LivingEntityUseItemEvents.LIVING_USE_ITEM_FINISH.register((entity, item, duration, result) -> {
            io.redspace.ironsspellbooks.effect.GluttonyEffect.finishEating(entity, item);
            return null;
        });
        CriticalHitEvent.CRITICAL_HIT.register(ServerPlayerEvents::fixDragonCrits);
        LivingEntityEvents.CHANGE_TARGET.register(event -> {
            if (!event.isCanceled()) ServerPlayerEvents.onLivingChangeTarget(event);
        });
        LivingEntityEvents.EXPERIENCE_DROP.register(ServerPlayerEvents::onExperienceDroppedEvent);
        LivingEntityEvents.LivingTickEvent.TICK.register(ServerPlayerEvents::onLivingTick);
        EntityMountEvents.registerForBoth((vehicle, passenger, mounting) ->
                ServerPlayerEvents.preventDismount(passenger, vehicle, mounting));
        EntityEvents.PROJECTILE_IMPACT.register(event -> {
            if (!event.isCanceled()) ServerPlayerEvents.onProjectileImpact(event);
        });
        BlockEvents.BLOCK_BREAK.register(event -> {
            if (!event.isCanceled()) ServerPlayerEvents.onBlockBreak(event);
            if (!event.isCanceled()) WizardAIEvents.onBlockBreak(event);
        });
        SpellEvents.register(SpellEvents.LAST, SpellTeleportEvent.class,
                ServerPlayerEvents::onSpellTeleport, false);
    }
}
