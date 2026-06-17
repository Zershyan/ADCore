package io.zershyan.adcore.common.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public class ADCoreExclusiveHurtEvent extends Event {
    private final LivingEntity entity;
    private final DamageSource source;
    private boolean onlyADCoreCausingDamage;
    public ADCoreExclusiveHurtEvent(LivingEntity entity, DamageSource source, boolean onlyADCoreCausingDamage) {
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
