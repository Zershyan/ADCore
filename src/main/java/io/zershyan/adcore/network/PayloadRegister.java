package io.zershyan.adcore.network;

import io.zershyan.adcore.ADCore;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ADCore.MODID)
public class PayloadHandler {
    private static int cid = 0;
    private static final String PROTOCOL_VERSION = ModList.get()
            .getModContainerById(ADCore.MODID)
            .map(c -> c.getModInfo().getVersion().toString())
            .orElse("unknown");

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.
    }
}
