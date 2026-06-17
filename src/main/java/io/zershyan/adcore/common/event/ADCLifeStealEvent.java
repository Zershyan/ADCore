package io.zershyan.adcore.common.event;

import io.zershyan.adcore.common.registry.ModAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

/**
 * Calculating life steal.
 */
public class ADCoreLifeStealEvent extends Event {
    private final LivingEntity attacker;
    private final DamageSource source;
    private final float damage;
    private final float attackLifeStealRate;
    private final float almightyLifeStealRate;
    private float newAttackLifeStealRate;
    private float newAlmightyLifeStealRate;
    private boolean isCancelled = false;
    public ADCoreLifeStealEvent(LivingEntity attacker, DamageSource source, float damage, float atkLSR, float amtLSR) {
        this.attacker = attacker;
        this.source = source;
        this.damage = damage;
        this.attackLifeStealRate = atkLSR;
        this.newAttackLifeStealRate = atkLSR;
        this.almightyLifeStealRate = amtLSR;
        this.newAlmightyLifeStealRate = amtLSR;
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public DamageSource getSource() {
        return source;
    }

    public float getDamage() {
        return damage;
    }

    public float getAlmightyLifeStealRate() {
        return almightyLifeStealRate;
    }

    public float getAttackLifeStealRate() {
        return attackLifeStealRate;
    }

    public float getNewAlmightyLifeStealRate() {
        return newAlmightyLifeStealRate;
    }

    public float getNewAttackLifeStealRate() {
        return newAttackLifeStealRate;
    }

    public float getTotalLifeSteal() {
        return (float) ModAttributes.ALMIGHTY_LIFE_STEAL.value().sanitizeValue(newAlmightyLifeStealRate + newAttackLifeStealRate);
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setNewAlmightyLifeStealRate(float newAlmightyLifeStealRate) {
        this.newAlmightyLifeStealRate = newAlmightyLifeStealRate;
    }

    public void setNewAttackLifeStealRate(float newAttackLifeStealRate) {
        this.newAttackLifeStealRate = newAttackLifeStealRate;
    }

    public void setCancel(boolean cancel) {
        isCancelled = cancel;
    }
}
