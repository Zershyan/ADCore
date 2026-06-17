package io.zershyan.adcore.common.registry.entry.conditionModifier;

import io.zershyan.adcore.ADCore;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = ADCore.MODID)
public class ConditionModifierRegistry {
    private static final ResourceKey<Registry<ConditionModifier>> REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ADCore.MODID, "condition_attribute_modifier"));
    public static final Registry<ConditionModifier> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY).sync(true).create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(REGISTRY);
    }
}
