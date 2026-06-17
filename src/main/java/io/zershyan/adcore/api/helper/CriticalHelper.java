package io.zershyan.adcore.api.helper;

import io.zershyan.adcore.common.event.ADCAttackEvent;
import io.zershyan.adcore.common.utils.PlayerUtils;
import io.zershyan.adcore.util.mixin.IMixinDamageSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;

/**
 * This helper uses to calculate critical
 */
public class CriticalHelper extends AttackHelper {
    private final LivingEntity attacker;
    public CriticalHelper(LivingEntity attacker) {
        super(attacker);
        this.attacker = attacker;
    }

    public float calculateCritical(float damage) {
        return damage * getCriticalDamage();
    }

    /**
     * Set critical to the damage source
     * @param source source to set
     */
    public void setCritical(DamageSource source) {
        ((IMixinDamageSource) source).adcore$critical(true);
    }

    /**
     * Return true if damage is critical.
     * @param source source
     * @return boolean
     */
    public boolean isCritical(DamageSource source) {
        return ((IMixinDamageSource) source).adcore$isCritical();
    }

    private float critical(Entity target, DamageSource source, float damage) {
        setCritical(source);
        if(attacker instanceof ServerPlayer player) {
            PlayerUtils.playServerSound(player, null, SoundEvents.PLAYER_ATTACK_CRIT);
            player.crit(target);
        }
        return calculateCritical(damage);
    }

    /**
     * Try to apply critical to damage value.
     * @param target Target
     * @param source Damage source
     * @param damage Original damage value
     * @return Calculated value
     */
    public float tryCritical(Entity target, DamageSource source, float damage) {
        LivingEntity targetEntity = target.asLivingEntity();
        if(targetEntity == null) return damage;
        var critical = new ADCAttackEvent.PreCalCritical(attacker, targetEntity, source, getCriticalRate(), damage);
        ADCAttackEvent.PreCalCritical event = NeoForge.EVENT_BUS.post(critical);
        if(event.isCanceled()) return damage;
        float rate = event.getCriticalRate();
        damage = event.getNewDamage();
        if (attacker.getRandom().nextFloat() < rate) {
            return critical(target, source, damage);
        } else return damage;
    }
}
