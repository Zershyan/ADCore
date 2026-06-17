package io.zershyan.adcore.common.registry.attackEffects;

import com.google.common.collect.Multimap;
import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.registry.ADCAttributes;
import io.zershyan.adcore.common.registry.attackEffects.create.AttackEffectInstance;
import io.zershyan.adcore.common.registry.attackEffects.create.AttackEffect;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class QuickAttackEffect extends AttackEffect {
    public QuickAttackEffect() {
        super(Properties.of().durationTicks(100));
    }

    @Override
    public void trigger(AttackEffectInstance instance, LivingEntity target, DamageSource source, float attackDamage, float applyScale) {
        int triggerCount = instance.getTriggerCount();
        if(instance.getOwner() instanceof Player player) {
//            player.sendSystemMessage(Component.literal("总共第" + triggerCount + "次攻击"));
//            if(triggerCount % 3 == 0) {
//                player.sendSystemMessage(Component.literal("每3次攻击会触发一次这个消息。"));
//            }
            float atkSpeed = ADCoreAPI.attributeHelper(player).getAtkSpeed();
            player.sendSystemMessage(Component.literal("第" + triggerCount + "层§d速攻§r，目前攻速加成：" + 5 * triggerCount + "%，目前攻速：" + atkSpeed));
        }
    }

//    @Override
//    public boolean isNeedTriggerAgain(LivingEntity entity, AttackEffectInstance instance) {
//        return instance.getTriggerCount() % 3 == 0;
//    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(AttackEffectInstance instance) {
        Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers = super.getAttributeModifiers(instance);
        AttributeModifier exampleAtkEffAtkSpeed = new AttributeModifier(
                Identifier.fromNamespaceAndPath(ADCore.MODID, "example_atk_eff_atk_speed"),
                0.05 * instance.getTriggerCount(),
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );
        attributeModifiers.put(ADCAttributes.ATK_SPEED, exampleAtkEffAtkSpeed);
        return attributeModifiers;
    }
}
