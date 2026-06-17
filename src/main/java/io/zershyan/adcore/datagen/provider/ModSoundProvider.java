package io.zershyan.adcore.datagen.provider;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.registry.ADCSounds;
import io.zershyan.adcore.datagen.init.ADCoreLang;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider {
    public ModSoundProvider(PackOutput output) {
        super(output, ADCore.MODID);
    }

    @Override
    public void registerSounds() {
        ADCSounds.SOUNDS.forEach(sound -> sound.unwrapKey()
                .ifPresent(key -> {
                    Identifier name = key.identifier();
                    String translatableKey = ADCoreLang.getKey(sound);
                    add(name, definition().with(sound(name)).subtitle(translatableKey));
                })
        );

        ADCSounds.SOUNDS_MAP.forEach((sound, number) -> sound.unwrapKey()
                .ifPresent(key -> {
                    Identifier name = key.identifier();
                    String translatableKey = ADCoreLang.getKey(sound);
                    SoundDefinition.Sound[] sounds = new SoundDefinition.Sound[number];
                    for (int i = 0; i < number; i++) {
                        sounds[i] = sound(name.withSuffix("_" + i));
                    }
                    add(name, definition().with(sounds).subtitle(translatableKey));
                })
        );
    }
}
