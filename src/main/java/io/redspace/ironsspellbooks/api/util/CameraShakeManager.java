package io.redspace.ironsspellbooks.api.util;

import io.redspace.ironsspellbooks.network.SyncAllCameraShakesPacket;
import io.redspace.ironsspellbooks.network.SyncCameraShakePacket;
import io.redspace.ironsspellbooks.setup.PacketDistributor;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CameraShakeManager {
    public static final ArrayList<CameraShakeData> cameraShakeData = new ArrayList<>();
    public static ArrayList<CameraShakeData> clientCameraShakeData = new ArrayList<>();
    private static int nextId = 0;

    public static int getNextId() {
        return nextId++;
    }

    public static void serverTick(net.minecraft.server.MinecraftServer server) {
        if (cameraShakeData.isEmpty()) {
            return;
        }
        ArrayList<CameraShakeData> completed = new ArrayList<>();
        for (CameraShakeData data : cameraShakeData) {
            data.tickCount++;
            if (data.tickCount >= data.duration) {
                completed.add(data);
            }
        }
        if (!completed.isEmpty()) {
            completed.forEach(CameraShakeManager::removeCameraShake);
        }
    }

    public static void addCameraShake(CameraShakeData data) {
        cameraShakeData.add(data);
        PacketDistributor.sendToAllPlayers(new SyncCameraShakePacket(data, false));
    }

    public static void removeCameraShake(CameraShakeData data) {
        if (cameraShakeData.removeIf(instance -> instance.id == data.id)) {
            PacketDistributor.sendToAllPlayers(new SyncCameraShakePacket(data, true));
        }
    }

    public static void addClientCameraShake(CameraShakeData data) {
        clientCameraShakeData.add(data);
    }

    public static void removeClientCameraShake(CameraShakeData data) {
        clientCameraShakeData.removeIf(instance -> instance.id == data.id);
    }

    public static void doSync(ServerPlayer serverPlayer) {
        PacketDistributor.sendToPlayer(serverPlayer, new SyncAllCameraShakesPacket(cameraShakeData));
    }

    private static final int fadeoutDuration = 20;
    private static final float fadeoutMultiplier = 1f / fadeoutDuration;

    @Environment(EnvType.CLIENT)
    public static void handleCameraShake(io.github.fabricators_of_create.porting_lib.event.client.CameraSetupCallback.CameraInfo event) {
        if (clientCameraShakeData.isEmpty()) {
            return;
        }

        var player = event.camera.getEntity();
        List<CameraShakeData> sortedActiveCameraShakes = clientCameraShakeData.stream()
                .filter(data -> data.dimension.equals(player.level().dimension()))
                .sorted(Comparator.comparingDouble(o -> o.origin.distanceToSqr(player.position())))
                .toList();
        if (sortedActiveCameraShakes.isEmpty()) {
            return;
        }
        var cameraShake = sortedActiveCameraShakes.get(0);
        var closestPos = cameraShake.origin;

        float distanceMultiplier = 1 / (cameraShake.radius * cameraShake.radius);
        float fadeout = (cameraShake.duration - cameraShake.tickCount) >= fadeoutDuration ? 1f
                : ((cameraShake.duration - cameraShake.tickCount) * fadeoutMultiplier);
        fadeout = Mth.clamp(fadeout, 0, 1); // additional safeguard against negative values
        float intensity = (float) Mth.clampedLerp(1, 0, closestPos.distanceToSqr(player.position()) * distanceMultiplier) * fadeout;

        float f = (float) (player.tickCount + event.partialTicks);
        float yaw = Mth.cos(f * 1.5f) * intensity * .5f;
        float pitch = Mth.cos(f * 2f) * intensity * .5f;
        float roll = Mth.sin(f * 2.2f) * intensity * .5f;
        event.yaw += yaw;
        event.roll += roll;
        event.pitch += pitch;
    }

    @Environment(EnvType.CLIENT)
    public static void handleCameraShake() {
        if (Minecraft.getInstance().isSingleplayer() && Minecraft.getInstance().isPaused()) {
            return;
        }
        ArrayList<CameraShakeData> toRemove = new ArrayList<>();
        for (var data : clientCameraShakeData) {
            data.tickCount++;
            if (data.tickCount > data.duration + 5) {
                // safeguard against missed packets or other state tracking failure
                toRemove.add(data);
            }
        }
        clientCameraShakeData.removeAll(toRemove);
    }
}
