package io.redspace.ironsspellbooks.player;

import com.mojang.blaze3d.platform.InputConstants;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.fabricmc.api.EnvType;

import java.util.ArrayList;
import java.util.List;

public final class KeyMappings {
    public static final String KEY_BIND_GENERAL_CATEGORY = "key.irons_spellbooks.group_1";
    public static final String KEY_BIND_QUICK_CAST_CATEGORY = "key.irons_spellbooks.group_2";

    public static final ExtendedKeyMapping SPELL_WHEEL_KEYMAP = new ExtendedKeyMapping(getResourceName("spell_wheel"), InputConstants.Type.KEYSYM, InputConstants.KEY_R, KEY_BIND_GENERAL_CATEGORY);
    public static final ExtendedKeyMapping SPELL_WHEEL_TOGGLE_KEYMAP = new ExtendedKeyMapping(getResourceName("spell_wheel_toggle"), InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), KEY_BIND_GENERAL_CATEGORY);
    public static final ExtendedKeyMapping SPELLBOOK_CAST_ACTIVE_KEYMAP = new ExtendedKeyMapping(getResourceName("spellbook_cast"), InputConstants.Type.KEYSYM, InputConstants.KEY_V, KEY_BIND_GENERAL_CATEGORY);
    public static final ExtendedKeyMapping SPELLBAR_SCROLL_MODIFIER_KEYMAP = new ExtendedKeyMapping(getResourceName("spell_bar_modifier"), InputConstants.Type.KEYSYM, InputConstants.KEY_LALT, KEY_BIND_GENERAL_CATEGORY);
    public static final List<ExtendedKeyMapping> QUICK_CAST_MAPPINGS = createQuickCastKeybinds();

    private static String getResourceName(String name) {
        return String.format("key.irons_spellbooks.%s", name);
    }

    public static void onRegisterKeybinds() {
        //Ironsspellbooks.logger.debug("KeyMappings.onRegisterKeybinds");
        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(SPELL_WHEEL_KEYMAP);
        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(SPELL_WHEEL_TOGGLE_KEYMAP);
        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(SPELLBOOK_CAST_ACTIVE_KEYMAP);
        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(SPELLBAR_SCROLL_MODIFIER_KEYMAP);
//        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(ELDRITCH_SCREEN_KEYMAP);
        QUICK_CAST_MAPPINGS.forEach(net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper::registerKeyBinding);
    }

    private static List<ExtendedKeyMapping> createQuickCastKeybinds() {
        var qcm = new ArrayList<ExtendedKeyMapping>();
        for (int i = 1; i <= 15; i++) {
            qcm.add(new ExtendedKeyMapping(getResourceName(String.format("spell_quick_cast_%d", i)), InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), KEY_BIND_QUICK_CAST_CATEGORY));
        }
        return qcm;
    }
}
