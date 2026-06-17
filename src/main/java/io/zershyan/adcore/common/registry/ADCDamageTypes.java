package io.zershyan.adcore.common.registry;

import io.zershyan.adcore.ADCore;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> NORMAL_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            Identifier.fromNamespaceAndPath(ADCore.MODID, "normal_damage"));
    public static final ResourceKey<DamageType> ATTACK_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            Identifier.fromNamespaceAndPath(ADCore.MODID, "attack_damage"));
    public static final ResourceKey<DamageType> MAGIC_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            Identifier.fromNamespaceAndPath(ADCore.MODID, "magic_damage"));
    public static final ResourceKey<DamageType> TRUE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            Identifier.fromNamespaceAndPath(ADCore.MODID, "true_damage"));

    public static void register(BootstrapContext<DamageType> context) {
        context.register(NORMAL_DAMAGE, new DamageType(getMsgId(NORMAL_DAMAGE), 0.0f));
        context.register(ATTACK_DAMAGE, new DamageType(getMsgId(ATTACK_DAMAGE), 0.0f));
        context.register(MAGIC_DAMAGE, new DamageType(getMsgId(MAGIC_DAMAGE), 0.0f));
        context.register(TRUE_DAMAGE, new DamageType(getMsgId(TRUE_DAMAGE), 0.0f));
    }

    private static String getMsgId(ResourceKey<DamageType> key) {
        return key.identifier().getNamespace() + "." + key.identifier().getPath();
    }

    public static Holder<DamageType> getDamageType(RegistryAccess access, ResourceKey<DamageType> key) {
        try { return access.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key); }
        catch (Exception _) { return null; }
    }

    public static boolean isADCoreDamage(DamageSource source) {
        return ADCore.MODID.equals(source.typeHolder().unwrapKey().map(ResourceKey::identifier)
                .map(Identifier::getNamespace).orElse(null));
    }

    public static boolean isNeedPenetration(DamageSource source) {
        return source.is(NORMAL_DAMAGE) || source.is(ATTACK_DAMAGE);
    }
}
