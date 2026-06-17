package io.zershyan.adcore.common.event;

import io.zershyan.adcore.common.registry.ADCAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

/**
 * Before heal-amplify be applied.
 */
public class ADCHealAmplifyEvent extends Event {
    private final float originRate;
    private final LivingEntity entity;
    private float newRate;
    private boolean isCancelled = false;
    public ADCHealAmplifyEvent(LivingEntity entity, float rate) {
        this.entity = entity;
        this.originRate = rate;
        this.newRate = rate;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public float getOriginRate() {
        return originRate;
    }

    public float getNewRate() {
        return (float) ADCAttributes.HEAL_AMPLIFY.value().sanitizeValue(newRate);
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setNewRate(float newRate) {
        this.newRate = newRate;
    }

    public void setCancel(boolean cancel) {
        isCancelled = cancel;
    }
}
