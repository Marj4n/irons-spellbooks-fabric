package org.marj4n.irons_spellbooks.events;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;

/** Mutable spell lifecycle event dispatched synchronously through Fabric. */
public abstract class SpellEvent extends BaseEvent {
    public boolean isCancelable() { return false; }

    @Override
    public void setCanceled(boolean canceled) {
        if (canceled && !isCancelable()) {
            throw new IllegalArgumentException("This spell event cannot be canceled");
        }
        super.setCanceled(canceled);
    }

    @Override
    public final void sendEvent() { SpellEvents.EVENT.invoker().accept(this); }
}
