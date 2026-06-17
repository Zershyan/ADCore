package io.zershyan.adcore.network.handler;

import io.zershyan.adcore.network.data.SoundData;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import org.jetbrains.annotations.NotNull;

public class ServerPayloadHandler {
    public static void playSound(SoundData sound, IPayloadContext context) {
        context.enqueueWork(() -> {
            context.handle(sound);
        })
    }
}
