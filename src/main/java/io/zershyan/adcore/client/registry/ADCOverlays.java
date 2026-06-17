package io.zershyan.adcore.client.registry;

import io.zershyan.adcore.client.registry.overlay.AttributeOverlay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class ADCOverlays {
    public static void registerOverlay(RegisterGuiLayersEvent event)  {
        event.registerAboveAll(AttributeOverlay.overlayId, new AttributeOverlay());
    }

    public static void register(IEventBus modEventBus) {
        if(FMLEnvironment.getDist() != Dist.DEDICATED_SERVER)
            modEventBus.addListener(ADCOverlays::registerOverlay);
    }

}
