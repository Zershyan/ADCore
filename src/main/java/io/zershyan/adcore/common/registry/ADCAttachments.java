package io.zershyan.adcore.common.registry;

import com.mojang.serialization.Codec;
import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectData;
import io.zershyan.adcore.common.registry.entry.condition.modifier.ConditionModifierMap;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ADCAttachments {
    private static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ADCore.MODID);
    public static final Supplier<AttachmentType<Map<Identifier, AttackEffectData>>> ATTACK_EFFECTS;
    public static final Supplier<AttachmentType<Map<Holder<Attribute>, ConditionModifierMap>>> CONDITION_ATTRIBUTE_MODIFIER;

    static {
        ATTACK_EFFECTS = REGISTRY.register("attack_effects", () -> AttachmentType
                .builder(() -> ((Map<Identifier, AttackEffectData>) new HashMap<Identifier, AttackEffectData>()))
                .serialize(Codec.unboundedMap(Identifier.CODEC, AttackEffectData.CODEC).fieldOf("attack_effects"))
                .copyOnDeath()
                .build());
        CONDITION_ATTRIBUTE_MODIFIER = REGISTRY.register("condition_modifier", () -> AttachmentType
                .builder(() -> ((Map<Holder<Attribute>, ConditionModifierMap>) new HashMap<Holder<Attribute>, ConditionModifierMap>()))
                .serialize(Codec.unboundedMap(Attribute.CODEC, ConditionModifierMap.CODEC).fieldOf("condition_modifier"))
                .copyOnDeath()
                .sync(ByteBufCodecs.map(HashMap::new, Attribute.STREAM_CODEC, ConditionModifierMap.STREAM_CODEC))
                .build());
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
