package io.zershyan.adcore.common.registry.atkEffect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;


public class AttackEffectInstance extends AttackEffectData {
    @NotNull
    private final LivingEntity owner;
    private int timeCounter;
    private int tickCount = 0;

    AttackEffectInstance(@NotNull LivingEntity owner, AttackEffect effect, int triggerCount) {
        super(effect, triggerCount);
        this.owner = owner;
    }

    public void tick() {
        effect.tick(this);
        tickCount++;
        if(timeCounter > 0) {
            if(--timeCounter == 0)
                durationEnd();
        }
        if(triggerCount > 0) {
            if(tickCount % 20 == 0)
                getAttributeModifiers();
        }
    }

    public void trigger(LivingEntity target, DamageSource source, final float attackDamage, final float applyScale) {
        setTriggerCount(triggerCount + 1);
        effect.triggerSound(this, target);
        triggerDontCount(target, source, attackDamage, applyScale);
    }

    public void triggerDontCount(LivingEntity target, DamageSource source, final float attackDamage, final float applyScale) {
        timeCounter = durationTicks;
        effect.trigger(this, target, source, attackDamage, applyScale);
        getAttributeModifiers();
    }

    public AttackEffect.RepeatInfo getRepeatInfo(LivingEntity target) {
        return effect.repeatInfo(this, target);
    }

    public void durationEnd() {
        removeAttributeModifiers();
        setTriggerCount(0);
        effect.durationEnd(this);
    }

    private void setTriggerCount(int triggerCount) {
        int maxTriggerCount = effect.getProperties().getMaxTriggerCount();
        int count = Math.max(0, triggerCount);
        if(count >= maxTriggerCount) count -= maxTriggerCount;
        this.triggerCount = count;
    }

    public void getAttributeModifiers() {
        owner.getAttributes().addTransientAttributeModifiers(effect.getAttributeModifiers(this));
    }

    public void removeAttributeModifiers() {
        owner.getAttributes().removeAttributeModifiers(effect.getAttributeModifiers(this));
    }

    public @NotNull LivingEntity getOwner() {
        return owner;
    }
}
