package io.redspace.ironsspellbooks.registries;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.redspace.ironsspellbooks.command.*;
import io.redspace.ironsspellbooks.gui.inscription_table.InscriptionTableMenu;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;







public class CommandRegistry {
    @SuppressWarnings("unchecked")

    public static void onCommandsRegister(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection selection) {




        CreateScrollCommand.register(commandDispatcher);
        CreateSpellBookCommand.register(commandDispatcher);
        CreateImbuedSwordCommand.register(commandDispatcher, commandBuildContext);
        CreateDebugWizardCommand.register(commandDispatcher);
        CastCommand.register(commandDispatcher);
        ManaCommand.register(commandDispatcher);
        GenerateModList.register(commandDispatcher);
        LearnCommand.register(commandDispatcher);
        ClearCooldownCommand.register(commandDispatcher);
        ClearRecastsCommand.register(commandDispatcher);
        IronsSpellbooksCommand.register(commandDispatcher);

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ClearSpellSelectionCommand.register(commandDispatcher);
            IronsDebugCommand.register(commandDispatcher);
            GenerateSiteData.register(commandDispatcher);
            commandDispatcher.register((LiteralArgumentBuilder<CommandSourceStack>) ((LiteralArgumentBuilder) LiteralArgumentBuilder.literal("it")).executes(source -> ((CommandSourceStack) source.getSource()).getPlayer().openMenu(new SimpleMenuProvider(
                    (i, inventory, player) ->
                            new InscriptionTableMenu(i, inventory, ContainerLevelAccess.NULL), Component.translatable("block.irons_spellbooks.inscription_table")
            )).orElse(0)));
        }
    }
}