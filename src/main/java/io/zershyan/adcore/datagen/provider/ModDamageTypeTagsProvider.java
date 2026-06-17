package io.zershyan.adcore.datagen.provider;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.registry.ADCDamageTypes;
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
        tag(DamageTypeTags.IS_PLAYER_ATTACK)
                .add(ADCDamageTypes.NORMAL_DAMAGE)
                .add(ADCDamageTypes.ATTACK_DAMAGE)
                .add(ADCDamageTypes.MAGIC_DAMAGE)
                .add(ADCDamageTypes.TRUE_DAMAGE);
        tag(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(ADCDamageTypes.NORMAL_DAMAGE)
                .add(ADCDamageTypes.ATTACK_DAMAGE)
                .add(ADCDamageTypes.MAGIC_DAMAGE)
                .add(ADCDamageTypes.TRUE_DAMAGE);
        tag(DamageTypeTags.BYPASSES_ARMOR)
                .add(ADCDamageTypes.MAGIC_DAMAGE)
                .add(ADCDamageTypes.TRUE_DAMAGE);
        tag(DamageTypeTags.BYPASSES_WOLF_ARMOR)
                .add(ADCDamageTypes.MAGIC_DAMAGE)
                .add(ADCDamageTypes.TRUE_DAMAGE);
        tag(DamageTypeTags.BYPASSES_RESISTANCE)
                .add(ADCDamageTypes.TRUE_DAMAGE);
        tag(DamageTypeTags.BYPASSES_EFFECTS)
                .add(ADCDamageTypes.TRUE_DAMAGE);
        tag(DamageTypeTags.BYPASSES_ENCHANTMENTS)
                .add(ADCDamageTypes.TRUE_DAMAGE);
    }
}
