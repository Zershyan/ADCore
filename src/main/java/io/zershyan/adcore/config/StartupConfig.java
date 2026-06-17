package io.zershyan.adcore.config;

import io.zershyan.adcore.datagen.init.ADCConfigLang;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class StartupConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> attributeAttach;
    public static final ModConfigSpec.DoubleValue baseAtkSpeed;
    public static final ModConfigSpec.DoubleValue baseMeleeAtk;
    public static final ModConfigSpec.DoubleValue baseRangedAtk;
    public static final ModConfigSpec.DoubleValue baseCriticalRate;
    public static final ModConfigSpec.DoubleValue baseCriticalDamage;
    public static final ModConfigSpec.DoubleValue baseMeleeAmplify;
    public static final ModConfigSpec.DoubleValue baseRangedAmplify;
    public static final ModConfigSpec.DoubleValue baseHitRate;
    public static final ModConfigSpec.DoubleValue baseEvasionRate;
    public static final ModConfigSpec.DoubleValue baseMeleePenetration;
    public static final ModConfigSpec.DoubleValue baseMeleePenetrationRate;
    public static final ModConfigSpec.DoubleValue baseRangedPenetration;
    public static final ModConfigSpec.DoubleValue baseRangedPenetrationRate;
    public static final ModConfigSpec.DoubleValue baseAttackLifeSteal;
    public static final ModConfigSpec.DoubleValue baseAlmightyLifeSteal;
    public static final ModConfigSpec.DoubleValue baseHealAmplify;
    public static final ModConfigSpec.DoubleValue baseDamageResistance;
    public static final ModConfigSpec.BooleanValue enableExample;

    static {
        attributeAttach = BUILDER.translation(ADCConfigLang.attributeAttach.getKey())
                .defineList(
                        ADCConfigLang.attributeAttach.getName(),
                        List.of("minecraft:player"),
                        () -> "minecraft:player",
                        o -> Identifier.read((String) o).isSuccess()
                );
//        BUILDER.push(ModConfigLang.general.getName()).translation(ModConfigLang.general.getKey());
//
//        BUILDER.pop();

        BUILDER.push(ADCConfigLang.attributeDefault.getName()).translation(ADCConfigLang.attributeDefault.getKey());
        baseAtkSpeed = BUILDER.translation(ADCConfigLang.baseAtkSpeed.getKey()).defineInRange(
                ADCConfigLang.baseAtkSpeed.getName(), 1.0, 0.0, 20.0);
        baseMeleeAtk = BUILDER.translation(ADCConfigLang.baseMeleeAtk.getKey()).defineInRange(
                ADCConfigLang.baseMeleeAtk.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseRangedAtk = BUILDER.translation(ADCConfigLang.baseRangedAtk.getKey()).defineInRange(
                ADCConfigLang.baseRangedAtk.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseCriticalRate = BUILDER.translation(ADCConfigLang.baseCriticalRate.getKey()).defineInRange(
                ADCConfigLang.baseCriticalRate.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseCriticalDamage = BUILDER.translation(ADCConfigLang.baseCriticalDamage.getKey()).defineInRange(
                ADCConfigLang.baseCriticalDamage.getName(), 1.5, 1.0, Double.MAX_VALUE);
        baseMeleeAmplify = BUILDER.translation(ADCConfigLang.baseMeleeAmplify.getKey()).defineInRange(
                ADCConfigLang.baseMeleeAmplify.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseRangedAmplify = BUILDER.translation(ADCConfigLang.baseRangedAmplify.getKey()).defineInRange(
                ADCConfigLang.baseRangedAmplify.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseHitRate = BUILDER.translation(ADCConfigLang.baseHitRate.getKey()).defineInRange(
                ADCConfigLang.baseHitRate.getName(), 1.0, 0.0, Double.MAX_VALUE);
        baseEvasionRate = BUILDER.translation(ADCConfigLang.baseEvasionRate.getKey()).defineInRange(
                ADCConfigLang.baseEvasionRate.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseMeleePenetration = BUILDER.translation(ADCConfigLang.baseMeleePenetration.getKey()).defineInRange(
                ADCConfigLang.baseMeleePenetration.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseMeleePenetrationRate = BUILDER.translation(ADCConfigLang.baseMeleePenetrationRate.getKey()).defineInRange(
                ADCConfigLang.baseMeleePenetrationRate.getName(), 0.0, 0.0, 1.0);
        baseRangedPenetration = BUILDER.translation(ADCConfigLang.baseRangedPenetration.getKey()).defineInRange(
                ADCConfigLang.baseRangedPenetration.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseRangedPenetrationRate = BUILDER.translation(ADCConfigLang.baseRangedPenetrationRate.getKey()).defineInRange(
                ADCConfigLang.baseRangedPenetrationRate.getName(), 0.0, 0.0, 1.0);
        baseAttackLifeSteal = BUILDER.translation(ADCConfigLang.baseAttackLifeSteal.getKey()).defineInRange(
                ADCConfigLang.baseAttackLifeSteal.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseAlmightyLifeSteal = BUILDER.translation(ADCConfigLang.baseAlmightyLifeSteal.getKey()).defineInRange(
                ADCConfigLang.baseAlmightyLifeSteal.getName(), 0.0, 0.0, Double.MAX_VALUE);
        baseHealAmplify = BUILDER.translation(ADCConfigLang.baseHealAmplify.getKey()).defineInRange(
                ADCConfigLang.baseHealAmplify.getName(), 0.0, -1.0, Double.MAX_VALUE);
        baseDamageResistance = BUILDER.translation(ADCConfigLang.baseDamageResistance.getKey()).defineInRange(
                ADCConfigLang.baseDamageResistance.getName(), 0.0, 0.0, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push(ADCConfigLang.developmentConfig.getName()).translation(ADCConfigLang.developmentConfig.getKey());
        enableExample = BUILDER.translation(ADCConfigLang.enableExample.getKey())
                .define(ADCConfigLang.enableExample.getName(), true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
