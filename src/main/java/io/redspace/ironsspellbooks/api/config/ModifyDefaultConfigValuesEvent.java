package io.redspace.ironsspellbooks.api.config;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import org.marj4n.irons_spellbooks.events.SpellEvent;

public class ModifyDefaultConfigValuesEvent extends SpellEvent {

    private final AbstractSpell spell;
    private final SpellConfigHolder config;

    public ModifyDefaultConfigValuesEvent(AbstractSpell spell, SpellConfigHolder spellConfigHolder) {
        this.spell = spell;
        this.config = spellConfigHolder;
    }

    public <T> void setDefaultValue(SpellConfigParameter<T> type, T value) {
        config.setDefaultValue(type, value);
    }

    public AbstractSpell getSpell() {
        return spell;
    }
}
