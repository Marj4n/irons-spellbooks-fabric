package io.redspace.ironsspellbooks.api.events;


import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.world.entity.player.Player;
import org.marj4n.irons_spellbooks.events.SpellPlayerEvent;


public class SpellPreCastEvent extends SpellPlayerEvent {
    @Override public boolean isCancelable() { return true; }

    private final String spellId;
    private final SchoolType schoolType;
    private final CastSource castSource;
    private final int spellLevel;

    public SpellPreCastEvent(Player player, String spellId, int spellLevel, SchoolType schoolType, CastSource castSource) {
        super(player);
        this.spellId = spellId;
        this.spellLevel = spellLevel;
        this.schoolType = schoolType;
        this.castSource = castSource;
    }

    public String getSpellId() {
        return this.spellId;
    }

    public SchoolType getSchoolType() {
        return this.schoolType;
    }

    public int getSpellLevel() {
        return this.spellLevel;
    }

    public CastSource getCastSource() {
        return this.castSource;
    }
}
