package io.zershyan.adcore.util.mixin;

public interface IMixinLivingEntity {
    boolean adcore$isInCooldownTime();

    void adcore$setAtkCooldown(int atkCooldown);
}
