package io.zershyan.adcore.example.registry;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.example.registry.atkEffect.QuickAttackEffect;
import io.zershyan.adcore.example.registry.atkEffect.RepeatAttackEffect;
import io.zershyan.adcore.common.attackEffects.AttackEffectRegistry;
import io.zershyan.adcore.common.attackEffects.AttackEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ADCAttackEffects {
    public static final DeferredRegister<AttackEffect> REGISTRY = DeferredRegister.create(AttackEffectRegistry.REGISTRY, ADCore.MODID);
    public static final Supplier<AttackEffect> EXAMPLE = REGISTRY.register("example", QuickAttackEffect::new);
    public static final Supplier<AttackEffect> REPEAT = REGISTRY.register("repeat", RepeatAttackEffect::new);

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
