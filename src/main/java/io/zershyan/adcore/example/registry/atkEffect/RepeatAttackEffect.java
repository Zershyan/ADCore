package io.zershyan.adcore.example.registry.atkEffect;

import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.api.helper.AttackEffectHelper;
import io.zershyan.adcore.api.helper.AttackHelper;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffect;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * @see AttackEffect
 */
public class RepeatAttackEffect extends AttackEffect {
    public RepeatAttackEffect(Properties properties) {
        super(properties);
    }

    @Override
    public void trigger(AttackEffectInstance instance, LivingEntity target, DamageSource source, float attackDamage, float applyScale) {
        if(instance.getOwner() instanceof Player player) {
            AttackHelper attackHelper = ADCoreAPI.attackHelper(player);
            DamageSource normalSource = attackHelper.createNormalSource();
            //When it is triggered by attack, applyScale is 1.0f. If it is triggered by another trigger, will be other value.
            if(normalSource != null) attackHelper.applyDamage(target, normalSource, 2.0f * applyScale);
        }
    }

    /**
     * Repeat info.
     * It will be got when {@link AttackEffectHelper#triggerAllEffects} be called.
     * If count in repeat info isn't zero, and the triggerOtherEffect is true.<br>
     * It will trigger all triggers according count.<br>
     * It will return fixed repeat info if enableRepeatInfo of properties is false.
     * @param instance AttackEffectInstance
     * @param target target
     * @return RepeatInfo
     * @see AttackEffectHelper#triggerAllEffects
     * @see AttackEffect.RepeatInfo
     */
    @Override
    public RepeatInfo getRepeatInfo(AttackEffectInstance instance, LivingEntity target) {
        int triggerCount = instance.getTriggerCount();
        if(triggerCount != 0 && triggerCount % 3 == 0) return new RepeatInfo(1, true, 1.0f);
        else return super.getRepeatInfo(instance, target);
    }
}
