package io.zershyan.adcore.common.registry.entry.condition.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ConditionModifierMap extends EnumMap<AttributeModifier.Operation, Map<Identifier, ConditionModifier>> {
    public static final Codec<ConditionModifierMap> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.unboundedMap(AttributeModifier.Operation.CODEC, Codec.unboundedMap(Identifier.CODEC, ConditionModifier.CODEC))
                    .fieldOf("condition_modifier_map").forGetter(map -> map)
    ).apply(i, ConditionModifierMap::new));
    public static final StreamCodec<ByteBuf, ConditionModifierMap> STREAM_CODEC = ByteBufCodecs.map(ConditionModifierMap::new,
            AttributeModifier.Operation.STREAM_CODEC, ByteBufCodecs.map(HashMap::new, Identifier.STREAM_CODEC, ConditionModifier.STREAM_CODEC));

    public ConditionModifierMap(Map<AttributeModifier.Operation, Map<Identifier, ConditionModifier>> enumMap) {
        super(enumMap);
    }

    public ConditionModifierMap(int value) {
        super(AttributeModifier.Operation.class);
    }

    public ConditionModifierMap() {
        super(AttributeModifier.Operation.class);
    }

    public Collection<ConditionModifier> getModifiersOrEmpty(AttributeModifier.Operation operation) {
        return this.getOrDefault(operation, Map.of()).values();
    }
}
