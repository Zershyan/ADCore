package io.zershyan.dreamworld.datagen.init;

import io.zershyan.dreamworld.Dreamworld;

public enum TranslatableLang {
    CREATIVE_TAB_NAME(new ModLang.LangEntity<>(
            "item_group." + Dreamworld.MODID + "." + Dreamworld.MODID,
            Dreamworld.class.getSimpleName(),
            Dreamworld.class.getSimpleName()
    )),
    RESOURCES(new ModLang.LangEntity<>(
            Dreamworld.MODID + ".resources",
            "Resources for " + Dreamworld.class.getSimpleName(),
            "Resources for " + Dreamworld.class.getSimpleName()
    )),
    ;

    public final ModLang.LangEntity<String> langEntity;

    TranslatableLang(ModLang.LangEntity<String> lang) {
        this.langEntity = lang;
    }

    public String getKey() {
        return langEntity.key();
    }
}
