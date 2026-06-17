package io.zershyan.adcore.config;

import io.zershyan.adcore.datagen.init.ADCConfigLang;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.DoubleValue atkDamageConvert;
    public static final ModConfigSpec.BooleanValue adcoreItemDamage;

    static {
        atkDamageConvert = BUILDER.translation(ADCConfigLang.atkDamageConvert.getKey())
                .defineInRange(ADCConfigLang.atkDamageConvert.getName(), 0.1, 0.0, Integer.MAX_VALUE);
        adcoreItemDamage = BUILDER.translation(ADCConfigLang.adcoreItemDamage.getKey())
                .define(ADCConfigLang.adcoreItemDamage.getName(), false);
        SPEC = BUILDER.build();
    }
}
