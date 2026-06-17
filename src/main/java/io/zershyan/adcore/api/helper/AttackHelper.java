package io.zershyan.adcore.api.helper;

import io.zershyan.adcore.common.registry.ModAttributes;
import io.zershyan.adcore.common.registry.ModDamageTypes;
import io.zershyan.adcore.config.ModConfigs;
import io.zershyan.adcore.util.IMixinLivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.AttackRange;
import org.jetbrains.annotations.Nullable;

public class ADCoreLivingHelper {
    private final LivingEntity entity;

    public ADCoreLivingHelper(LivingEntity entity) {
        this.entity = entity;
    }

    @Nullable
    public DamageSource createDamageSource(ResourceKey<DamageType> key) {
        Holder<DamageType> damageType = ModDamageTypes.getDamageType(entity.registryAccess(), key);
        return damageType == null ? null : new DamageSource(damageType, entity);
    }

    public void applyMeleeDamage(Entity target) {
        applyDamage(target, entity.getAttribute(ModAttributes.MELEE_ATK));
    }

    public void applyRangedDamage(Entity target) {
        applyDamage(target, entity.getAttribute(ModAttributes.RANGED_ATK));
    }

    public void applyDamage(Entity target) {
        if(isRangedAtk(target)) {
            applyRangedDamage(target);
        } else applyMeleeDamage(target);
    }

    private void applyDamage(Entity target, @Nullable AttributeInstance atk) {
        if(atk == null) return;
        if(getAtkCooldown() != 0) return;
        if(!(entity.level() instanceof ServerLevel level)) return;
        if(target.asLivingEntity() == null) return;
        float originDamage = (float) (atk.getValue() * ModConfigs.Common.atkDamageConvert.get());
        if(createDamageSource(ModDamageTypes.NORMAL_DAMAGE) instanceof DamageSource source) {
            if(originDamage == 0) return;
            if(target.hurtServer(level, source, originDamage)) applyAtkCooldown();
        }
    }

    public boolean isRangedAtk(Entity target) {
        double distance = entity.distanceToSqr(target);
        boolean flag1 = entity.level().dimension() != target.level().dimension();
        AttackRange attackRange = entity.get(DataComponents.ATTACK_RANGE);
        boolean flag2 = attackRange != null && distance > attackRange.maxReach() + 1;
        return flag1 || flag2;
    }

    public boolean isMeleeAtk(Entity target) {
        return !isRangedAtk(target);
    }

    public int getAtkCooldownAttr() {
        AttributeInstance attribute = entity.getAttribute(ModAttributes.ATK_SPEED);
        if(attribute == null) return 0;
        double atkSpeed = attribute.getValue();
        return (int) (atkSpeed * 20);
    }

    public int getAtkCooldown() {
        return ((IMixinLivingEntity) entity).adcore$getAtkCooldown();
    }

    public void applyAtkCooldown() {
        ((IMixinLivingEntity) entity).adcore$setAtkCooldown(getAtkCooldownAttr());
    }

    public float getPenetrationRate(Entity target) {
        return isRangedAtk(target) ? getRangedPenetrationRate() : getMeleePenetrationRate();
    }

    public float getMeleePenetrationRate() {
        return getPenetrationRate(entity.getAttribute(ModAttributes.MELEE_PENETRATION_RATE));
    }

    public float getRangedPenetrationRate() {
        return getPenetrationRate(entity.getAttribute(ModAttributes.RANGED_PENETRATION_RATE));
    }

    private float getPenetrationRate(@Nullable AttributeInstance penetration) {
        if(penetration == null) return 0.0f;
        float num = 1.0f;
        for (AttributeModifier modifier : penetration.getModifiers()) {
            if(modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE) {
                num *= 1.0f - (float) modifier.amount();
            }
        }
        float result = 1.0f - num;
        for (AttributeModifier modifier : penetration.getModifiers()) {
            if(modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                result *= 1.0f + (float) modifier.amount();
            }
        }
        return result;
    }

    public double getPenetration(Entity target) {
        return isRangedAtk(target) ? getRangedPenetration() : getMeleePenetration();
    }

    public double getMeleePenetration() {
        return getPenetration(entity.getAttribute(ModAttributes.MELEE_PENETRATION));
    }

    public double getRangedPenetration() {
        return getPenetration(entity.getAttribute(ModAttributes.RANGED_PENETRATION));
    }

    private double getPenetration(@Nullable AttributeInstance penetration) {
        if(penetration == null) return 0;
        return penetration.getValue();
    }
}
