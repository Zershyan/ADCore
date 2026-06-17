package io.zershyan.adcore.common.registry;

import com.mojang.serialization.Codec;
import io.zershyan.adcore.ADCore;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ADCore.MODID);
    public static final Supplier<AttachmentType<List<Identifier>>> ATTACK_EFFECTS = REGISTRY.register("attack_effects", () -> AttachmentType
            .builder(() -> ((List<Identifier>) new ArrayList<Identifier>())).serialize(Codec.list(Identifier.CODEC).fieldOf("attack_effects")).build());

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
