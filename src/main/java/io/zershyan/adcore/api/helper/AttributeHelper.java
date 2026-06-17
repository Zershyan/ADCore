package io.zershyan.adcore.api.helper;

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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AttributeHelper extends ADCHelper{
    private final Holder<Attribute> attribute;
    public AttributeHelper(LivingEntity entity, Holder<Attribute> attribute) {
        super(entity);
        this.attribute = attribute;
    }

    public float getValue(float defaultValue) {
        try {
            return getValue().floatValue();
        } catch (Exception e) {
            return (float) attribute.value().sanitizeValue(defaultValue);
        }
    }

    public boolean getValue(boolean defaultValue) {
        return getValue(defaultValue ? 1.0f : 0.0f) == 1.0f;
    }

    public Double getValue() {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) throw new RuntimeException("The attribute does not exist.");
        if(entity.hasData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER)) {
            Map<Holder<Attribute>, ConditionModifierMap> modifierMapMap = entity.getData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER);
            if(modifierMapMap.containsKey(attribute)) {
                ConditionModifierMap modifierMap = modifierMapMap.get(attribute);
                //计算基础数值
                double base = instance.getBaseValue();
                for(AttributeModifier modifier : getOriginModifiers(AttributeModifier.Operation.ADD_VALUE, instance))
                    base += modifier.amount();
                for (ConditionModifier conditionModifier : modifierMap.getModifiersOrEmpty(AttributeModifier.Operation.ADD_VALUE))
                    if(conditionModifier.testCondition(entity)) base += conditionModifier.amount();

                //计算基础乘区
                double result = base;
                for(AttributeModifier modifier : getOriginModifiers(AttributeModifier.Operation.ADD_MULTIPLIED_BASE, instance))
                    result += base * modifier.amount();
                for (ConditionModifier conditionModifier : modifierMap.getModifiersOrEmpty(AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    if(conditionModifier.testCondition(entity)) result += base * conditionModifier.amount();

                //计算独立乘区
                for(AttributeModifier modifier : getOriginModifiers(AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, instance))
                    result *= (double)1.0F + modifier.amount();
                for(ConditionModifier conditionModifier : modifierMap.getModifiersOrEmpty(AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                    if(conditionModifier.testCondition(entity)) result *= (double)1.0F + conditionModifier.amount();

                return attribute.value().sanitizeValue(result);
            }
        }
        return instance.getValue();
    }

    public float getSpecificValue() {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) throw new RuntimeException("The attribute does not exist.");
        Map<Holder<Attribute>, ConditionModifierMap> modifierMapMap = entity.getData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER);

        //计算基础乘区
        float num = 1.0f;
        for(AttributeModifier modifier : AttributeHelper.getOriginModifiers(AttributeModifier.Operation.ADD_MULTIPLIED_BASE, instance))
            num *= 1.0f - (float) modifier.amount();
        if(modifierMapMap.containsKey(attribute)) {
            for (ConditionModifier conditionModifier : modifierMapMap.get(attribute).getModifiersOrEmpty(AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                if(conditionModifier.testCondition(entity)) num *= 1.0f - (float) conditionModifier.amount();
        }

        //计算独立乘区
        float result = 1.0f - num;
        for(AttributeModifier modifier : AttributeHelper.getOriginModifiers(AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, instance))
            result *= 1.0f + (float) modifier.amount();
        if(modifierMapMap.containsKey(attribute)) {
            for (ConditionModifier conditionModifier : modifierMapMap.get(attribute).getModifiersOrEmpty(AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                if(conditionModifier.testCondition(entity)) result *= 1.0f + (float) conditionModifier.amount();
        }
        return (float) attribute.value().sanitizeValue(result);
    }

    public static Collection<AttributeModifier> getOriginModifiers(AttributeModifier.Operation operation, AttributeInstance instance) {
        return ((IMixinAttributeInstance) instance).adcore$getModifiersOrEmpty(operation);
    }

    public void modifyAttributeTransient(AttributeModifier modifier) {
        modifyAttribute(modifier, false);
    }

    public void modifyAttributePermanent(AttributeModifier modifier) {
        modifyAttribute(modifier, true);
    }

    public void modifyAttribute(AttributeModifier modifier, boolean permanent) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) return;
        if(permanent) instance.addOrReplacePermanentModifier(modifier);
        else instance.addOrUpdateTransientModifier(modifier);
    }

    public void modifyAttributeCondition(ConditionModifier modifier) {
        Map<Holder<Attribute>, ConditionModifierMap> modifierMapMap = new HashMap<>(entity.getData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER));
        ConditionModifierMap modifierMap = modifierMapMap.getOrDefault(attribute, new ConditionModifierMap());
        AttributeModifier.Operation operation = modifier.operation();
        Map<Identifier, ConditionModifier> map = new HashMap<>(modifierMap.getOrDefault(operation, new HashMap<>()));
        map.put(modifier.id(), modifier);
        modifierMap.put(operation, map);
        modifierMapMap.put(attribute, modifierMap);
        entity.setData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER, modifierMapMap);
    }

    public void removeModifier(Identifier id) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) return;
        instance.removeModifier(id);
    }

    public void removeConditionModifier(Identifier id) {
        Map<Holder<Attribute>, ConditionModifierMap> modifierMapMap = new HashMap<>(entity.getData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER));
        if(modifierMapMap.containsKey(attribute)) {
            ConditionModifierMap modifierMap = modifierMapMap.get(attribute);
            for (AttributeModifier.Operation value : AttributeModifier.Operation.values()) {
                if(modifierMap.containsKey(value)) {
                    Map<Identifier, ConditionModifier> map = new HashMap<>(modifierMap.get(value));
                    map.remove(id);
                    modifierMap.put(value, map);
                }
            }
        }
        entity.setData(ADCAttachments.CONDITION_ATTRIBUTE_MODIFIER, modifierMapMap);
    }

    public void setBaseValue(double value) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) return;
        instance.setBaseValue(value);
    }

    public boolean setBaseBool(Identifier identifier, boolean bool) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if(instance == null) return false;
        instance.removeModifiers();
        int value = (int) instance.getValue();
        if(value == 1) {
            if(bool) return true;
            AttributeModifier modifier = new AttributeModifier(identifier, -1, AttributeModifier.Operation.ADD_VALUE);
            instance.addPermanentModifier(modifier);
        } else if(value == 0) {
            if(!bool) return true;
            AttributeModifier modifier = new AttributeModifier(identifier, 1, AttributeModifier.Operation.ADD_VALUE);
            instance.addPermanentModifier(modifier);
        } else instance.setBaseValue(bool ? 1.0f : 0.0f);
        return true;
    }

    public float getBaseValue(float defaultValue) {
        AttributeInstance instance = entity.getAttribute(attribute);
        return instance == null ? defaultValue : (float) instance.getBaseValue();
    }
}
