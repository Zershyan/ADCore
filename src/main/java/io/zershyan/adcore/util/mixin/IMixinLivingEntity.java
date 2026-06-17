package io.zershyan.adcore.util;

public interface IMixinLivingEntity {
    boolean adcore$isInCooldownTime();

    void adcore$setAtkCooldown(int atkCooldown);
}
