package org.marj4n.irons_spellbooks.events;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

/** Addons can register typed listeners; no Forge event bus is loaded. */
public final class SpellEvents {
    public static final ResourceLocation NORMAL = Event.DEFAULT_PHASE;
    public static final ResourceLocation LAST = new ResourceLocation("irons_spellbooks", "last");
    @FunctionalInterface
    public interface Listener { void accept(SpellEvent event); }

    public static final Event<Listener> EVENT = EventFactory.createWithPhases(Listener.class,
            callbacks -> event -> {
                for (Listener callback : callbacks) callback.accept(event);
            }, NORMAL, LAST);

    private SpellEvents() {}

    public static <T extends SpellEvent> void register(Class<T> type, Consumer<T> callback) {
        register(NORMAL, type, callback, false);
    }

    public static <T extends SpellEvent> void register(ResourceLocation phase, Class<T> type,
            Consumer<T> callback, boolean receiveCanceled) {
        EVENT.register(phase, event -> {
            if (type.isInstance(event) && (receiveCanceled || !event.isCanceled())) {
                callback.accept(type.cast(event));
            }
        });
    }

    public static boolean post(BaseEvent event) { return event.post(); }
}
