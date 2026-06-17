package io.zershyan.adcore.example.registry.atkEffect;

import com.google.common.collect.Multimap;
import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.registry.ADCAttributes;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffect;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

/**
 * @see AttackEffect
 */
public class QuickAttackEffect extends AttackEffect {
    public QuickAttackEffect(Properties properties) {
        super(properties);
    }

    /**
     * Will be called when trigger attack effect.
     * @param instance attack effect instance
     * @param target target
     * @param source damage source
     * @param attackDamage attack damage
     * @param applyScale apply scale
     */
    @Override
    public void trigger(AttackEffectInstance instance, LivingEntity target, DamageSource source, float attackDamage, float applyScale) {
        int triggerCount = instance.getTriggerCount();
        if(instance.getOwner() instanceof Player player) {
            float atkSpeed = ADCoreAPI.helper(player).getAtkSpeed();
            player.sendSystemMessage(Component.literal("第" + triggerCount + "层§d速攻§r，目前攻速加成：" + 5 * triggerCount + "%，目前攻速：" + atkSpeed));
        }
    }

    /**
     * Attribute modify
     * @param instance attack effect instance
     * @return Multimap
     */
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
