package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.player.ServerPlayerEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobSpawnMixin {
    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void ironsSpellbooks$spawnAttributes(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType reason, SpawnGroupData group, CompoundTag nbt,
            CallbackInfoReturnable<SpawnGroupData> cir) {
        ServerPlayerEvents.handleResistanceAttributesOnSpawn((Mob) (Object) this);
    }
}
