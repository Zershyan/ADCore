package io.zershyan.adcore.common.event;

import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.registry.ModAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public abstract class ADCoreAttackEvent extends Event {
    private final LivingEntity attacker;
    private boolean isCancelled = false;
    private final boolean cancelable;
    public ADCoreAttackEvent(LivingEntity attacker) {
        this.attacker = attacker;
        this.cancelable = false;
    }

    public ADCoreAttackEvent(LivingEntity attacker, boolean cancelable) {
        this.attacker = attacker;
        this.cancelable = cancelable;
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancel(boolean cancel) {
        if(!cancelable) return;
        this.isCancelled = cancel;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    /**
     * Before all the attack logic.
     */
    public static class Pre extends ADCoreAttackEvent {
        public Pre(LivingEntity attacker) {
            super(attacker, true);
        }
    }

    /**
     * Attack-cooldown detecting now.
     * It isn't cancelable.
     */
    public static class Cooldown extends ADCoreAttackEvent {
        private boolean isCooldown;
        public Cooldown(LivingEntity attacker, boolean isCooldown) {
            super(attacker);
            this.isCooldown = isCooldown;
        }

        public boolean isCooldown() {
            return isCooldown;
        }

        public void setCooldown(boolean cooldown) {
            isCooldown = cooldown;
        }
    }

    /**
     * Hit judgment now.
     * It isn't cancelable.
     */
    public static class HitRate extends ADCoreAttackEvent {
        private boolean canHit;
        public HitRate(LivingEntity attacker, boolean isCanHit) {
            super(attacker);
            this.canHit = isCanHit;
        }

        public boolean isCanHit() {
            return canHit;
        }

        public void setCanHit(boolean canHit) {
            this.canHit = canHit;
        }
    }

    /**
     * Getting atk now.
     * It isn't cancelable.
     */
    public static class Atk extends ADCoreAttackEvent {
        private float atk;
        public Atk(LivingEntity attacker, float atk) {
            super(attacker);
            this.atk = atk;
        }

        public float getAtk() {
            return (float) ModAttributes.MELEE_ATK.value().sanitizeValue(atk);
        }

        public void setAtk(float atk) {
            this.atk = atk;
        }
    }

    /**
     * Before calculate critical.
     * Rate: 1.0 == 100%
     */
    public static class PreCalCritical extends ADCoreAttackEvent {
        private final float originDamage;
        private final DamageSource source;
        private float criticalRate;
        private float newDamage;
        public PreCalCritical(LivingEntity attacker, DamageSource source, float criticalRate, float originDamage) {
            super(attacker, true);
            this.source = source;
            this.criticalRate = criticalRate;
            this.originDamage = originDamage;
            this.newDamage = originDamage;
        }

        public float getOriginDamage() {
            return originDamage;
        }

        public float getCriticalRate() {
            return (float) ModAttributes.CRITICAL_RATE.value().sanitizeValue(criticalRate);
        }

        public void setCriticalRate(float criticalRate) {
            this.criticalRate = criticalRate;
        }

        public float getNewDamage() {
            return newDamage;
        }

        public void setNewDamage(float newDamage) {
            this.newDamage = newDamage;
        }

        public DamageSource getSource() {
            return source;
        }
    }

    /**
     * Before calculate amplify.
     */
    public static class PreCalAmplify extends ADCoreAttackEvent {
        private final float originDamage;
        private float newDamage;
        public PreCalAmplify(LivingEntity attacker, float originDamage) {
            super(attacker, true);
            this.originDamage = originDamage;
            this.newDamage = originDamage;
        }

        public float getOriginDamage() {
            return originDamage;
        }

        public float getNewDamage() {
            return newDamage;
        }

        public void setNewDamage(float newDamage) {
            this.newDamage = newDamage;
        }
    }

    /**
     * After the damage application.
     * It isn't cancelable.
     */
    public static class Post extends ADCoreAttackEvent {
        private final DamageSource source;
        private final float damage;
        private final boolean hurtSuccess;
        public Post(LivingEntity attacker, DamageSource source, float damage, boolean hurtSuccess) {
            super(attacker);
            this.source = source;
            this.damage = damage;
            this.hurtSuccess = hurtSuccess;
        }

        public DamageSource getSource() {
            return source;
        }

        public float getDamage() {
            return damage;
        }

        public boolean isHurtSuccess() {
            return hurtSuccess;
        }

        public boolean isCritical() {
            return ADCoreAPI.criticalHelper(getAttacker()).isCritical(source);
        }
    }
}
