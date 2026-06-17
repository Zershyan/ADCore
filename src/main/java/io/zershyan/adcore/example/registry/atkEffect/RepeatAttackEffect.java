package io.zershyan.adcore.common.registry.attackEffects;

import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.api.helper.AttackHelper;
import io.zershyan.adcore.common.registry.attackEffects.create.AttackEffect;
import io.zershyan.adcore.common.registry.attackEffects.create.AttackEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RepeatAttackEffect extends AttackEffect {
    public RepeatAttackEffect() {
        super(Properties.of().durationTicks(100).enableRepeatInfo());
    }

    @Override
    public void trigger(AttackEffectInstance instance, LivingEntity target, DamageSource source, float attackDamage, float applyScale) {
        if(instance.getOwner() instanceof Player player) {
            AttackHelper attackHelper = ADCoreAPI.attackHelper(player);
            DamageSource normalSource = attackHelper.createNormalSource();
            if(normalSource != null) attackHelper.applyDamage(target, normalSource, 1.0f);
        }
    }

    @Override
    public RepeatInfo getRepeatInfo(AttackEffectInstance instance, LivingEntity target) {
        int triggerCount = instance.getTriggerCount();
        if(triggerCount != 0 && triggerCount % 3 == 0) return new RepeatInfo(1, true, 1.0f);
        else return super.getRepeatInfo(instance, target);
    }
}
