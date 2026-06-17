package io.zershyan.adcore.common.registry;

import io.zershyan.adcore.ADCore;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ADCSounds {
    private static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, ADCore.MODID);

    public static final List<Holder<SoundEvent>> SOUNDS = new ArrayList<>();
    public static final HashMap<Holder<SoundEvent>, Integer> SOUNDS_MAP = new HashMap<>();

    public static final Holder<SoundEvent> EVASION_SUCCESS = registerSoundEvent("evasion_success", 3);
    public static final Holder<SoundEvent> HIT_FAILURE = registerSoundEvent("hit_failure", 3);

    private static Holder<SoundEvent> registerSoundEvent(String name) {
        Holder<SoundEvent> soundEvent = REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(
                Identifier.fromNamespaceAndPath(ADCore.MODID, name)));
        SOUNDS.add(soundEvent);
        return soundEvent;
    }

    private static Holder<SoundEvent> registerSoundEvent(String name, int num) {
        Holder<SoundEvent> soundEvent = REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(
                Identifier.fromNamespaceAndPath(ADCore.MODID, name)));
        SOUNDS_MAP.put(soundEvent, num);
        return soundEvent;
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
