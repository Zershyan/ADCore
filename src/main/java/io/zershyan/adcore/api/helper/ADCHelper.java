package io.zershyan.adcore.api.helper;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.registry.ADCAttributes;
import io.zershyan.adcore.config.StartupConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.List;

/**
 * @see ADCoreAPI#helper
 */
public class ADCHelper {
    private static final Identifier onlyADCoreFeature = Identifier.fromNamespaceAndPath(ADCore.MODID, "only_adcore");
    private static final Identifier adcoreStatus = Identifier.fromNamespaceAndPath(ADCore.MODID, "adcore_status");

    protected final LivingEntity entity;
    private AmplifyHelper amplifyHelper;
    private AttackEffectHelper attackEffectHelper;
    private AttackHelper attackHelper;
    private CriticalHelper criticalHelper;

    public ADCHelper(LivingEntity entity) {
        this.entity = entity;
    }

    public AmplifyHelper amplify() {
        return amplifyHelper == null
                ? amplifyHelper = new AmplifyHelper(entity)
                : amplifyHelper;
    }

    public AttackHelper attack() {
        return attackHelper == null
                ? attackHelper = new AttackHelper(entity)
                : attackHelper;
    }

    public AttributeHelper attribute(Holder<Attribute> attribute) {
        return new AttributeHelper(entity, attribute);
    }

    public CriticalHelper critical() {
        return criticalHelper == null
                ? criticalHelper = new CriticalHelper(entity)
                : criticalHelper;
    }

    public AttackEffectHelper attackEffect() {
        return attackEffectHelper == null
                ? attackEffectHelper = new AttackEffectHelper(entity)
                : attackEffectHelper;
    }

    public LivingEntity entity() {
        return entity;
    }

    /**
     * @return True if entity has attribute
     */
    public boolean hasADCoreAttribute() {
        Identifier key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        List<Identifier> identifiers = StartupConfig.attributeAttach.get().stream().map(Identifier::parse).toList();
        return identifiers.contains(key);
    }

    public boolean isOnlyADCoreFeature() {
        return attribute(ADCAttributes.ONLY_ADCORE_FEATURE).getValue(false);
    }

    public boolean getAdcoreStatus() {
        return attribute(ADCAttributes.ADCORE_STATUS).getValue(false);
    }

    public boolean setOnlyADCoreFeature(boolean bool) {
        return attribute(ADCAttributes.ONLY_ADCORE_FEATURE).setBaseBool(onlyADCoreFeature, bool);
    }

    public boolean setAdcoreStatus(boolean bool) {
        return attribute(ADCAttributes.ADCORE_STATUS).setBaseBool(adcoreStatus, bool);
    }

    public float getAtkSpeed() {
        return attribute(ADCAttributes.ATK_SPEED).getValue(1.0f);
    }

    public float getMeleeAtk() {
        return attribute(ADCAttributes.MELEE_ATK).getValue(0.0f);
    }

    public float getRangedAtk() {
        return attribute(ADCAttributes.RANGED_ATK).getValue(0.0f);
    }

    public float getCriticalRate() {
        return attribute(ADCAttributes.CRITICAL_RATE).getValue(0.0f);
    }

    public float getCriticalDamage() {
        return attribute(ADCAttributes.CRITICAL_DAMAGE).getValue(1.5f);
    }

    public float getMeleeAmplify() {
        return attribute(ADCAttributes.MELEE_AMPLIFY).getValue(0.0f);
    }

    public float getRangedAmplify() {
        return attribute(ADCAttributes.RANGED_AMPLIFY).getValue(0.0f);
    }

    public float getHitRate() {
        return attribute(ADCAttributes.HIT_RATE).getValue(1.0f);
    }

    public float getEvasionRate() {
        return attribute(ADCAttributes.EVASION_RATE).getValue(0.0f);
    }

    public float getMeleePenetrationRate() {
        return attribute(ADCAttributes.MELEE_PENETRATION_RATE).getSpecificValue();
    }

    public float getRangedPenetrationRate() {
        return attribute(ADCAttributes.RANGED_PENETRATION_RATE).getSpecificValue();
    }

    public float getMeleePenetration() {
        return attribute(ADCAttributes.MELEE_PENETRATION).getValue(0.0f);
    }

    public float getRangedPenetration() {
        return attribute(ADCAttributes.RANGED_PENETRATION).getValue(0.0f);
    }

    public float getAttackLifeSteal() {
        return attribute(ADCAttributes.ATTACK_LIFE_STEAL).getValue(0.0f);
    }

    public float getAlmightyLifeSteal() {
        return attribute(ADCAttributes.ALMIGHTY_LIFE_STEAL).getValue(0.0f);
    }

    public float getHealAmplify() {
        return attribute(ADCAttributes.HEAL_AMPLIFY).getValue(0.0f);
    }

    public float getDamageResistance() {
        return attribute(ADCAttributes.DAMAGE_RESISTANCE).getValue(0.0f);
    }
}
