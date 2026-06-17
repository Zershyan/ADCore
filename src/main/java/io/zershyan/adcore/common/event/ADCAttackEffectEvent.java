package io.zershyan.adcore.common.event;

import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffect;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public abstract class ADCAttackEffectEvent extends Event {
    private final LivingEntity target;
    private final AttackEffectInstance effect;
    private final DamageSource damageSource;
    private final DamageSource effectSource;
    private final float attackDamage;

    public ADCAttackEffectEvent(LivingEntity target, AttackEffectInstance effect, DamageSource damageSource, DamageSource effectSource, float attackDamage) {
        this.target = target;
        this.effect = effect;
        this.damageSource = damageSource;
        this.effectSource = effectSource;
        this.attackDamage = attackDamage;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public AttackEffectInstance getEffect() {
        return effect;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public DamageSource getEffectSource() {
        return effectSource;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public static class Pre extends ADCAttackEffectEvent {
        private boolean isCanceled;

        public Pre(LivingEntity target, AttackEffectInstance effect, DamageSource damageSource, DamageSource effectSource, float amount) {
            super(target, effect, damageSource, effectSource, amount);
        }

        public void setCancel(boolean canceled) {
            isCanceled = canceled;
        }

        public boolean isCanceled() {
            return isCanceled;
        }
    }

    public static class Post extends ADCAttackEffectEvent {
        private final AttackEffect.RepeatInfo repeatInfo;

        public Post(LivingEntity target, AttackEffectInstance effect, AttackEffect.RepeatInfo repeatInfo, DamageSource damageSource, DamageSource effectSource, float amount) {
            super(target, effect, damageSource, effectSource, amount);
            this.repeatInfo = repeatInfo;
        }

        public AttackEffect.RepeatInfo getRepeatInfo() {
            return repeatInfo;
        }
    }
}
