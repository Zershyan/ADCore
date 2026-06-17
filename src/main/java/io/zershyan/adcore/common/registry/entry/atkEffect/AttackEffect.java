package io.zershyan.adcore.common.registry.entry.atkEffect;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.zershyan.adcore.api.helper.AttackEffectHelper;
import io.zershyan.adcore.common.utils.PlayerUtils;
import io.zershyan.adcore.config.ServerConfig;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

/**
 * You can register it.
 * @see io.zershyan.adcore.example.registry.ExampleAttackEffects
 */
public abstract class AttackEffect {
    private final Properties properties;
    public AttackEffect(Properties properties) {
        this.properties = properties;
    }

    /**
     * If it is in duration, it will be called.
     * @param instance AttackEffectInstance
     */
    public void tick(AttackEffectInstance instance) {}

    /**
     * Will be called when trigger attack effect.
     * @param instance attack effect instance
     * @param target target
     * @param source damage source
     * @param attackDamage attack damage
     * @param applyScale apply scale
     */
    public abstract void trigger(AttackEffectInstance instance, LivingEntity target, DamageSource source, final float attackDamage, final float applyScale);

    /**
     * If duration ends, it will be called.
     * @param instance AttackEffectInstance
     */
    public void durationEnd(AttackEffectInstance instance) {}

    public final RepeatInfo repeatInfo(AttackEffectInstance instance, LivingEntity target) {
        if(!properties.enableRepeatInfo) return new RepeatInfo(0, false, 1.0f);
        else return getRepeatInfo(instance, target);
    }

    /**
     * Repeat info.
     * It will be got when {@link AttackEffectHelper#triggerAllEffects} be called.
     * If count in repeat info isn't zero, and the triggerOtherEffect is true.<br>
     * It will trigger all triggers according count.<br>
     * It will return fixed repeat info if enableRepeatInfo of properties is false.
     * @param instance AttackEffectInstance
     * @param target target
     * @return RepeatInfo
     * @see AttackEffectHelper#triggerAllEffects
     * @see AttackEffect.RepeatInfo
     */
    public RepeatInfo getRepeatInfo(AttackEffectInstance instance, LivingEntity target) {
        return new RepeatInfo(0, false, 1.0f);
    }

    public void triggerSound(AttackEffectInstance instance, LivingEntity target) {
        Holder<SoundEvent> sounds = getTriggerSound(target);
        if(sounds == null) return;
        if(instance.getOwner() instanceof ServerPlayer serverPlayer) {
            PlayerUtils.playClientSound(serverPlayer, sounds.value());
        }
    }

    @Nullable
    public Holder<SoundEvent> getTriggerSound(LivingEntity target) {
        return null;
    }

    /**
     * Attribute modify
     * @param instance attack effect instance
     * @return Multimap
     */
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(AttackEffectInstance instance) {
        return HashMultimap.create();
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * durationTicks: The trigger count duration.<br>
     * maxTriggerCount: The max trigger count.<br>
     * enableRepeatInfo: {@link AttackEffect#getRepeatInfo}
     */
    public static class Properties {
        private int durationTicks = -1;
        private int maxTriggerCount = -1;
        private boolean enableRepeatInfo = false;
        Properties() {}

        public static Properties of() {
            return new Properties();
        }

        public Properties noDuration() {
            return durationTicks(-1);
        }

        public Properties noMaxTriggerCount() {
            return maxTriggerCount(-1);
        }

        public Properties enableRepeatInfo() {
            this.enableRepeatInfo = true;
            return this;
        }

        public Properties durationTicks(int durationTicks) {
            this.durationTicks = durationTicks;
            return this;
        }

        public Properties maxTriggerCount(int maxTriggerCount) {
            this.maxTriggerCount = maxTriggerCount;
            return this;
        }

        public int getDurationTicks() {
            return durationTicks;
        }

        public int getMaxTriggerCount() {
            return maxTriggerCount == -1 ? Integer.MAX_VALUE : maxTriggerCount;
        }

        public boolean isEnableRepeatInfo() {
            return enableRepeatInfo;
        }
    }

    public record RepeatInfo(int count, boolean triggerOtherEffect, float applyScale) {
        @Override
        public float applyScale() {
            return Math.max(applyScale, 0);
        }

        @Override
        public int count() {
            return Mth.clamp(count, 0, ServerConfig.maxAttackEffectRepeatCount.get());
        }
    }
}
