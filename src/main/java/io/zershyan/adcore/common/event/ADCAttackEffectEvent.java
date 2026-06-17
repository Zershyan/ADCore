package io.zershyan.adcore.common.event;

import io.zershyan.adcore.common.registry.attackEffects.create.AttackEffect;
import io.zershyan.adcore.common.registry.attackEffects.create.AttackEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public class ADCRawAttackEffectEvent extends Event {
    private final LivingEntity target;
    private final AttackEffectInstance effect;
    private final AttackEffect.RepeatInfo repeatInfo;
    private final DamageSource damageSource;
    private final DamageSource effectSource;
    private final float amount;
    private boolean isCanceled;

    public ADCRawAttackEffectEvent(LivingEntity target, AttackEffectInstance effect, DamageSource damageSource, DamageSource effectSource, float amount) {
        this.target = target;
        this.effect = effect;
        this.repeatInfo = effect.getRepeatInfo(target);
        this.damageSource = damageSource;
        this.effectSource = effectSource;
        this.amount = amount;
    }

    public void setCancel(boolean canceled) {
        isCanceled = canceled;
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

    public float getAmount() {
        return amount;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public AttackEffect.RepeatInfo getRepeatInfo() {
        return repeatInfo;
    }
}
