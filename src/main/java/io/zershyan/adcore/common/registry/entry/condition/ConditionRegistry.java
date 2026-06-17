package io.zershyan.adcore.common.registry.entry.condition;

import io.zershyan.adcore.ADCore;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = ADCore.MODID)
public class ConditionRegistry {
    private static final ResourceKey<Registry<Condition>> REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ADCore.MODID, "condition"));
    public static final Registry<Condition> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY).sync(true).create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(REGISTRY);
    }
}
