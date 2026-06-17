package io.zershyan.adcore.mixin.adcore.common;

import io.zershyan.adcore.util.mixin.IMixinDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DamageSource.class)
public class MixinDamageSource implements IMixinDamageSource {
    @Unique
    private boolean adcore$isCritical = false;

    @Unique
    @Override
    public void adcore$critical(boolean critical) {
        this.adcore$isCritical = critical;
    }

    @Unique
    @Override
    public boolean adcore$isCritical() {
        return adcore$isCritical;
    }
}
