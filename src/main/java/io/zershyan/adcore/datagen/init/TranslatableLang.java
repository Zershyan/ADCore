package io.zershyan.adcore.datagen.init;

import io.zershyan.adcore.ADCore;

public enum TranslatableLang {
    CREATIVE_TAB_NAME(new ADCoreLang.LangEntity<>(
            "item_group." + ADCore.MODID + "." + ADCore.MODID,
            ADCore.class.getSimpleName(),
            ADCore.class.getSimpleName()
    )),
    RESOURCES(new ADCoreLang.LangEntity<>(
            ADCore.MODID + ".resources",
            "Resources for " + ADCore.class.getSimpleName(),
            "Resources for " + ADCore.class.getSimpleName()
    )),
    COMMAND_SWITCH_SUCCESS(new ADCoreLang.LangEntity<>(
            ADCore.MODID + ADCoreLang.command + ".command_switch_success",
            "你的§6ADCore功能§r已被修改为%s状态。",
            "Your §6ADCore Status§r has been modified to %s state."
    )),
    COMMAND_ONLY_ADCORE_DAMAGE_SWITCH_SUCCESS(new ADCoreLang.LangEntity<>(
            ADCore.MODID + ADCoreLang.command + ".command_only_adcore_damage_switch_success",
            "你的§6仅使用ADCore伤害功能§r已被修改为%s状态。",
            "Your §6Only-ADCore-Damage§r has been turned %s."
    )),
    COMMAND_SUCCESS_TO(new ADCoreLang.LangEntity<>(
            ADCore.MODID + ADCoreLang.command + ".command_success_to",
            "已将%s个目标的对应功能修改为%s状态。",
            "The corresponding feature for %s targets have been turned %s."
    )),
    COMMAND_STATUS_ON(new ADCoreLang.LangEntity<>(
            ADCore.MODID + ADCoreLang.command + ".command_status_on",
            "§a开启",
            "§aon"
    )),
    COMMAND_STATUS_OFF(new ADCoreLang.LangEntity<>(
            ADCore.MODID + ADCoreLang.command + ".command_status_off",
            "§c关闭",
            "§coff"
    )),
    ;

    public final ADCoreLang.LangEntity<String> langEntity;

    TranslatableLang(ADCoreLang.LangEntity<String> lang) {
        this.langEntity = lang;
    }

    public String getKey() {
        return langEntity.key();
    }
}
