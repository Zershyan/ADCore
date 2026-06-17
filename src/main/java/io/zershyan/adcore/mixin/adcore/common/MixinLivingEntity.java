package io.zershyan.adcore.mixin.adcore.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.api.helper.AttackEffectHelper;
import io.zershyan.adcore.api.helper.AttackHelper;
import io.zershyan.adcore.common.registry.ADCDamageTypes;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectInstance;
import io.zershyan.adcore.util.mixin.IMixinLivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(LivingEntity.class)
public class MixinLivingEntity implements IMixinLivingEntity {

    @Unique
    private long adcore$atkCooldownTime = System.currentTimeMillis();

    @Unique
    @Final
    private final Supplier<Boolean> adcore$hasADCoreAttribute = () -> ADCoreAPI.helper(
            LivingEntity.class.cast(this)).hasADCoreAttribute();

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void tick(CallbackInfo ci) {
        LivingEntity entity = LivingEntity.class.cast(this);
        if(!adcore$getModStatus()) return;
        AttackEffectHelper attackEffectHelper = ADCoreAPI.attackEffectHelper(entity);
        if(!attackEffectHelper.hasAttackEffect()) return;
        attackEffectHelper.getDataMap().values().forEach(data -> {
            AttackEffectInstance instance = data.asInstance(entity);
            if(instance.getDurationTicks() != 0) instance.tick();
        });
    }

    @Inject(
            method = "playHurtSound",
            at = @At("HEAD"),
            cancellable = true
    )
    public void playHurtSound(DamageSource source, CallbackInfo ci) {
        if(!source.is(ADCDamageTypes.ATTACK_DAMAGE) && ADCDamageTypes.isADCoreDamage(source))
            ci.cancel();
    }

    @WrapOperation(
            method = "getDamageAfterArmorAbsorb",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getArmorValue()I")
    )
    private int modifyArmorValue(LivingEntity instance, Operation<Integer> original, @Local(argsOnly = true, name = "damageSource") LocalRef<DamageSource> source) {
        DamageSource damageSource = source.get();
        float armorValue = original.call(instance);
        if(damageSource == null || !ADCDamageTypes.isNeedPenetration(damageSource))
            return (int) armorValue;
        Entity entity = damageSource.getEntity();
        if(entity instanceof ServerPlayer serverPlayer) {
            if(!adcore$getModStatus()) return (int) armorValue;
            AttackHelper helper = ADCoreAPI.attackHelper(serverPlayer);
            armorValue *= (1.0f - helper.getPenetrationRateWithTarget(instance));
            armorValue -= helper.getPenetrationWithTarget(instance);
        }
        return Mth.floor(armorValue);
    }

    @Unique
    @Override
    public boolean adcore$isInCooldownTime() {
        return System.currentTimeMillis() < adcore$atkCooldownTime;
    }

    @Unique
    @Override
    public void adcore$setAtkCooldown(int atkCooldown) {
        this.adcore$atkCooldownTime = System.currentTimeMillis() + atkCooldown;
    }

    @Unique
    private boolean adcore$getModStatus() {
        return adcore$hasADCoreAttribute.get() && ADCoreAPI.helper(LivingEntity.class.cast(this)).getAdcoreStatus();
    }
}
