package io.zershyan.adcore.common.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

/**
 * You can use this event to control all the damage about if cause ADCore damage only.
 * If onlyADCoreCausingDamage is true, all the original damage will be canceled.
 */
public class ADCExclusiveHurtEvent extends Event {
    private final LivingEntity entity;
    private final DamageSource source;
    private boolean onlyADCoreCausingDamage;
    public ADCExclusiveHurtEvent(LivingEntity entity, DamageSource source, boolean onlyADCoreCausingDamage) {
        this.entity = entity;
        this.source = source;
        this.onlyADCoreCausingDamage = onlyADCoreCausingDamage;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public DamageSource getSource() {
        return source;
    }

    public boolean isOnlyADCoreCausingDamage() {
        return onlyADCoreCausingDamage;
    }

    public void setOnlyADCoreCausingDamage(boolean onlyADCoreCausingDamage) {
        this.onlyADCoreCausingDamage = onlyADCoreCausingDamage;
    }
}
