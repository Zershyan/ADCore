package io.zershyan.adcore.datagen.init;

import io.zershyan.adcore.ADCore;

import java.util.List;

public enum ADCoreConfigLang {
    //server

    //client


    //common
    atkDamageConvert(
            "atkDamageConvert",
            "攻击力->普攻伤害 转化系数",
            "Attack -> Damage Conversion Coefficient"
    ),
    adcoreItemDamage(
            "adcoreItemDamage",
            "仅使用ADCore伤害时损耗物品耐久",
            "Item break when Only-ADCore-Damage on"
    ),

    //startup
    attributeAttach(
            "attributeAttach",
            "拥有ADCore属性的生物id",
            "The List of Mob ID with ADCore Attributes"
    ),
    baseAtkSpeed(
            "baseAtkSpeed",
            "攻击速度",
            "Atk Speed"
    ),
    baseMeleeAtk(
            "baseMeleeAtk",
            "近战攻击力",
            "Melee Atk"
    ),
    baseRangedAtk(
            "baseRangedAtk",
            "远程攻击力",
            "Ranged Atk"
    ),
    baseCriticalRate(
            "baseCriticalRate",
            "暴击率",
            "Critical Rate"
    ),
    baseCriticalDamage(
            "baseCriticalDamage",
            "暴击伤害",
            "Critical Damage"
    ),
    baseMeleeAmplify(
            "baseMeleeAmplify",
            "近战增伤",
            "Melee Amplify"
    ),
    baseRangedAmplify(
            "baseRangedAmplify",
            "远程增伤",
            "Ranged Amplify"
    ),
    baseHitRate(
            "baseHitRate",
            "命中率",
            "Hit Rate"
    ),
    baseEvasionRate(
            "baseEvasionRate",
            "闪避率",
            "Evasion Rate"
    ),
    baseMeleePenetration(
            "baseMeleePenetration",
            "近战护甲穿透",
            "Melee Armor Penetration"
    ),
    baseMeleePenetrationRate(
            "baseMeleePenetrationRate",
            "近战护甲穿透率",
            "Melee Armor Penetration Rate"
    ),
    baseRangedPenetration(
            "baseRangedPenetration",
            "远程护甲穿透",
            "Ranged Armor Penetration"
    ),
    baseRangedPenetrationRate(
            "baseRangedPenetrationRate",
            "远程护甲穿透率",
            "Ranged Armor Penetration Rate"
    ),
    baseAttackLifeSteal(
            "baseAttackLifeSteal",
            "生命偷取",
            "Attack Life Steal"
    ),
    baseAlmightyLifeSteal(
            "baseAlmightyLifeSteal",
            "全能吸血",
            "Almighty Life Steal"
    ),
    baseHealAmplify(
            "baseHealAmplify",
            "治疗效果提升",
            "Healing Effect Amplify"
    ),

    //type
    attributeDefault(
            "attributeDefault",
            "默认属性值",
            "Default Attribute"
    ),

    ;

    private final String name;
    private final ADCoreLang.Lang lang;

    ADCoreConfigLang(String name, String zhCn, String enUs) {
        this.name = name;
        this.lang = new ADCoreLang.Lang(zhCn, enUs);
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return ADCore.MODID + ".configuration." + name;
    }

    public static void initLang(List<ADCoreLang.LangEntity<?>> langList) {
        for (ADCoreConfigLang value : ADCoreConfigLang.values()) {
            langList.add(new ADCoreLang.LangEntity<>(value.getKey(), value.lang));
        }
    }
}
