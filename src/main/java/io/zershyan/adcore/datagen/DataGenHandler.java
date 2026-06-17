package io.zershyan.dreamworld.datagen;

import io.zershyan.dreamworld.Dreamworld;
import io.zershyan.dreamworld.datagen.provider.client.ModItemModelProvider;
import io.zershyan.dreamworld.datagen.provider.client.ModLangProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Dreamworld.MODID)
public class DataGenHandler {
    @SubscribeEvent
    public static void clientDataGather(GatherDataEvent.Client event) {
        event.createProvider(ModItemModelProvider::new);
        event.createProvider(ModLangProvider::runZhCn);
        event.createProvider(ModLangProvider::runEnUs);
    }

    @SubscribeEvent
    public static void serverDataGather(GatherDataEvent.Server event) {

    }
}
