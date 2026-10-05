package io.redspace.ironsspellbooks.mixin;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Animal.class)
public abstract class BreedingMixin {
    @Inject(method="finalizeSpawnChildFromBreeding",at=@At("HEAD"))
    private void irons_spellbooks$offspring(ServerLevel level, Animal partner, AgeableMob child, CallbackInfo ci) {
        io.redspace.ironsspellbooks.player.ServerPlayerEvents.changeBreedOutcome(child,(Animal)(Object)this,partner);
    }
}
