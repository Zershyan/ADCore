package io.zershyan.adcore.example.registry;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffect;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffectRegistry;
import io.zershyan.adcore.example.ExampleHandler;
import io.zershyan.adcore.example.registry.atkEffect.QuickAttackEffect;
import io.zershyan.adcore.example.registry.atkEffect.RepeatAttackEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ExampleAttackEffects {
    public static final DeferredRegister<AttackEffect> REGISTRY = DeferredRegister.create(AttackEffectRegistry.REGISTRY, ADCore.MODID);
    //Duration 5 second
    public static final Supplier<AttackEffect> QUICK_ATTACK = REGISTRY.register("quick_attack",
            () -> new QuickAttackEffect(AttackEffect.Properties.of().durationTicks(100)));
    //Enable repeat info
    public static final Supplier<AttackEffect> REPEAT_ATTACK = REGISTRY.register("repeat_attack",
            () -> new RepeatAttackEffect(AttackEffect.Properties.of().durationTicks(100).enableRepeatInfo()));

    /**
     * @see ExampleHandler#register(IEventBus, IEventBus)
     * @param modEventBus Mod Bus
     */
    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
