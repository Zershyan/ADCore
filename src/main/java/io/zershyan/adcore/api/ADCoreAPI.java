package io.zershyan.adcore.api;

import io.zershyan.adcore.api.helper.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

/**
 * You can use ADCore with this API.
 */
public class ADCoreAPI {
    public static final String MODID = "adcore";

    /**
     * Get attack helper
     * @param attacker attacker
     * @return AttackHelper
     */
    public static AttackHelper attackHelper(LivingEntity attacker) {
        return new AttackHelper(attacker);
    }

    /**
     * Get amplify helper
     * @param attacker attacker
     * @return AmplifyHelper
     */
    public static AmplifyHelper amplifyHelper(LivingEntity attacker) {
        return new AmplifyHelper(attacker);
    }

    /**
     * Get critical helper
     * @param attacker attacker
     * @return CriticalHelper
     */
    public static CriticalHelper criticalHelper(LivingEntity attacker) {
        return new CriticalHelper(attacker);
    }

    /**
     * Get attribute helper
     * @param entity entity
     * @return AttributeHelper
     */
    public static AttributeHelper attributeHelper(LivingEntity entity, Holder<Attribute> attribute) {
        return new AttributeHelper(entity, attribute);
    }

    /**
     * Get attack effect helper
     * @param entity entity
     * @return AttackEffectHelper
     */
    public static AttackEffectHelper attackEffectHelper(LivingEntity entity) {
        return new AttackEffectHelper(entity);
    }

    /**
     * Get general helper
     * @param entity entity
     * @return ADCHelper
     */
    public static ADCHelper helper(LivingEntity entity) {
        return new ADCHelper(entity);
    }

    /**
     * A quick function
     * @param entity target
     * @return If only cause adcore damage
     */
    public static boolean isOnlyCauseADCoreDamage(LivingEntity entity) {
        return helper(entity).isOnlyADCoreFeature();
    }

    /**
     * A quick function
     * @param entity target
     * @param bool turn on or off
     * @return If success
     */
    public static boolean setOnlyCauseADCoreDamage(LivingEntity entity, boolean bool) {
        return helper(entity).setOnlyADCoreFeature(bool);
    }

    /**
     * A quick function
     * @param entity target
     * @return If only cause adcore damage
     */
    public static boolean getModStatus(LivingEntity entity) {
        ADCHelper adcHelper = helper(entity);
        return adcHelper.hasADCoreAttribute() && adcHelper.getAdcoreStatus();
    }

    /**
     * A quick function
     * @param entity target
     * @param bool turn on or off
     * @return If success
     */
    public static boolean setModStatus(LivingEntity entity, boolean bool) {
        return helper(entity).setAdcoreStatus(bool);
    }
}
