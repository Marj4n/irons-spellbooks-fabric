package io.redspace.ironsspellbooks.entity.mobs.wizards;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.NeutralWizard;
import io.redspace.ironsspellbooks.util.ModTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import org.marj4n.irons_spellbooks.events.InteractionEvents;
import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;

import java.util.List;

public class WizardAIEvents {

    public static void onBlockBreak(BlockEvents.BreakEvent event) {
        if (event.getState().is(ModTags.GUARDED_BY_WIZARDS)) {
            angerNearbyWizards(event.getPlayer(), 3, false, true);
        }
    }

    public static void onBlockUsed(InteractionEvents.RightClickBlock event) {
        var blockstate = event.getLevel().getBlockState(event.getHitVec().getBlockPos());
        if (blockstate.is(ModTags.GUARDED_BY_WIZARDS)) {
            //If the block being guarded can hold a loot table, we only anger if it does. (Ie you looted a chest of theirs, versus opening a generic chest while in their vicinity)
            if (!(event.getLevel().getBlockEntity(event.getPos()) instanceof RandomizableContainerBlockEntity randomizableContainerBlockEntity) || randomizableContainerBlockEntity.lootTable != null) {
                angerNearbyWizards(event.getEntity(), 1, false, true);
            }
        }
    }

    public static void angerNearbyWizards(Player player, int angerLevel, boolean requireLineOfSight, boolean blockRelated) {
        if (player.getAbilities().instabuild) {
            return;
        }
        List<NeutralWizard> list = player.level().getEntitiesOfClass(NeutralWizard.class, player.getBoundingBox().inflate(16.0D));
        list.stream().filter((neutralWizard) -> (neutralWizard.guardsBlocks() || !blockRelated) && (!requireLineOfSight || BehaviorUtils.canSee(neutralWizard, player))).forEach((neutralWizard) -> {
            neutralWizard.increaseAngerLevel(player, angerLevel, true);
            neutralWizard.setPersistentAngerTarget(player.getUUID());
            if (blockRelated && player instanceof ServerPlayer serverPlayer) {
                var advancement = serverPlayer.serverLevel().getServer().getAdvancements().getAdvancement(IronsSpellbooks.id("irons_spellbooks/steal_from_wizard"));
                if (advancement != null) {
                    serverPlayer.getAdvancements().award(advancement, "anger_wizard");
                }
            }
        });
    }

}
