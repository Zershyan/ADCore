package io.zershyan.adcore.common.registry;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.common.command.ADCoreCommand;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.commands.Commands.literal;

public class ADCCommands {
    static final Set<String> adcoreCommand = new HashSet<>(Set.of(ADCore.MODID, "adc"));

    public static void register(IEventBus neoBus) {
        neoBus.addListener(ADCCommands::commonCommandRegister);
    }

    public static void commonCommandRegister(RegisterCommandsEvent event) {
        adcoreCommand.forEach(string -> {
            LiteralArgumentBuilder<CommandSourceStack> builder = literal(string);
            ADCoreCommand.register(builder);
            event.getDispatcher().register(builder);
        });
    }
}
