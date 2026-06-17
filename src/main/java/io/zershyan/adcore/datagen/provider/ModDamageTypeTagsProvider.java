package io.zershyan.adcore.datagen.provider.server;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.registry.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ADCore.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider registries) {
        tag(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(ModDamageTypes.NORMAL_DAMAGE)
                .add(ModDamageTypes.MAGIC_DAMAGE)
                .add(ModDamageTypes.TRUE_DAMAGE);
        tag(DamageTypeTags.BYPASSES_ARMOR)
                .add(ModDamageTypes.MAGIC_DAMAGE)
                .add(ModDamageTypes.TRUE_DAMAGE);
        tag(DamageTypeTags.BYPASSES_RESISTANCE)
                .add(ModDamageTypes.TRUE_DAMAGE);
    }
}
