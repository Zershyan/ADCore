package io.zershyan.adcore.util.mixin;

import org.spongepowered.asm.mixin.Unique;

public interface IMixinDamageSource {
    @Unique
    void adcore$critical(boolean critical);

    @Unique
    boolean adcore$isCritical();
}
