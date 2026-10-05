package io.redspace.ironsspellbooks.api.spells;

import io.redspace.ironsspellbooks.api.network.ISerializable;
import net.minecraft.nbt.CompoundTag;
import io.github.fabricators_of_create.porting_lib.core.util.INBTSerializable;


public interface ICastDataSerializable extends ICastData, ISerializable, INBTSerializable<CompoundTag> {
}
