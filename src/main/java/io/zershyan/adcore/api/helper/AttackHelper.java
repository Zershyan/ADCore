package io.zershyan.adcore.api.helper;

import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.event.ADCAttackEvent;
import io.zershyan.adcore.common.registry.ADCAttributes;
import io.zershyan.adcore.common.registry.ADCDamageTypes;
import io.zershyan.adcore.common.registry.ADCSounds;
import io.zershyan.adcore.common.utils.PlayerUtils;
import io.zershyan.adcore.config.CommonConfig;
import io.zershyan.adcore.util.mixin.IMixinLivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

/**
 * This helper uses to attack another entity.
 * @see ADCoreAPI#attackHelper
 */
public class AttackHelper extends ADCHelper {
    protected final LivingEntity attacker;

    public AttackHelper(LivingEntity attacker) {
        super(attacker);
        this.attacker = attacker;
    }

    /**
     * Get attack type according to distance with target
     * @param target target
     * @return If ranged atk.
     */
    public boolean isRangedAtk(Entity target) {
        double distance = attacker.distanceToSqr(target);
        boolean flag1 = attacker.level().dimension() != target.level().dimension();
        AttributeInstance attribute = attacker.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
        double reach = attribute != null ? attribute.getValue() + 1 : 0;
        boolean flag2 = distance > reach * reach;
        return flag1 || flag2;
    }

    //Ranged attribute if distance overreaches.
    public float getAtkWithTarget(Entity target) {
        return isRangedAtk(target) ? getRangedAtk() : getMeleeAtk();
    }

    //Ranged attribute if distance overreaches.
    public float getPenetrationWithTarget(Entity target) {
        return isRangedAtk(target) ? getRangedPenetration() : getMeleePenetration();
    }

    //Ranged attribute if distance overreaches.
    public float getPenetrationRateWithTarget(Entity target) {
        return isRangedAtk(target) ? getRangedPenetrationRate() : getMeleePenetrationRate();
    }

    //Attack damage source only uses when entity attacks another entity.
    @Nullable
    public DamageSource createAttackSource() {
        return createDamageSource(ADCDamageTypes.ATTACK_DAMAGE);
    }

    //Can use anywhere. It will not bypass armor.
    @Nullable
    public DamageSource createNormalSource() {
        return createDamageSource(ADCDamageTypes.NORMAL_DAMAGE);
    }

    //Can use anywhere. It will bypass armor.
    @Nullable
    public DamageSource createMagicSource() {
        return createDamageSource(ADCDamageTypes.MAGIC_DAMAGE);
    }

    //Can use anywhere. It will bypass all.
    @Nullable
    public DamageSource createTrueSource() {
        return createDamageSource(ADCDamageTypes.TRUE_DAMAGE);
    }

    @Nullable
    private DamageSource createDamageSource(ResourceKey<DamageType> damageType) {
        Holder<DamageType> holder = ADCDamageTypes.getDamageType(attacker.registryAccess(), damageType);
        return holder == null ? null : new DamageSource(holder, attacker);
    }

    private boolean applyDamage(Entity target) {
        if(!(attacker.level() instanceof ServerLevel level)) return false;
        if(target.asLivingEntity() == null) return false;
        ADCAttackEvent.Atk atkEvent = new ADCAttackEvent.Atk(attacker, getAtkWithTarget(target));
        float atk = NeoForge.EVENT_BUS.post(atkEvent).getAtk();
        float originDamage = (float) (atk * CommonConfig.atkDamageConvert.get());
        if(createAttackSource() instanceof DamageSource source) {
            float damage = critical().tryCritical(target, source, originDamage);
            damage = amplify().tryAmplify(target, damage);
            if(damage == 0) return true;
            boolean hurtSuccess = target.hurtServer(level, source, damage);
            NeoForge.EVENT_BUS.post(new ADCAttackEvent.Post(attacker, source, damage, hurtSuccess));
            attackEffect().triggerAllEffects(target, source, damage);
            return hurtSuccess;
        } else return false;
    }

    /**
     * Apply damage to entity. It is a quick method.
     * Can't apply attack damage with this function, please use {@link AttackHelper#tryAttack}.
     * @param target target
     * @param source damage source
     * @param amount damage amount
     * @return If success
     */
    public boolean applyDamage(Entity target, DamageSource source, float amount) {
        if(source.typeHolder() == ADCDamageTypes.ATTACK_DAMAGE) {
            throw new UnsupportedOperationException("Can't apply attack damage with this function, please use AttackHelper#tryAttack.");
        }
        if(!(attacker.level() instanceof ServerLevel level)) return false;
        if(target.asLivingEntity() == null) return false;
        return target.hurtServer(level, source, amount);
    }

    /**
     * Get attack cooldown according to atk speed.
     * Unit is ms.
     * @return cooldown
     */
    public int getAtkCooldownAttr() {
        return Math.round(1000 / Mth.clamp(getAtkSpeed(), 0.0f, 20.0f));
    }

    /**
     * Apply attack cooldown to entity.
     */
    public void applyAtkCooldown() {
        ((IMixinLivingEntity) attacker).adcore$setAtkCooldown(getAtkCooldownAttr());
    }

    /**
     * @return True if entity is in attack cooldown
     */
    public boolean isAtkInCooldown() {
        return ((IMixinLivingEntity) attacker).adcore$isInCooldownTime();
    }

    /**
     * Calculate hit rate if attack target.
     * Entity Hit Rate subtract Target Evasion Rate
     * @param target target
     * @return Result of cal
     */
    public float calHitRate(Entity target) {
        float hitRate = getHitRate();
        LivingEntity targetEntity = target.asLivingEntity();
        if(targetEntity == null) return hitRate;
        float evasionRate = ADCoreAPI.helper(targetEntity).attribute(ADCAttributes.EVASION_RATE).getValue(0.0f);
        return Mth.clamp(hitRate - evasionRate, 0.0f, 1.0f);
    }

    /**
     * Apply sound to hit failure and evasion success.
     * Only play to player.
     * @param target target
     */
    public void applyMissSound(Entity target) {
        if(attacker instanceof ServerPlayer attackerPlayer) {
            PlayerUtils.playClientSound(attackerPlayer, ADCSounds.HIT_FAILURE.value());
        }
        if(target instanceof ServerPlayer targetPlayer) {
            PlayerUtils.playClientSound(targetPlayer, ADCSounds.EVASION_SUCCESS.value());
        }
    }

    /**
     * Will call when attack miss.
     * @param target target
     */
    public void attackMiss(Entity target) {
        applyMissSound(target);
        applyAtkCooldown();
    }

    private void hurtAndBreak(Entity target) {
        if(!CommonConfig.adcoreItemDamage.get()) return;
        if(target instanceof LivingEntity targetLivingEntity) {
            attacker.getMainHandItem().postHurtEnemy(attacker, targetLivingEntity);
        }
    }

    /**
     * Try to apply damage directly
     * @param target target
     */
    public void tryApplyDamage(Entity target) {
        if(applyDamage(target)) {
            hurtAndBreak(target);
            applyAtkCooldown();
        }
    }

    /**
     * Calculate hit rate and try damage target.
     * @param target target
     */
    public void tryAttack(Entity target) {
        ADCAttackEvent.Cooldown cooldownEvent = new ADCAttackEvent.Cooldown(attacker, isAtkInCooldown());
        if(NeoForge.EVENT_BUS.post(cooldownEvent).isCooldown()) return;
        float hitRate = calHitRate(target);
        boolean isCanHit = attacker.getRandom().nextFloat() < hitRate;
        ADCAttackEvent.HitRate hitRateEvent = new ADCAttackEvent.HitRate(attacker, isCanHit);
        if(NeoForge.EVENT_BUS.post(hitRateEvent).isCanHit())
            tryApplyDamage(target);
        else attackMiss(target);
    }

    public LivingEntity getAttacker() {
        return attacker;
    }
}
