package io.zershyan.adcore.common.registry.entry.conditionModifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.Objects;
import java.util.concurrent.Callable;

public class ConditionModifier {
    public static final Codec<ConditionModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Identifier.CODEC.fieldOf("modifier").forGetter(ConditionModifier::getId)
    ).apply(i, ConditionModifier::of));
    public static final StreamCodec<ByteBuf, ConditionModifier> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, ConditionModifier::getId, ConditionModifier::of);

    private final Callable<Boolean> condition;

    public ConditionModifier(Callable<Boolean> condition) {
        this.condition = condition;
    }

    public static ConditionModifier of(Identifier id) {
        return ConditionModifierRegistry.REGISTRY.getValue(id);
    }

    private Identifier getId() {
        return Objects.requireNonNull(ConditionModifierRegistry.REGISTRY.getKey(this));
    }
}
