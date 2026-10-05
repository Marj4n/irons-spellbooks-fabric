package io.redspace.ironsspellbooks.render;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
public final class CinderousRarity {
    public static final Rarity CINDEROUS_RARITY=Rarity.EPIC;
    public static final int COLOR=0xf2552e;
    private static final java.util.Set<String> ITEMS=java.util.Set.of("pyrium_staff", "legionnaire_flamberge", "hellrazor", "unchained_book", "pyrium_ingot", "cinderous_soulcaller", "cinderous_soul_rune", "cinderous_vault", "betrayer_signet", "music_disc_flame_still_burns", "disc_fragment_flame_still_burns");
    public static boolean isCinderous(ItemStack stack) {
        var id=BuiltInRegistries.ITEM.getKey(stack.getItem());
        return id.getNamespace().equals("irons_spellbooks") && ITEMS.contains(id.getPath());
    }
}
