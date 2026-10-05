package io.redspace.ironsspellbooks.item.weapons.pyrium_staff;

import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.item.weapons.StaffTier;
import org.marj4n.irons_spellbooks.client.item.NativeClientItemExtensions;

import java.util.function.Consumer;

public class PyriumStaffItem extends StaffItem {

    public PyriumStaffItem(Properties properties) {
        super(properties, StaffTier.PYRIUM_STAFF);
    }

    @Override
    public boolean hasCustomRendering() {
        return true;
    }

    @Override
    public void initializeClient(Consumer<NativeClientItemExtensions> consumer) {
        consumer.accept(
                new PyriumStaffClientExtensions()
        );
    }
}
