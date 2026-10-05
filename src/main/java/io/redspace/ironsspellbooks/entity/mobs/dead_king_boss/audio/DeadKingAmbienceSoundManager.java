package io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.audio;


import io.redspace.ironsspellbooks.config.ClientConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


public class DeadKingAmbienceSoundManager {

    private final Vec3 vec3;
    @Environment(EnvType.CLIENT)
    private DeadKingAmbienceSoundInstance soundInstance;

    public DeadKingAmbienceSoundManager(Vec3 origin) {
        this.vec3 = origin;
    }

    public void trigger() {
        if (ClientConfigs.ENABLE_BOSS_MUSIC.get() && (this.soundInstance == null || this.soundInstance.isStopped())) {
            this.soundInstance = new DeadKingAmbienceSoundInstance(vec3);
            Minecraft.getInstance().getSoundManager().play(soundInstance);
        }
    }

    public void triggerStop() {
        if (this.soundInstance != null) {
            soundInstance.triggerStop();
        }
    }
}
