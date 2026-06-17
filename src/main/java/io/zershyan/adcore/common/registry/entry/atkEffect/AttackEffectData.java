package io.zershyan.adcore.common.registry.entry.atkEffect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AttackEffectData {
    public static final Codec<AttackEffectData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Identifier.CODEC.fieldOf("effect").forGetter(AttackEffectData::getId),
            Codec.INT.fieldOf("triggerCount").forGetter(AttackEffectData::getTriggerCount)
    ).apply(i, AttackEffectData::of));
    @Nullable
    private AttackEffectInstance instance;
    protected final AttackEffect effect;
    protected final int durationTicks;
    protected int triggerCount;

    AttackEffectData(AttackEffect effect) {
        this.effect = effect;
        this.durationTicks = effect.getProperties().getDurationTicks();
    }

    AttackEffectData(AttackEffect effect, int triggerCount) {
        this(effect);
        this.triggerCount = triggerCount;
    }

    public static AttackEffectData of(AttackEffect effect) {
        return new AttackEffectData(effect);
    }

    public static AttackEffectData of(Identifier id, int triggerCount) {
        AttackEffect attackEffect = AttackEffectRegistry.REGISTRY.getValue(id);
        if(attackEffect == null) throw new RuntimeException("Canvas doesn't exist.");
        else return new AttackEffectData(attackEffect, triggerCount);
    }

    public AttackEffectInstance asInstance(LivingEntity owner) {
        if(this.instance != null && this.instance.getOwner() == owner) return instance;
        return this.instance = new AttackEffectInstance(owner, effect, triggerCount);
    }

    public int getTriggerCount() {
        return triggerCount;
    }

    public AttackEffect getEffect() {
        return effect;
    }

    public int getDurationTicks() {
        return durationTicks;
    }

    public Identifier getId() {
        return Optional.ofNullable(AttackEffectRegistry.REGISTRY.getKey(effect)).orElseThrow(
                () -> new NullPointerException("Can not find registry"));
    }
}
