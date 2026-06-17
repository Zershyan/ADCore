package io.zershyan.adcore.common.utils;

import io.zershyan.adcore.common.event.ADCSoundEvent;
import io.zershyan.adcore.network.data.SoundData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class PlayerUtils {
    public static void playClientSound(ServerPlayer player, SoundEvent sound) {
        if (NeoForge.EVENT_BUS.post(new ADCSoundEvent(sound, player)).isCanceled()) return;
        PacketDistributor.sendToPlayer(player, new SoundData(sound));
    }

    public static void playLocalSound(LocalPlayer player, SoundEvent sound) {
        if (NeoForge.EVENT_BUS.post(new ADCSoundEvent(sound, player)).isCanceled()) return;
        player.level().playLocalSound(player, sound, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static void playServerSound(ServerPlayer player, @Nullable Entity except, SoundEvent sound) {
        player.level().playSound(except, player.blockPosition(), sound, player.getSoundSource(), 1.0F, 1.0F);
    }
}
