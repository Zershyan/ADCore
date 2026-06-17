package io.zershyan.adcore.common.registry;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.config.StartupConfig;
import io.zershyan.adcore.datagen.init.ADCoreLang;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ADCAttributes {
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, ADCore.MODID);

    public static final Holder<Attribute> ADCORE_STATUS = REGISTRY.register("adcore_status", () ->
            new BooleanAttribute(ADCoreLang.getAttributeKey("adcore_status"), true).setSyncable(true));
    public static final Holder<Attribute> ONLY_ADCORE_FEATURE = REGISTRY.register("only_adcore_feature", () ->
            new BooleanAttribute(ADCoreLang.getAttributeKey("only_adcore_feature"), false).setSyncable(true));

    private static final List<Holder<Attribute>> attributes = new ArrayList<>(
            List.of(ONLY_ADCORE_FEATURE, ADCORE_STATUS));

    public static final Holder<Attribute> ATK_SPEED = registerRangedAttribute("atk_speed", StartupConfig.baseAtkSpeed);
    public static final Holder<Attribute> MELEE_ATK = registerRangedAttribute("melee_atk", StartupConfig.baseMeleeAtk);
    public static final Holder<Attribute> RANGED_ATK = registerRangedAttribute("ranged_atk", StartupConfig.baseRangedAtk);
    public static final Holder<Attribute> CRITICAL_RATE = registerRangedAttribute("critical_rate", StartupConfig.baseCriticalRate);
    public static final Holder<Attribute> CRITICAL_DAMAGE = registerRangedAttribute("critical_damage", StartupConfig.baseCriticalDamage);
    public static final Holder<Attribute> MELEE_AMPLIFY = registerRangedAttribute("melee_amplify", StartupConfig.baseMeleeAmplify);
    public static final Holder<Attribute> RANGED_AMPLIFY = registerRangedAttribute("ranged_amplify", StartupConfig.baseRangedAmplify);
    public static final Holder<Attribute> HIT_RATE = registerRangedAttribute("hit_rate", StartupConfig.baseHitRate);
    public static final Holder<Attribute> EVASION_RATE = registerRangedAttribute("evasion_rate", StartupConfig.baseEvasionRate);
    public static final Holder<Attribute> MELEE_PENETRATION = registerRangedAttribute("melee_penetration", StartupConfig.baseMeleePenetration);
    public static final Holder<Attribute> MELEE_PENETRATION_RATE = registerRangedAttribute("melee_penetration_rate", StartupConfig.baseMeleePenetrationRate);
    public static final Holder<Attribute> RANGED_PENETRATION = registerRangedAttribute("ranged_penetration", StartupConfig.baseRangedPenetration);
    public static final Holder<Attribute> RANGED_PENETRATION_RATE = registerRangedAttribute("ranged_penetration_rate", StartupConfig.baseRangedPenetrationRate);
    public static final Holder<Attribute> ATTACK_LIFE_STEAL = registerRangedAttribute("attack_life_steal", StartupConfig.baseAttackLifeSteal);
    public static final Holder<Attribute> ALMIGHTY_LIFE_STEAL = registerRangedAttribute("almighty_life_steal", StartupConfig.baseAlmightyLifeSteal);
    public static final Holder<Attribute> HEAL_AMPLIFY = registerRangedAttribute("heal_amplify", StartupConfig.baseHealAmplify);
    public static final Holder<Attribute> DAMAGE_RESISTANCE = registerRangedAttribute("damage_resistance", StartupConfig.baseDamageResistance);

    private static Holder<Attribute> registerRangedAttribute(String name, ModConfigSpec.DoubleValue doubleValue) {
        Holder<Attribute> attribute = REGISTRY.register(name, () -> {
            ModConfigSpec.Range<@NotNull Double> range = doubleValue.getSpec().getRange();
            double min, max;
            if(range == null) {
                min = Double.MAX_VALUE;
                max = Double.MIN_VALUE;
            } else {
                min = range.getMin();
                max = range.getMax();
            }
            return new RangedAttribute(ADCoreLang.getAttributeKey(name), doubleValue.get(), min, max).setSyncable(true);
        });

        attributes.add(attribute);
        return attribute;
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
        modEventBus.register(new ADCAttributes());
    }

    @SubscribeEvent
    public void attach(EntityAttributeModificationEvent event) {
        List<Identifier> list = StartupConfig.attributeAttach.get().stream().map(Identifier::parse).toList();
        event.getTypes().stream().collect(Collectors.filtering(entityType ->
                list.contains(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)), Collectors.toList())
        ).forEach(entityType -> {
            for (Holder<Attribute> attribute : attributes) {
                if(!event.has(entityType, attribute)) {
                    event.add(entityType, attribute);
                }
            }
        });
    }
}
