package io.zershyan.adcore.network;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.network.data.SoundData;
import io.zershyan.adcore.network.handler.ClientPayloadHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ADCore.MODID)
public class PayloadRegister {
    private static final String PROTOCOL_VERSION = ModList.get()
            .getModContainerById(ADCore.MODID)
            .map(c -> c.getModInfo().getVersion().toString())
            .orElse("unknown");

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToClient(SoundData.TYPE, SoundData.STREAM_CODEC, ClientPayloadHandler::playSound);
    }
}
