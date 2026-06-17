package io.zershyan.adcore.common.registry.entry.condition.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import io.zershyan.adcore.common.registry.entry.condition.Condition;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ConditionModifier {
    public static final Codec<ConditionModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
            Identifier.CODEC.fieldOf("id").forGetter(ConditionModifier::id),
            Codec.DOUBLE.fieldOf("amount").forGetter(ConditionModifier::amount),
            AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(ConditionModifier::operation),
            Condition.CODEC.fieldOf("condition").forGetter(ConditionModifier::condition)
    ).apply(i, ConditionModifier::new));
    public static final StreamCodec<ByteBuf, ConditionModifier> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, ConditionModifier::id,
            ByteBufCodecs.DOUBLE, ConditionModifier::amount,
            AttributeModifier.Operation.STREAM_CODEC, ConditionModifier::operation,
            Condition.STREAM_CODEC, ConditionModifier::condition,
            ConditionModifier::new
    );

    protected final Identifier id;
    protected final double amount;
    protected final AttributeModifier.Operation operation;
    protected final Condition condition;

    public ConditionModifier(Identifier id, double amount, AttributeModifier.Operation operation, Condition condition) {
        this.id = id;
        this.amount = amount;
        this.operation = operation;
        this.condition = condition;
    }

    public Identifier id() {
        return id;
    }

    public double amount() {
        return amount;
    }

    public AttributeModifier.Operation operation() {
        return operation;
    }

    public Condition condition() {
        return condition;
    }

    public boolean testCondition(LivingEntity entity) {
        return condition.testCondition(entity);
    }
}
