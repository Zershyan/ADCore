package io.zershyan.adcore.common.registry.entry.atkEffect;

import io.zershyan.adcore.ADCore;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = ADCore.MODID)
public class AttackEffectRegistry {
    public static final Map<LivingEntity, Map<Identifier, AttackEffectData>> caches = new HashMap<>();
    private static final ResourceKey<Registry<AttackEffect>> REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ADCore.MODID, "attack_effect"));
    public static final Registry<AttackEffect> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY).sync(true).create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(REGISTRY);
    }

    @SubscribeEvent
    public static void resetCache(ServerStartedEvent event) {
        caches.clear();
    }
}
