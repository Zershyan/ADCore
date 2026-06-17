package io.zershyan.adcore.example.registry;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.registry.entry.condition.Condition;
import io.zershyan.adcore.common.registry.entry.condition.ConditionRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ExampleConditions {
    public static final DeferredRegister<Condition> REGISTRY = DeferredRegister.create(ConditionRegistry.REGISTRY, ADCore.MODID);
    //条件：生命值大于80%
    public static final Supplier<Condition> HEALTH_MORE = REGISTRY.register("health_more_than_80", () ->
            new Condition(entity -> entity.getHealth() / entity.getMaxHealth() > 0.8f));
    //条件：攻速大于2.0
    public static final Supplier<Condition> ATK_MORE = REGISTRY.register("atk_more_than_2", () ->
            new Condition(entity -> ADCoreAPI.helper(entity).getAtkSpeed() > 2.0f));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }

}
