package io.zershyan.adcore.api.helper;

import com.mojang.logging.LogUtils;
import io.zershyan.adcore.common.registry.ADCAttachments;
import io.zershyan.adcore.common.registry.entry.condition.modifier.ConditionModifier;
import io.zershyan.adcore.common.registry.entry.condition.modifier.ConditionModifierMap;
import io.zershyan.adcore.util.mixin.IMixinAttributeInstance;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.*;

public class AttributeHelperV2 extends ADCHelper{
    private final LivingEntity entity;
    public AttributeHelperV2(LivingEntity entity) {
        super(entity);
        this.entity = entity;
    }

    public float getValue(Holder<Attribute> attribute, float defaultValue) {
        try {
            return getValue(attribute).floatValue();
        } catch (Exception e) {
            LogUtils.getLogger().error(e.getMessage());
            return defaultValue;
        }
    }

    public boolean getValue(Holder<Attribute> attribute, boolean defaultValue) {
        return getValue(attribute, defaultValue ? 1.0f : 0.0f) == 1.0f;
    }

    public Double getValue(Holder<Attribute> attribute) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) throw new RuntimeException("The attribute does not exist.");
        if(entity.hasData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER)) {
            Map<Holder<Attribute>, ConditionModifierMap> modifierMapMap = entity.getData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER);
            if(modifierMapMap.containsKey(attribute)) {
                ConditionModifierMap modifierMap = modifierMapMap.get(attribute);
                double base = instance.getBaseValue();
                for(AttributeModifier modifier : getOriginModifiers(AttributeModifier.Operation.ADD_VALUE, instance))
                    base += modifier.amount();
                for (ConditionModifier conditionModifier : modifierMap.getModifiersOrEmpty(AttributeModifier.Operation.ADD_VALUE))
                    if(conditionModifier.testCondition()) base += conditionModifier.amount();

                double result = base;
                for(AttributeModifier modifier : getOriginModifiers(AttributeModifier.Operation.ADD_MULTIPLIED_BASE, instance))
                    result += base * modifier.amount();
                for (ConditionModifier conditionModifier : modifierMap.getModifiersOrEmpty(AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    if(conditionModifier.testCondition()) result += base * conditionModifier.amount();

                for(AttributeModifier modifier : getOriginModifiers(AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, instance))
                    result *= (double)1.0F + modifier.amount();
                for(ConditionModifier conditionModifier : modifierMap.getModifiersOrEmpty(AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                    result *= (double)1.0F + conditionModifier.amount();

                return attribute.value().sanitizeValue(result);
            }
        }
        return instance.getValue();
    }

    private static Collection<AttributeModifier> getOriginModifiers(AttributeModifier.Operation operation, AttributeInstance instance) {
        return ((IMixinAttributeInstance) instance).adcore$getModifiersOrEmpty(operation);
    }

    public void modifyAttributeTransient(Holder<Attribute> attribute, AttributeModifier modifier) {
        modifyAttribute(attribute, modifier, false);
    }

    public void modifyAttributePermanent(Holder<Attribute> attribute, AttributeModifier modifier) {
        modifyAttribute(attribute, modifier, true);
    }

    private void modifyAttribute(Holder<Attribute> attribute, AttributeModifier modifier, boolean permanent) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) return;
        if(permanent) instance.addOrReplacePermanentModifier(modifier);
        else instance.addOrUpdateTransientModifier(modifier);
    }

    public void modifyAttributeCondition(Holder<Attribute> attribute, ConditionModifier modifier) {
        Map<Holder<Attribute>, ConditionModifierMap> modifierMapMap = new HashMap<>(entity.getData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER));
        ConditionModifierMap modifierMap;
        if(!modifierMapMap.containsKey(attribute)) {
            modifierMap = new ConditionModifierMap();
            modifierMapMap.put(attribute, modifierMap);
        } else modifierMap = modifierMapMap.get(attribute);
        AttributeModifier.Operation operation = modifier.operation();
        Map<Identifier, ConditionModifier> map;
        if(modifierMap.containsKey(operation)) {
            map = new HashMap<>();
            modifierMap.put(operation, map);
        }else map = modifierMap.get(operation);
        map.put(modifier.id(), modifier);
        entity.setData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER, modifierMapMap);
    }

    public void removeModifier(Holder<Attribute> attribute, Identifier id) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) return;
        instance.removeModifier(id);
    }

    private void removeConditionModifier(Holder<Attribute> attribute, Identifier id) {
        Map<Holder<Attribute>, ConditionModifierMap> modifierMapMap = new HashMap<>(entity.getData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER));
        if(modifierMapMap.containsKey(attribute)) {
            ConditionModifierMap modifierMap = modifierMapMap.get(attribute);
            modifierMap.values()
        }

    }
}
