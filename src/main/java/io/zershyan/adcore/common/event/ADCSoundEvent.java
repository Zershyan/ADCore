package io.zershyan.adcore.common.event;

import io.zershyan.adcore.common.registry.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

/**
 * It is an event to listen the evasion or hit fail sound.
 * Both client and server.
 */
public class ADCoreSoundEvent extends Event {
    private final Player player;
    private final SoundEvent sound;
    private boolean isCanceled = false;
    public ADCoreSoundEvent(SoundEvent sound, Player player) {
        this.sound = sound;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public boolean isEvasionSuccess() {
        return sound.equals(ModSounds.EVASION_SUCCESS.value());
    }

    public boolean isHitFailure() {
        return sound.equals(ModSounds.HIT_FAILURE.value());
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
}
