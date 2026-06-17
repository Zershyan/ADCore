package io.zershyan.adcore.datagen.init;

import io.zershyan.adcore.ADCore;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public enum ADCConfigLang {
    //server
    maxAttackEffectRepeatCount(
            "maxCountAttackEffectSelfCycle",
            "攻击特效触发其他攻击特效的次数上限",
            "Max Number of 'AttackEffect trigger another AttackEffect'"
    ),

    //client
    showAttributeXOffset(
            "showAttributeXOffset",
            "显示属性X偏移",
            "Show Attribute X Offset"
    ),
    showAttributeYOffset(
            "showAttributeYOffset",
            "显示属性Y偏移",
            "Show Attribute Y Offset"
    ),
    showAttributeScale(
            "showAttributeScale",
            "显示属性缩放",
            "Show Attribute Scale"
    ),
    showAttributeOverlay(
            "showAttributeOverlay",
            "显示具体属性数值",
            "Show attribute detail values"
    ),
    showAtkSpeed(
            "showAtkSpeed",
            "显示攻击速度",
            "Show Atk Speed"
    ),
    showMeleeAtk(
            "showMeleeAtk",
            "显示近战攻击力",
            "Show Melee Atk"
    ),
    showRangedAtk(
            "showRangedAtk",
            "显示远程攻击力",
            "Show Ranged Atk"
    ),
    showCriticalRate(
            "showCriticalRate",
            "显示暴击率",
            "Show Critical Rate"
    ),
    showCriticalDamage(
            "showCriticalDamage",
            "显示暴击伤害",
            "Show Critical Damage"
    ),
    showMeleeAmplify(
            "showMeleeAmplify",
            "显示近战增伤",
            "Show Melee Amplify"
    ),
    showRangedAmplify(
            "showRangedAmplify",
            "显示远程增伤",
            "Show Ranged Amplify"
    ),
    showHitRate(
            "showHitRate",
            "显示命中率",
            "Show Hit Rate"
    ),
    showEvasionRate(
            "showEvasionRate",
            "显示闪避率",
            "Show Evasion Rate"
    ),
    showMeleePenetration(
            "showMeleePenetration",
            "显示近战护甲穿透",
            "Show Melee Armor Penetration"
    ),
    showRangedPenetration(
            "showRangedPenetration",
            "显示远程护甲穿透",
            "Show Ranged Armor Penetration"
    ),
    showAttackLifeSteal(
            "showAttackLifeSteal",
            "显示生命偷取",
            "Show Attack Life Steal"
    ),
    showAlmightyLifeSteal(
            "showAlmightyLifeSteal",
            "显示全能吸血",
            "Show Almighty Life Steal"
    ),
    showHealAmplify(
            "showHealAmplify",
            "显示治疗效果提升",
            "Show Healing Effect Amplify"
    ),
    showDamageResistance(
            "showDamageResistance",
            "显示减伤",
            "Show Damage Resistance"
    ),

    //common
    atkDamageConvert(
            "atkDamageConvert",
            "攻击力->普攻伤害 转化系数",
            "Attack -> Damage Conversion Coefficient"
    ),
    adcoreItemDamage(
            "adcoreItemDamage",
            "仅使用ADCore伤害时损耗物品耐久",
            "Item Break When Only-ADCore-Damage On"
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
    baseDamageResistance(
            "baseDamageResistance",
            "减伤",
            "Damage Resistance"
    ),
    enableExample(
            "enableExample",
            "开启ADCore的代码示例",
            "Enable ADCore Code Example"
    ),

    //type
    attributeDefault(
            "attributeDefault",
            "默认属性值",
            "Default Attribute"
    ),
    developmentConfig(
            "developmentConfig",
            "开发环境配置",
            "Development Config"
    ),
    attributeOverlay(
            "attributeOverlay",
            "属性显示配置",
            "Attribute Display Config"
    )

    ;

    private final String name;
    private final ADCoreLang.Lang lang;

    ADCConfigLang(String name, String zhCn, String enUs) {
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
        for (ADCConfigLang value : ADCConfigLang.values()) {
            langList.add(new ADCoreLang.LangEntity<>(value.getKey(), value.lang));
        }
    }
}
