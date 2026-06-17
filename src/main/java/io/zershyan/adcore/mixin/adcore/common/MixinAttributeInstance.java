package io.zershyan.adcore.mixin.adcore.common;

import io.zershyan.adcore.util.mixin.IMixinAttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;

@Mixin(AttributeInstance.class)
public abstract class MixinAttributeInstance implements IMixinAttributeInstance {

    @Shadow
    protected abstract Collection<AttributeModifier> getModifiersOrEmpty(AttributeModifier.Operation operation);

    @Unique
    @Override
    public Collection<AttributeModifier> adcore$getModifiersOrEmpty(AttributeModifier.Operation operation) {
        return getModifiersOrEmpty(operation);
    }
}
