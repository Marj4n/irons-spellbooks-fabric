//package io.redspace.ironsspellbooks.loot;
//
//import io.redspace.ironsspellbooks.IronsSpellbooks;
//import net.minecraft.network.chat.ClickEvent;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.Style;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import org.marj4n.irons_spellbooks.events.InteractionEvents;
//
//
//@EventBusSubscriber
//public class LootDebugEvents {
//
//    private static final boolean debugLootTables = false;
//
//    @SubscribeEvent
//    public static void alertLootTable(InteractionEvents.RightClickBlock event) {
//        if (debugLootTables) {
//            var blockEntity = event.getLevel().getBlockEntity(event.getHitVec().getBlockPos());
//            if (blockEntity instanceof RandomizableContainerBlockEntity chest) {
//                var lootTable = chest.getLootTable();
//                if (lootTable != null) {
//                    if (event.getEntity() instanceof ServerPlayer serverPlayer)
//                        serverPlayer.sendSystemMessage(Component.literal(chest.getLootTable().toString()).withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, lootTable.toString()))));
//                    IronsSpellbooks.LOGGER.info("{}", chest.getLootTable());
//                }
//            }
//        }
//    }
//}
