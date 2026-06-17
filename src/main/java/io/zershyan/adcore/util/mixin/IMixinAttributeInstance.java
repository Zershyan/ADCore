package io.zershyan.adcore.util.mixin;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;

public interface IMixinAttributeInstance {
    @Unique
    Collection<AttributeModifier> adcore$getModifiersOrEmpty(AttributeModifier.Operation operation);
}
