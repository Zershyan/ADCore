package io.zershyan.adcore.datagen;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.registry.ADCDamageTypes;
import io.zershyan.adcore.datagen.provider.ModDamageTypeTagsProvider;
import io.zershyan.adcore.datagen.provider.ModLangProvider;
import io.zershyan.adcore.datagen.provider.ModPackMetadataProvider;
import io.zershyan.adcore.datagen.provider.ModSoundProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.commons.lang3.function.Consumers;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ADCore.MODID)
public class DataGenHandler {
    @SubscribeEvent
    public static void dataGather(GatherDataEvent.Client event) {
        ModContainer modContainer = event.getModContainer();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        RegistrySetBuilder registry = new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, ADCDamageTypes::register);
        var datapackProvider = event.createProvider(output -> new DatapackBuiltinEntriesProvider(
                output, lookupProvider, registry, Consumers.nop(), Set.of(modContainer.getModId())));
        CompletableFuture<HolderLookup.Provider> registryProvider = datapackProvider.getRegistryProvider();

        GatherDataEvent.DataProviderFromOutput<@NotNull ModDamageTypeTagsProvider> damageTypeTagsProvider =
                output -> new ModDamageTypeTagsProvider(output, registryProvider);

        event.createProvider(ModSoundProvider::new);
        event.createProvider(ModPackMetadataProvider::new);
        event.createProvider(ModLangProvider::runZhCn);
        event.createProvider(ModLangProvider::runEnUs);
        event.createProvider(damageTypeTagsProvider);
    }
}
