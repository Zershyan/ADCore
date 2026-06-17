package io.zershyan.adcore.config;

import io.zershyan.adcore.datagen.init.ADCConfigLang;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue showAttributeOverlay;
    public static final ModConfigSpec.DoubleValue showAttributeScale;
    public static final ModConfigSpec.IntValue showAttributeXOffset;
    public static final ModConfigSpec.IntValue showAttributeYOffset;
    public static final ModConfigSpec.BooleanValue showAtkSpeed;
    public static final ModConfigSpec.BooleanValue showMeleeAtk;
    public static final ModConfigSpec.BooleanValue showRangedAtk;
    public static final ModConfigSpec.BooleanValue showCriticalRate;
    public static final ModConfigSpec.BooleanValue showCriticalDamage;
    public static final ModConfigSpec.BooleanValue showMeleeAmplify;
    public static final ModConfigSpec.BooleanValue showRangedAmplify;
    public static final ModConfigSpec.BooleanValue showHitRate;
    public static final ModConfigSpec.BooleanValue showEvasionRate;
    public static final ModConfigSpec.BooleanValue showMeleePenetration;
    public static final ModConfigSpec.BooleanValue showRangedPenetration;
    public static final ModConfigSpec.BooleanValue showAttackLifeSteal;
    public static final ModConfigSpec.BooleanValue showAlmightyLifeSteal;
    public static final ModConfigSpec.BooleanValue showHealAmplify;
    public static final ModConfigSpec.BooleanValue showDamageResistance;

    static {
        BUILDER.push(ADCConfigLang.attributeOverlay.getName()).translation(ADCConfigLang.attributeOverlay.getKey());
        showAttributeOverlay = BUILDER.translation(ADCConfigLang.showAttributeOverlay.getKey()).define(
                ADCConfigLang.showAttributeOverlay.getName(), true);
        showAttributeScale = BUILDER.translation(ADCConfigLang.showAttributeScale.getKey()).defineInRange(
                ADCConfigLang.showAttributeScale.getName(), 1.0, 0, 10);
        showAttributeXOffset = BUILDER.translation(ADCConfigLang.showAttributeXOffset.getKey()).defineInRange(
                ADCConfigLang.showAttributeXOffset.getName(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        showAttributeYOffset = BUILDER.translation(ADCConfigLang.showAttributeYOffset.getKey()).defineInRange(
                ADCConfigLang.showAttributeYOffset.getName(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        showAtkSpeed = BUILDER.translation(ADCConfigLang.showAtkSpeed.getKey()).define(
                ADCConfigLang.showAtkSpeed.getName(), true);
        showMeleeAtk = BUILDER.translation(ADCConfigLang.showMeleeAtk.getKey()).define(
                ADCConfigLang.showMeleeAtk.getName(), true);
        showRangedAtk = BUILDER.translation(ADCConfigLang.showRangedAtk.getKey()).define(
                ADCConfigLang.showRangedAtk.getName(), true);
        showCriticalRate = BUILDER.translation(ADCConfigLang.showCriticalRate.getKey()).define(
                ADCConfigLang.showCriticalRate.getName(), true);
        showCriticalDamage = BUILDER.translation(ADCConfigLang.showCriticalDamage.getKey()).define(
                ADCConfigLang.showCriticalDamage.getName(), false);
        showMeleeAmplify = BUILDER.translation(ADCConfigLang.showMeleeAmplify.getKey()).define(
                ADCConfigLang.showMeleeAmplify.getName(), false);
        showRangedAmplify = BUILDER.translation(ADCConfigLang.showRangedAmplify.getKey()).define(
                ADCConfigLang.showRangedAmplify.getName(), false);
        showHitRate = BUILDER.translation(ADCConfigLang.showHitRate.getKey()).define(
                ADCConfigLang.showHitRate.getName(), false);
        showEvasionRate = BUILDER.translation(ADCConfigLang.showEvasionRate.getKey()).define(
                ADCConfigLang.showEvasionRate.getName(), false);
        showMeleePenetration = BUILDER.translation(ADCConfigLang.showMeleePenetration.getKey()).define(
                ADCConfigLang.showMeleePenetration.getName(), false);
        showRangedPenetration = BUILDER.translation(ADCConfigLang.showRangedPenetration.getKey()).define(
                ADCConfigLang.showRangedPenetration.getName(), false);
        showAttackLifeSteal = BUILDER.translation(ADCConfigLang.showAttackLifeSteal.getKey()).define(
                ADCConfigLang.showAttackLifeSteal.getName(), false);
        showAlmightyLifeSteal = BUILDER.translation(ADCConfigLang.showAlmightyLifeSteal.getKey()).define(
                ADCConfigLang.showAlmightyLifeSteal.getName(), false);
        showHealAmplify = BUILDER.translation(ADCConfigLang.showHealAmplify.getKey()).define(
                ADCConfigLang.showHealAmplify.getName(), false);
        showDamageResistance = BUILDER.translation(ADCConfigLang.showDamageResistance.getKey()).define(
                ADCConfigLang.showDamageResistance.getName(), false);
        BUILDER.pop();
        
        SPEC = BUILDER.build();
    }
}
