package io.zershyan.adcore.common;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.api.helper.AttributeHelper;
import io.zershyan.adcore.common.event.*;
import io.zershyan.adcore.common.registry.ADCDamageTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

@EventBusSubscriber(modid = ADCore.MODID)
public class ADCoreCoreHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void cancelOriginAttack(EntityInvulnerabilityCheckEvent event) {
        DamageSource damageSource = event.getSource();
        if(ADCDamageTypes.isADCoreDamage(damageSource)) return;
        Entity entity = damageSource.getEntity();
        LivingEntity target = event.getEntity().asLivingEntity();
        if(target == null) return;
        if(entity instanceof ServerPlayer source) {
            if(!ADCoreAPI.getModStatus(source)) return;
            if(!event.isInvulnerable()) {
                ADCExclusiveHurtEvent exclusiveHurtEvent = NeoForge.EVENT_BUS.post(new ADCExclusiveHurtEvent(
                        source, damageSource, ADCoreAPI.isOnlyCauseADCoreDamage(source)));
                event.setInvulnerable(exclusiveHurtEvent.isOnlyADCoreCausingDamage());
            }
            ADCAttackEvent.Pre preEvent = NeoForge.EVENT_BUS.post(new ADCAttackEvent.Pre(source));
            if(!preEvent.isCanceled()) {
                ADCoreAPI.attackHelper(source).tryAttack(target);
                if(damageSource.getDirectEntity() instanceof Projectile projectile) {
                    projectile.discard();
                }
            }
        }
    }

    @SubscribeEvent
    public static void damageResistance(LivingDamageEvent.Pre event) {
        float newDamage = event.getNewDamage();
        if(newDamage == 0) return;
        DamageSource damageSource = event.getSource();
        if(event.getEntity().asLivingEntity() instanceof LivingEntity target) {
            if(damageSource.is(DamageTypeTags.BYPASSES_RESISTANCE)) return;
            if(!ADCoreAPI.getModStatus(target)) return;
            AttributeHelper attributeHelper = ADCoreAPI.attributeHelper(target);
            float damageResistance = attributeHelper.getDamageResistance();
            if(damageResistance == 0) return;
            float fix = Mth.clamp(1.0f - damageResistance, 0.0f, 1.0f);
            event.setNewDamage(newDamage * fix);
        }
    }

    @SubscribeEvent
    public static void lifeSteal(LivingDamageEvent.Post event) {
        float newDamage = event.getNewDamage();
        if(newDamage == 0) return;
        DamageSource damageSource = event.getSource();
        LivingEntity target = event.getEntity().asLivingEntity();
        if(target == null) return;
        if(damageSource.getEntity() instanceof LivingEntity source) {
            if(!ADCoreAPI.getModStatus(source)) return;
            AttributeHelper attributeHelper = ADCoreAPI.attributeHelper(source);
            float amtLifeStealRate = attributeHelper.getAlmightyLifeSteal();
            float atkLifeStealRate = 0.0f;
            if(damageSource.is(ADCDamageTypes.ATTACK_DAMAGE)) {
                atkLifeStealRate = attributeHelper.getAttackLifeSteal();
            }
            ADCLifeStealEvent lifeStealEvent = NeoForge.EVENT_BUS.post(new ADCLifeStealEvent(
                    source, damageSource, newDamage, atkLifeStealRate, amtLifeStealRate));
            if(lifeStealEvent.isCancelled()) return;
            float value = lifeStealEvent.getTotalLifeSteal() * newDamage;
            if(value > 0) source.setHealth(source.getHealth() + value);
        }
    }

    @SubscribeEvent
    public static void healModify(LivingHealEvent event) {
        float amount = event.getAmount();
        if(amount == 0) return;
        LivingEntity entity = event.getEntity();
        if(!ADCoreAPI.getModStatus(entity)) return;
        float healAmplify = ADCoreAPI.attributeHelper(entity).getHealAmplify();
        ADCHealAmplifyEvent amplifyEvent = NeoForge.EVENT_BUS.post(new ADCHealAmplifyEvent(entity, healAmplify));
        if(amplifyEvent.isCancelled()) return;
        event.setAmount(amount + amount * amplifyEvent.getNewRate());
    }

    @SubscribeEvent
    public static void attachEntityAttackEffect(EntityJoinLevelEvent event) {
        if(event.getLevel().isClientSide()) return;
        if(event.getEntity() instanceof LivingEntity living) {
            NeoForge.EVENT_BUS.post(new ADCAttackEffectAttachEvent(living, event));
        }
    }
}
