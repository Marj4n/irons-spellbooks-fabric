package io.redspace.ironsspellbooks.registries;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.command.SpellArgument;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
public final class CommandArgumentRegistry {
    public static void register() {
        ArgumentTypeRegistry.registerArgumentType(IronsSpellbooks.id("spell"), SpellArgument.class,
                SingletonArgumentInfo.contextFree(SpellArgument::spellArgument));
    }
}
