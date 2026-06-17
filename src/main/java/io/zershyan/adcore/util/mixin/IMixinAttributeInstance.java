package io.zershyan.adcore.util.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Unique;

import java.util.concurrent.Callable;
import java.util.function.Function;

public interface IMixinAttributeModifier {
    @Unique
    AttributeModifier adcore$condition(LivingEntity entity, Function<LivingEntity, Boolean> condition);

    @Unique
    boolean adcore$runCondition();

    @Unique
    boolean adcore$hasCondition();
}
