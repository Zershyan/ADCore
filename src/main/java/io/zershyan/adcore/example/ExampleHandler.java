package io.zershyan.adcore.example;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.event.ADCAttackEffectAttachEvent;
import io.zershyan.adcore.example.registry.ADCAttackEffects;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class ApplyAttackEffectHandler {
    @SubscribeEvent
    public static void register(ADCAttackEffectAttachEvent event) {
        if(event.getEntity() instanceof ServerPlayer) {
            event.attachIfNewEntity(Identifier.fromNamespaceAndPath(ADCore.MODID, "example"), ADCAttackEffects.EXAMPLE.get());
            event.attachIfNewEntity(Identifier.fromNamespaceAndPath(ADCore.MODID, "example_repeat"), ADCAttackEffects.REPEAT.get());
        }
    }
}
