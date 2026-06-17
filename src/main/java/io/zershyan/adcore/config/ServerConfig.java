package io.zershyan.adcore.config;

import io.zershyan.adcore.datagen.init.ADCConfigLang;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue maxAttackEffectRepeatCount;

    static {
        maxAttackEffectRepeatCount = BUILDER.translation(ADCConfigLang.maxAttackEffectRepeatCount.getKey())
                .defineInRange(ADCConfigLang.maxAttackEffectRepeatCount.getName(), 10, 2, 100);
        SPEC = BUILDER.build();
    }

}
