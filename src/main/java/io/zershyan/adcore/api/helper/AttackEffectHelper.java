package io.zershyan.adcore.api.helper;

import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.event.ADCAttackEffectEvent;
import io.zershyan.adcore.common.registry.ADCAttachments;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffect;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectData;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectInstance;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This helper uses to opera data of attack-effect on living entity.
 * @see ADCoreAPI#attackEffectHelper
 */
public class AttackEffectHelper extends AttackHelper {
    public AttackEffectHelper(LivingEntity attacker) {
        super(attacker);
    }

    public boolean hasAttackEffect() {
        return !attacker.getData(ADCAttachments.ATTACK_EFFECTS).isEmpty();
    }

    private Map<Identifier, AttackEffectData> getCacheMap() {
        if(!AttackEffectRegistry.caches.containsKey(attacker)) {
            Map<Identifier, AttackEffectData> data = attacker.getData(ADCAttachments.ATTACK_EFFECTS);
            HashMap<Identifier, AttackEffectData> dataHashMap = new HashMap<>(data);
            AttackEffectRegistry.caches.put(attacker, dataHashMap);
            return dataHashMap;
        } else return AttackEffectRegistry.caches.get(attacker);
    }

    public void dropCache() {
        AttackEffectRegistry.caches.remove(attacker);
    }

    /**
     * @return A map all attack-effect data of this entity.
     */
    public Map<Identifier, AttackEffectData> getDataMap() {
        return Map.copyOf(getCacheMap());
    }

    private void setDataMap(Map<Identifier, AttackEffectData> dataMap) {
        attacker.setData(ADCAttachments.ATTACK_EFFECTS, dataMap);
    }

    private void updateDataMap(Consumer<Map<Identifier, AttackEffectData>> consumer) {
        Map<Identifier, AttackEffectData> dataMap = getCacheMap();
        consumer.accept(dataMap);
        setDataMap(dataMap);
    }

    public boolean hasAttackEffect(Identifier identifier) {
        return getCacheMap().containsKey(identifier);
    }

    public boolean hasAttackEffect(AttackEffect attackEffect) {
        return getCacheMap().values().stream().anyMatch(data -> data.getEffect().equals(attackEffect));
    }

    /**
     * Put attack effect to entity persistent data.
     * @param id id
     * @param effect attack effect
     */
    public void putAttackEffect(Identifier id, AttackEffect effect) {
        AttackEffectData effectData = AttackEffectData.of(effect);
        updateDataMap(map -> map.put(id, effectData));
    }

    /**
     * Remove attack effect on entity with id.
     * @param id id
     */
    public void removeAttackEffect(Identifier id) {
        updateDataMap(map -> map.remove(id));
    }

    /**
     * Remove attack effect on entity with the type of effect.
     * @param effect effect
     */
    public void removeAttackEffect(AttackEffect effect) {
        getDataMap().forEach((identifier, effectData) -> {
            if(effect.equals(effectData.getEffect())) removeAttackEffect(identifier);
        });
    }

    /**
     * Remove attack effect on entity with raw data of effect
     * @param effectData raw effect data
     */
    public void removeAttackEffect(AttackEffectData effectData) {
        removeAttackEffect(effectData.getEffect());
    }

    /**
     * Trigger all effect on entity.<br>
     * Don't use it in subclass of AttackEffect!!!<br>
     * It will also trigger all attack effects that can and need to be repeated.
     * @param target target
     * @param source damage source
     * @param damage original damage value
     */
    public void triggerAllEffects(Entity target, DamageSource source, final float damage) {
        if(target.asLivingEntity() instanceof LivingEntity targetEntity) {
            DamageSource damageSource = createNormalSource();
            if (damageSource == null) return;
            List<AttackEffectInstance> list = getDataMap().values().stream().map(data -> data.asInstance(attacker)).toList();
            for (AttackEffectInstance instance : list) {
                ADCAttackEffectEvent.Pre event = new ADCAttackEffectEvent.Pre(targetEntity, instance, damageSource, source, damage);
                if (NeoForge.EVENT_BUS.post(event).isCanceled()) continue;
                instance.trigger(targetEntity, source, damage, 1.0f);
                AttackEffect.RepeatInfo repeatInfo = instance.getRepeatInfo(targetEntity);
                if(repeatInfo.triggerOtherEffect()) {
                    for (AttackEffectInstance newInstance : list) {
                        if(repeatInfo.count() <= 0 || repeatInfo.applyScale() == 0.0f) continue;
                        for (int i = 0; i < repeatInfo.count(); i++) {
                            if(!newInstance.getEffect().getProperties().isEnableRepeatInfo() || newInstance.equals(instance)) {
                                newInstance.trigger(targetEntity, source, damage, repeatInfo.applyScale());
                            } else newInstance.triggerDontCount(targetEntity, source, damage, repeatInfo.applyScale());
                        }
                    }
                } else {
                    if(repeatInfo.count() <= 0 || repeatInfo.applyScale() == 0.0f) continue;
                    for (int i = 0; i < repeatInfo.count(); i++) {
                        instance.trigger(targetEntity, source, damage, repeatInfo.applyScale());
                    }
                }
                NeoForge.EVENT_BUS.post(new ADCAttackEffectEvent.Post(targetEntity, instance, repeatInfo, damageSource, source, damage));
            }
        }
    }

    /**
     * Get attack effect through its registry id.
     * @param id id
     * @return Attack effect
     */
    @Nullable
    public AttackEffect getAttackEffect(Identifier id) {
        return AttackEffectRegistry.REGISTRY.getValue(id);
    }
}
