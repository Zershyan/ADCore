package io.zershyan.adcore.config;

import io.zershyan.adcore.datagen.init.ADCConfigLang;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue maxCountAttackEffectSelfCycle;

    static {
        
        SPEC = BUILDER.build();
    }

}
