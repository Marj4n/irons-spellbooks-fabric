package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.gui.arcane_anvil.ArcaneAnvilMenu;
import io.redspace.ironsspellbooks.gui.inscription_table.InscriptionTableMenu;
import io.redspace.ironsspellbooks.gui.scroll_forge.ScrollForgeMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory;
import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;

import java.util.function.Supplier;


public class MenuRegistry {
    private static final DeferredRegistry<MenuType<?>> MENUS = DeferredRegistry.create(Registries.MENU, IronsSpellbooks.MODID);

    public static void register() {
        MENUS.register();
    }

    private static <T extends AbstractContainerMenu> RegistryEntry<MenuType<T>> registerMenuType(ExtendedFactory<T> factory, String name) {
        return MENUS.register(name, () -> new ExtendedScreenHandlerType<>(factory));
    }

    public static final Supplier<MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE_MENU = registerMenuType(InscriptionTableMenu::new, "inscription_table_menu");
    public static final Supplier<MenuType<ScrollForgeMenu>> SCROLL_FORGE_MENU = registerMenuType(ScrollForgeMenu::new, "scroll_forge_menu");
    public static final Supplier<MenuType<ArcaneAnvilMenu>> ARCANE_ANVIL_MENU = registerMenuType(ArcaneAnvilMenu::new, "arcane_anvil_menu");

}
