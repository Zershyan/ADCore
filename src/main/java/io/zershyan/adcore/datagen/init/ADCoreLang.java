package io.zershyan.adcore.datagen.init;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.registry.ADCAttributes;
import io.zershyan.adcore.common.registry.ADCDamageTypes;
import io.zershyan.adcore.common.registry.ADCSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.ArrayList;
import java.util.List;

public class ADCoreLang {
    public record Lang(String zhCn, String enUs) {}
    public record LangEntity<T>(T key, Lang lang) {
        public LangEntity(T key, String zhCn, String enUs) {
            this(key, new Lang(zhCn, enUs));
        }
    }
    public static final List<LangEntity<?>> langList = new ArrayList<>();
    public final static String command = ".command";

    public static void init() {
        langList.clear();

        //attribute
        langList.add(new LangEntity<>(ADCAttributes.ONLY_ADCORE_FEATURE, "覆盖原版逻辑", "Only ADCore Feature"));
        langList.add(new LangEntity<>(ADCAttributes.ATK_SPEED, "攻击速度", "Atk Speed"));
        langList.add(new LangEntity<>(ADCAttributes.MELEE_ATK, "近战攻击力", "Melee Attack"));
        langList.add(new LangEntity<>(ADCAttributes.RANGED_ATK, "远程攻击力", "Ranged Attack"));
        langList.add(new LangEntity<>(ADCAttributes.CRITICAL_RATE, "暴击率", "Critical Rate"));
        langList.add(new LangEntity<>(ADCAttributes.CRITICAL_DAMAGE, "暴击伤害", "Critical Damage"));
        langList.add(new LangEntity<>(ADCAttributes.MELEE_AMPLIFY, "近战增伤", "Melee Amplify"));
        langList.add(new LangEntity<>(ADCAttributes.RANGED_AMPLIFY, "远程增伤", "Ranged Amplify"));
        langList.add(new LangEntity<>(ADCAttributes.HIT_RATE, "命中率", "Hit Rate"));
        langList.add(new LangEntity<>(ADCAttributes.EVASION_RATE, "闪避率", "Evasion Rate"));
        langList.add(new LangEntity<>(ADCAttributes.MELEE_PENETRATION, "近战护甲穿透", "Melee Penetration"));
        langList.add(new LangEntity<>(ADCAttributes.MELEE_PENETRATION_RATE, "近战护甲穿透比", "Melee Penetration Rate"));
        langList.add(new LangEntity<>(ADCAttributes.RANGED_PENETRATION, "远程护甲穿透", "Ranged Penetration"));
        langList.add(new LangEntity<>(ADCAttributes.RANGED_PENETRATION_RATE, "远程护甲穿透比", "Ranged Penetration Rate"));
        langList.add(new LangEntity<>(ADCAttributes.ATTACK_LIFE_STEAL, "生命偷取", "Attack Life Steal"));
        langList.add(new LangEntity<>(ADCAttributes.ALMIGHTY_LIFE_STEAL, "全能吸血", "Almighty Life Steal"));
        langList.add(new LangEntity<>(ADCAttributes.HEAL_AMPLIFY, "治疗效果提升", "Healing Effect Amplify"));

        //sound
        langList.add(new LangEntity<>(ADCSounds.EVASION_SUCCESS, "闪避成功声", "Evasion Success"));
        langList.add(new LangEntity<>(ADCSounds.HIT_FAILURE, "攻击未命中声", "Hit Failure"));

        //damage type
        langList.add(new LangEntity<>(
                getDamageTypeKey(ADCDamageTypes.NORMAL_DAMAGE),
                "%1$s 被 %2$s 击杀。",
                "%1$s is killed by %2$s."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKey(ADCDamageTypes.ATTACK_DAMAGE),
                "%1$s 被 %2$s 使用普通攻击击杀。",
                "%1$s is killed by %2$s using normal attack."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKey(ADCDamageTypes.MAGIC_DAMAGE),
                "%1$s 被 %2$s 使用魔法伤害击杀。",
                "%1$s is killed by %2$s using magic damage."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKey(ADCDamageTypes.TRUE_DAMAGE),
                "%1$s 被 %2$s 使用真实伤害击杀。",
                "%1$s is killed by %2$s using true damage."
        ));

        initTranslatableLang();
        ADCConfigLang.initLang(langList);
    }

    private static void initTranslatableLang() {
        for (TranslatableLang value : TranslatableLang.values()) {
            ADCoreLang.langList.add(value.langEntity);
        }
    }

    public static String getAttributeKey(String name){
        return "attributes." + ADCore.MODID + "." + name;
    }

    public static String getKey(Holder<?> holder){
        ResourceKey<?> resourceKey = holder.unwrapKey().orElseThrow();
        if (resourceKey.registry().equals(Registries.ATTRIBUTE.identifier())) {
            return getAttributeKey(getPath(holder));
        } else if(resourceKey.registry().equals(Registries.SOUND_EVENT.identifier())) {
            return getSoundKey(getPath(holder));
        } else throw new IllegalArgumentException("Unknown registry: " + resourceKey.registry());
    }

    public static String getDamageTypeKey(ResourceKey<DamageType> resourceKey) {
        return "death.attack." + ADCDamageTypes.getMsgId(resourceKey);
    }

    public static String getDamageTypeKeyPlayer(ResourceKey<DamageType> resourceKey) {
        return getDamageTypeKey(resourceKey) + ".player";
    }

    public static String getSoundKey(String name){
        return "subtitle." + ADCore.MODID + ".sound." + name;
    }

    public static String getPath(Holder<?> holder) {
        return holder.unwrapKey()
                .map(ResourceKey::identifier)
                .map(Identifier::getPath)
                .orElse("");
    }
}
