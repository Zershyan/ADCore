package io.zershyan.adcore.network.handler;

import io.zershyan.adcore.common.utils.PlayerUtils;
import io.zershyan.adcore.network.data.SoundData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    public static void playSound(SoundData sound, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            ClientLevel level = instance.level;
            LocalPlayer player = instance.player;
            if(level == null) return;
            if(player == null) return;
            PlayerUtils.playLocalSound(player, sound.soundEvent());
        });
    }
}
