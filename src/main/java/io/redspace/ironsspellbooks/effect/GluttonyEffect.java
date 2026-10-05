package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.setup.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;


public class GluttonyEffect extends MagicMobEffect {
    public GluttonyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public static void finishEating(net.minecraft.world.entity.LivingEntity entity, net.minecraft.world.item.ItemStack item) {
        //This won't account for cake blocks, but that would require more invasive mixins to detect so this is fine
        if (!entity.level().isClientSide) {
            var food = item.getItem().getFoodProperties();
            if (food != null) {
                var gluttony = entity.getEffect(MobEffectRegistry.GLUTTONY.get());
                if (gluttony != null) {
                    var pmg = MagicData.getPlayerMagicData(entity);
                    pmg.addMana(food.getNutrition() * ratioForAmplifier(gluttony.getAmplifier()));
                    if (entity instanceof ServerPlayer serverPlayer) {
                        PacketDistributor.sendToPlayer(serverPlayer, new SyncManaPacket(pmg));
                    }
                }
            }
        }
    }

    public static float ratioForAmplifier(int amplifier) {
        return (4 + amplifier) * .5f;
    }
}
