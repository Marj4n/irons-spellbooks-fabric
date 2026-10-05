package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/** Every living entity receives spell attributes, including entities from other mods. */
@Mixin(AttributeMap.class)
public abstract class AttributeMapMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static AttributeSupplier ironsSpellbooks$addMagicAttributes(AttributeSupplier original) {
        var instances = new java.util.HashMap<>(
                ((AttributeSupplierAccessor) original).ironsSpellbooks$getInstances());
        ((AttributeSupplierAccessor) AttributeRegistry.createDefaults()).ironsSpellbooks$getInstances()
                .forEach(instances::putIfAbsent);
        return new AttributeSupplier(instances);
    }
}
