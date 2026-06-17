package io.zershyan.adcore.datagen.init;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.registry.ModAttributes;
import io.zershyan.adcore.common.registry.ModDamageTypes;
import io.zershyan.adcore.common.registry.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.ArrayList;
import java.util.List;

public class ModLang {
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
        langList.add(new LangEntity<>(ModAttributes.ONLY_ADCORE_FEATURE, "覆盖原版逻辑", "Only ADCore Feature"));
        langList.add(new LangEntity<>(ModAttributes.ATK_SPEED, "攻击速度", "Atk Speed"));
        langList.add(new LangEntity<>(ModAttributes.MELEE_ATK, "近战攻击力", "Melee Attack"));
        langList.add(new LangEntity<>(ModAttributes.RANGED_ATK, "远程攻击力", "Ranged Attack"));
        langList.add(new LangEntity<>(ModAttributes.CRITICAL_RATE, "暴击率", "Critical Rate"));
        langList.add(new LangEntity<>(ModAttributes.CRITICAL_DAMAGE, "暴击伤害", "Critical Damage"));
        langList.add(new LangEntity<>(ModAttributes.MELEE_AMPLIFY, "近战增伤", "Melee Amplify"));
        langList.add(new LangEntity<>(ModAttributes.RANGED_AMPLIFY, "远程增伤", "Ranged Amplify"));
        langList.add(new LangEntity<>(ModAttributes.HIT_RATE, "命中率", "Hit Rate"));
        langList.add(new LangEntity<>(ModAttributes.EVASION_RATE, "闪避率", "Evasion Rate"));
        langList.add(new LangEntity<>(ModAttributes.MELEE_PENETRATION, "近战护甲穿透", "Melee Penetration"));
        langList.add(new LangEntity<>(ModAttributes.MELEE_PENETRATION_RATE, "近战护甲穿透比", "Melee Penetration Rate"));
        langList.add(new LangEntity<>(ModAttributes.RANGED_PENETRATION, "远程护甲穿透", "Ranged Penetration"));
        langList.add(new LangEntity<>(ModAttributes.RANGED_PENETRATION_RATE, "远程护甲穿透比", "Ranged Penetration Rate"));
        langList.add(new LangEntity<>(ModAttributes.ATTACK_LIFE_STEAL, "生命偷取", "Attack Life Steal"));
        langList.add(new LangEntity<>(ModAttributes.ALMIGHTY_LIFE_STEAL, "全能吸血", "Almighty Life Steal"));
        langList.add(new LangEntity<>(ModAttributes.HEAL_AMPLIFY, "治疗效果提升", "Healing Effect Amplify"));

        //sound
        langList.add(new LangEntity<>(ModSounds.EVASION_SUCCESS, "闪避成功声", "Evasion Success"));
        langList.add(new LangEntity<>(ModSounds.HIT_FAILURE, "攻击未命中声", "Hit Failure"));

        //damage type
        langList.add(new LangEntity<>(
                getDamageTypeKey(ModDamageTypes.NORMAL_DAMAGE),
                "%1$s 死于物理伤害。",
                "%1$s dies from physical damage."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKeyPlayer(ModDamageTypes.NORMAL_DAMAGE),
                "%1$s 被 %2$s 击杀。",
                "%1$s is killed by %2$s."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKey(ModDamageTypes.ATTACK_DAMAGE),
                "%1$s 死于物理伤害。",
                "%1$s dies from physical damage."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKeyPlayer(ModDamageTypes.ATTACK_DAMAGE),
                "%1$s 被 %2$s 使用普通攻击击杀。",
                "%1$s is killed by %2$s using normal attack."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKey(ModDamageTypes.MAGIC_DAMAGE),
                "%1$s 死于魔法伤害。",
                "%1$s dies from magic damage."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKeyPlayer(ModDamageTypes.MAGIC_DAMAGE),
                "%1$s 被 %2$s 使用魔法伤害击杀。",
                "%1$s is killed by %2$s using magic damage."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKey(ModDamageTypes.TRUE_DAMAGE),
                "%1$s 死于真实伤害。",
                "%1$s dies from true damage."
        ));
        langList.add(new LangEntity<>(
                getDamageTypeKeyPlayer(ModDamageTypes.TRUE_DAMAGE),
                "%1$s 被 %2$s 使用真实伤害击杀。",
                "%1$s is killed by %2$s using true damage."
        ));

        initTranslatableLang();
        ModConfigLang.initLang(langList);
    }

    private static void initTranslatableLang() {
        for (TranslatableLang value : TranslatableLang.values()) {
            ModLang.langList.add(value.langEntity);
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
        return "death.attack." + ADCore.MODID + "." + resourceKey.identifier().getPath();
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
