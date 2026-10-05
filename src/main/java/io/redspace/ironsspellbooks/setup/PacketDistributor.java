package io.redspace.ironsspellbooks.setup;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.network.*;
import io.redspace.ironsspellbooks.network.casting.*;
import io.redspace.ironsspellbooks.network.gui.SelectSpellPacket;
import io.redspace.ironsspellbooks.network.particles.*;
import io.redspace.ironsspellbooks.network.debug.PlayPlayerAnimationPacket;
import io.redspace.ironsspellbooks.network.spells.GuidingBoltManagerStartTrackingPacket;
import io.redspace.ironsspellbooks.network.spells.GuidingBoltManagerStopTrackingPacket;
import io.redspace.ironsspellbooks.network.spells.LearnSpellPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.marj4n.irons_spellbooks.network.FabricChannel.Direction;
import org.marj4n.irons_spellbooks.network.FabricChannel;


/**
 * mirrors native 1.21+ PacketDistributor, acts as in-stead replacement
 */
//@Deprecated(forRemoval = true)
public class PacketDistributor {

    private static FabricChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        if (INSTANCE != null) throw new IllegalStateException("Spell packets registered twice");
        FabricChannel net = new FabricChannel(new ResourceLocation(IronsSpellbooks.MODID, "messages"));

        INSTANCE = net;

        /*
        1.20.1 Special Packets
         */
        net.messageBuilder(ClientboundEntityEvent.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(ClientboundEntityEvent::new)
                .encoder(ClientboundEntityEvent::toBytes)
                .consumerMainThread(ClientboundEntityEvent::handle)
                .add();

        net.messageBuilder(ClientboundSyncAnimation.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(ClientboundSyncAnimation::new)
                .encoder(ClientboundSyncAnimation::toBytes)
                .consumerMainThread(ClientboundSyncAnimation::handle)
                .add();

        net.messageBuilder(OpenHeldBookPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(OpenHeldBookPacket::new)
                .encoder(OpenHeldBookPacket::toBytes)
                .consumerMainThread(OpenHeldBookPacket::handle)
                .add();

        net.messageBuilder(SyncUpgradeOrbTypes.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncUpgradeOrbTypes::new)
                .encoder(SyncUpgradeOrbTypes::toBytes)
                .consumerMainThread(SyncUpgradeOrbTypes::handle)
                .add();
        /*
        End 1.20.1 Special Packets
         */

        net.messageBuilder(SyncJsonConfigPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncJsonConfigPacket::new)
                .encoder(SyncJsonConfigPacket::toBytes)
                .consumerMainThread(SyncJsonConfigPacket::handle)
                .add();

        net.messageBuilder(UpdateCastingStatePacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(UpdateCastingStatePacket::new)
                .encoder(UpdateCastingStatePacket::toBytes)
                .consumerMainThread(UpdateCastingStatePacket::handle)
                .add();

        net.messageBuilder(AddMotionToPlayerPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(AddMotionToPlayerPacket::new)
                .encoder(AddMotionToPlayerPacket::toBytes)
                .consumerMainThread(AddMotionToPlayerPacket::handle)
                .add();

        net.messageBuilder(SyncManaPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncManaPacket::new)
                .encoder(SyncManaPacket::toBytes)
                .consumerMainThread(SyncManaPacket::handle)
                .add();

        net.messageBuilder(OnClientCastPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(OnClientCastPacket::new)
                .encoder(OnClientCastPacket::toBytes)
                .consumerMainThread(OnClientCastPacket::handle)
                .add();

        net.messageBuilder(SyncPlayerDataPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncPlayerDataPacket::new)
                .encoder(SyncPlayerDataPacket::toBytes)
                .consumerMainThread(SyncPlayerDataPacket::handle)
                .add();

        net.messageBuilder(SyncEntityDataPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncEntityDataPacket::new)
                .encoder(SyncEntityDataPacket::toBytes)
                .consumerMainThread(SyncEntityDataPacket::handle)
                .add();

        net.messageBuilder(SyncCastingMobAimingDataPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncCastingMobAimingDataPacket::new)
                .encoder(SyncCastingMobAimingDataPacket::toBytes)
                .consumerMainThread(SyncCastingMobAimingDataPacket::handle)
                .add();

//        net.messageBuilder(InscribeSpellPacket.class, id(), Direction.PLAY_TO_SERVER)
//                .decoder(InscribeSpellPacket::new)
//                .encoder(InscribeSpellPacket::toBytes)
//                .consumerMainThread(InscribeSpellPacket::handle)
//                .add();

        net.messageBuilder(SyncCooldownPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncCooldownPacket::new)
                .encoder(SyncCooldownPacket::toBytes)
                .consumerMainThread(SyncCooldownPacket::handle)
                .add();

        net.messageBuilder(SyncCooldownsPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncCooldownsPacket::new)
                .encoder(SyncCooldownsPacket::toBytes)
                .consumerMainThread(SyncCooldownsPacket::handle)
                .add();

        net.messageBuilder(SyncRecastsPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncRecastsPacket::new)
                .encoder(SyncRecastsPacket::toBytes)
                .consumerMainThread(SyncRecastsPacket::handle)
                .add();

        net.messageBuilder(SyncRecastPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncRecastPacket::new)
                .encoder(SyncRecastPacket::toBytes)
                .consumerMainThread(SyncRecastPacket::handle)
                .add();

        net.messageBuilder(RemoveRecastPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(RemoveRecastPacket::new)
                .encoder(RemoveRecastPacket::toBytes)
                .consumerMainThread(RemoveRecastPacket::handle)
                .add();

        net.messageBuilder(TeleportParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(TeleportParticlesPacket::new)
                .encoder(TeleportParticlesPacket::toBytes)
                .consumerMainThread(TeleportParticlesPacket::handle)
                .add();

        net.messageBuilder(FrostStepParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(FrostStepParticlesPacket::new)
                .encoder(FrostStepParticlesPacket::toBytes)
                .consumerMainThread(FrostStepParticlesPacket::handle)
                .add();

        net.messageBuilder(ScrollForgeSelectSpellPacket.class, id(), Direction.PLAY_TO_SERVER)
                .decoder(ScrollForgeSelectSpellPacket::new)
                .encoder(ScrollForgeSelectSpellPacket::toBytes)
                .consumerMainThread(ScrollForgeSelectSpellPacket::handle)
                .add();

//        net.messageBuilder(InscriptionTableSelectSpellPacket.class, id(), Direction.PLAY_TO_SERVER)
//                .decoder(InscriptionTableSelectSpellPacket::new)
//                .encoder(InscriptionTableSelectSpellPacket::toBytes)
//                .consumerMainThread(InscriptionTableSelectSpellPacket::handle)
//                .add();

        net.messageBuilder(CancelCastPacket.class, id(), Direction.PLAY_TO_SERVER)
                .decoder(CancelCastPacket::new)
                .encoder(CancelCastPacket::toBytes)
                .consumerMainThread(CancelCastPacket::handle)
                .add();

        net.messageBuilder(QuickCastPacket.class, id(), Direction.PLAY_TO_SERVER)
                .decoder(QuickCastPacket::new)
                .encoder(QuickCastPacket::toBytes)
                .consumerMainThread(QuickCastPacket::handle)
                .add();

        net.messageBuilder(HealParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(HealParticlesPacket::new)
                .encoder(HealParticlesPacket::toBytes)
                .consumerMainThread(HealParticlesPacket::handle)
                .add();

        net.messageBuilder(BloodSiphonParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(BloodSiphonParticlesPacket::new)
                .encoder(BloodSiphonParticlesPacket::toBytes)
                .consumerMainThread(BloodSiphonParticlesPacket::handle)
                .add();

        net.messageBuilder(RegenCloudParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(RegenCloudParticlesPacket::new)
                .encoder(RegenCloudParticlesPacket::toBytes)
                .consumerMainThread(RegenCloudParticlesPacket::handle)
                .add();

        net.messageBuilder(OnCastStartedPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(OnCastStartedPacket::new)
                .encoder(OnCastStartedPacket::toBytes)
                .consumerMainThread(OnCastStartedPacket::handle)
                .add();

        net.messageBuilder(OnCastFinishedPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(OnCastFinishedPacket::new)
                .encoder(OnCastFinishedPacket::toBytes)
                .consumerMainThread(OnCastFinishedPacket::handle)
                .add();

        net.messageBuilder(AbsorptionParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(AbsorptionParticlesPacket::new)
                .encoder(AbsorptionParticlesPacket::toBytes)
                .consumerMainThread(AbsorptionParticlesPacket::handle)
                .add();

        net.messageBuilder(FortifyAreaParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(FortifyAreaParticlesPacket::new)
                .encoder(FortifyAreaParticlesPacket::toBytes)
                .consumerMainThread(FortifyAreaParticlesPacket::handle)
                .add();

        net.messageBuilder(SyncTargetingDataPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncTargetingDataPacket::new)
                .encoder(SyncTargetingDataPacket::toBytes)
                .consumerMainThread(SyncTargetingDataPacket::handle)
                .add();

        net.messageBuilder(CastErrorPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(CastErrorPacket::new)
                .encoder(CastErrorPacket::toBytes)
                .consumerMainThread(CastErrorPacket::handle)
                .add();

        net.messageBuilder(SyncAnimationPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncAnimationPacket::new)
                .encoder(SyncAnimationPacket::toBytes)
                .consumerMainThread(SyncAnimationPacket::handle)
                .add();

        net.messageBuilder(OakskinParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(OakskinParticlesPacket::new)
                .encoder(OakskinParticlesPacket::toBytes)
                .consumerMainThread(OakskinParticlesPacket::handle)
                .add();

        net.messageBuilder(SyncCameraShakePacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncCameraShakePacket::new)
                .encoder(SyncCameraShakePacket::write)
                .consumerMainThread(SyncCameraShakePacket::handle)
                .add();

        net.messageBuilder(SyncAllCameraShakesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(SyncAllCameraShakesPacket::new)
                .encoder(SyncAllCameraShakesPacket::write)
                .consumerMainThread(SyncAllCameraShakesPacket::handle)
                .add();

        net.messageBuilder(EquipmentChangedPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(EquipmentChangedPacket::new)
                .encoder(EquipmentChangedPacket::toBytes)
                .consumerMainThread(EquipmentChangedPacket::handle)
                .add();

        net.messageBuilder(LearnSpellPacket.class, id(), Direction.PLAY_TO_SERVER)
                .decoder(LearnSpellPacket::new)
                .encoder(LearnSpellPacket::toBytes)
                .consumerMainThread(LearnSpellPacket::handle)
                .add();

        net.messageBuilder(SelectSpellPacket.class, id(), Direction.PLAY_TO_SERVER)
                .decoder(SelectSpellPacket::new)
                .encoder(SelectSpellPacket::toBytes)
                .consumerMainThread(SelectSpellPacket::handle)
                .add();

        net.messageBuilder(CastPacket.class, id(), Direction.PLAY_TO_SERVER)
                .decoder(CastPacket::new)
                .encoder(CastPacket::toBytes)
                .consumerMainThread(CastPacket::handle)
                .add();

        net.messageBuilder(OpenEldritchScreenPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(OpenEldritchScreenPacket::new)
                .encoder(OpenEldritchScreenPacket::toBytes)
                .consumerMainThread(OpenEldritchScreenPacket::handle)
                .add();

        net.messageBuilder(FieryExplosionParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(FieryExplosionParticlesPacket::new)
                .encoder(FieryExplosionParticlesPacket::toBytes)
                .consumerMainThread(FieryExplosionParticlesPacket::handle)
                .add();

        net.messageBuilder(EntityEventPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(EntityEventPacket::new)
                .encoder(EntityEventPacket::toBytes)
                .consumerMainThread(EntityEventPacket::handle)
                .add();

        net.messageBuilder(GuidingBoltManagerStartTrackingPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(GuidingBoltManagerStartTrackingPacket::new)
                .encoder(GuidingBoltManagerStartTrackingPacket::toBytes)
                .consumerMainThread(GuidingBoltManagerStartTrackingPacket::handle)
                .add();

        net.messageBuilder(GuidingBoltManagerStopTrackingPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(GuidingBoltManagerStopTrackingPacket::new)
                .encoder(GuidingBoltManagerStopTrackingPacket::toBytes)
                .consumerMainThread(GuidingBoltManagerStopTrackingPacket::handle)
                .add();

        net.messageBuilder(ShockwaveParticlesPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(ShockwaveParticlesPacket::new)
                .encoder(ShockwaveParticlesPacket::toBytes)
                .consumerMainThread(ShockwaveParticlesPacket::handle)
                .add();

        net.messageBuilder(PlayPlayerAnimationPacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(PlayPlayerAnimationPacket::new)
                .encoder(PlayPlayerAnimationPacket::toBytes)
                .consumerMainThread(PlayPlayerAnimationPacket::handle)
                .add();
        net.messageBuilder(org.marj4n.irons_spellbooks.ironlib.SyncChroniclePacket.class, id(), Direction.PLAY_TO_CLIENT)
                .decoder(org.marj4n.irons_spellbooks.ironlib.SyncChroniclePacket::new)
                .encoder(org.marj4n.irons_spellbooks.ironlib.SyncChroniclePacket::toBytes)
                .consumerMainThread(org.marj4n.irons_spellbooks.ironlib.SyncChroniclePacket::handle).add();
        net.registerServerReceiver();
    }

    public static FabricChannel channel() {
        if (INSTANCE == null) throw new IllegalStateException("Spell packets not registered");
        return INSTANCE;
    }

    public static <MSG> void sendToServer(MSG message) {
        channel().sendToServer(message);
    }

    public static <MSG> void sendToPlayer(ServerPlayer player, MSG message) {
        channel().sendToPlayer(player, message);
    }

    public static <MSG> void sendToAllPlayers(MSG message) {
        if (IronsSpellbooks.MCS == null) throw new IllegalStateException("No active spell server");
        channel().sendToAllPlayers(IronsSpellbooks.MCS, message);
    }

    public static <MSG> void sendToPlayersTrackingEntity(Entity entity, MSG message) {
        channel().sendToTracking(entity, message);
    }

    public static <MSG> void sendToPlayersTrackingEntityAndSelf(Entity entity, MSG message) {
        sendToPlayersTrackingEntity(entity, message);
        if (entity instanceof ServerPlayer serverPlayer) sendToPlayer(serverPlayer, message);
    }
}
