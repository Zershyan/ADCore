package io.zershyan.adcore;

import io.zershyan.adcore.client.registry.ADCOverlays;
import io.zershyan.adcore.common.registry.*;
import io.zershyan.adcore.config.StartupConfig;
import io.zershyan.adcore.example.ExampleHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;


public class ADCore {
    public static final String MODID = "adcore";

    @Mod(value = ADCore.MODID)
    public static class Common {
        public Common(IEventBus modEventBus, ModContainer modContainer) {
            IEventBus neoEventBus = NeoForge.EVENT_BUS;

            ADCSounds.register(modEventBus);
            ADCConfigs.register(modContainer);
            ADCCommands.register(neoEventBus);
            ADCAttributes.register(modEventBus);
            ADCAttachments.register(modEventBus);

            //Example
            if(!FMLEnvironment.isProduction() && StartupConfig.enableExample.get()) {
                ExampleHandler.register(modEventBus, neoEventBus);
            }
        }
    }

    @Mod(value = ADCore.MODID, dist = Dist.CLIENT)
    public static class Client {
        public Client(IEventBus modEventBus, ModContainer modContainer) {
            IEventBus neoEventBus = NeoForge.EVENT_BUS;

            ADCConfigs.registerClient(modContainer);
            ADCOverlays.register(modEventBus);
        }
    }
}
