package io.zershyan.adcore.common.registry.entry.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;
import java.util.function.Function;

public class Condition {
    public static final Codec<Condition> CODEC = RecordCodecBuilder.create(i -> i.group(
            Identifier.CODEC.fieldOf("condition").forGetter(Condition::getId)
    ).apply(i, Condition::of));
    public static final StreamCodec<ByteBuf, Condition> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, Condition::getId, Condition::of);

    private final Function<LivingEntity, Boolean> condition;

    public Condition(Function<LivingEntity, Boolean> condition) {
        this.condition = condition;
    }

    public boolean testCondition(LivingEntity entity) {
        try { return condition.apply(entity); }
        catch (Exception _) { return false; }
    }

    public static Condition of(Identifier id) {
        return ConditionRegistry.REGISTRY.getValue(id);
    }

    private Identifier getId() {
        return Optional.ofNullable(ConditionRegistry.REGISTRY.getKey(this)).orElseThrow(
                () -> new NullPointerException("Can not find registry"));
    }
}
