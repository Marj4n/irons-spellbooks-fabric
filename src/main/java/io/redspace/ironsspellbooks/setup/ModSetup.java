package io.redspace.ironsspellbooks.setup;

import io.redspace.ironsspellbooks.compat.CompatHandler;
/** Shared setup invoked by the native Fabric initializer. */
public final class ModSetup {
    private ModSetup() {}
    public static void init() { CompatHandler.init(); }
}
