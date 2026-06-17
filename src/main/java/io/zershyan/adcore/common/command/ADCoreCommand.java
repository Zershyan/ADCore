package io.zershyan.adcore.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.datagen.init.TranslatableLang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static net.minecraft.commands.Commands.*;

public class ADCoreCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> command) {
        command.requires(hasPermission(LEVEL_ADMINS))
                .then(literal("onlyADCoreDamage").then(argument("bool", BoolArgumentType.bool())
                        .then(argument("entity", EntityArgument.entities())
                                .executes(ADCoreCommand::onlyADCoreDamage)
                        )))
                .then(argument("bool", BoolArgumentType.bool())
                        .then(argument("entity", EntityArgument.entities())
                                .executes(ADCoreCommand::switchMod)
                        )
                );
    }

    private static int switchMod(CommandContext<CommandSourceStack> context) {
        TranslatableLang msg = TranslatableLang.COMMAND_SWITCH_SUCCESS;
        return changeFeature(context, msg, ADCoreAPI::setModStatus);
    }

    private static int onlyADCoreDamage(CommandContext<CommandSourceStack> context) {
        TranslatableLang msg = TranslatableLang.COMMAND_ONLY_ADCORE_DAMAGE_SWITCH_SUCCESS;
        return changeFeature(context, msg, ADCoreAPI::setOnlyCauseADCoreDamage);
    }

    private static int changeFeature(CommandContext<CommandSourceStack> context, TranslatableLang msg, BiFunction<LivingEntity, Boolean, Boolean> callback) {
        CommandSourceStack source = context.getSource();
        try {
            boolean bool = BoolArgumentType.getBool(context, "bool");
            List<LivingEntity> entities = new ArrayList<>();
            try {
                entities.addAll(EntityArgument.getEntities(context, "entity").stream().filter(LivingEntity.class::isInstance)
                        .map(e -> ((LivingEntity) e).self()).toList());
            } catch (Exception _) {
                entities.add(source.getPlayerOrException());
            }
            TranslatableLang lang;
            if(bool) lang = TranslatableLang.COMMAND_STATUS_ON;
            else lang = TranslatableLang.COMMAND_STATUS_OFF;
            MutableComponent statusComponent = Component.translatable(lang.getKey());
            List.copyOf(entities).forEach(entity -> {
                if(entity instanceof LivingEntity livingEntity && callback.apply(livingEntity, bool)) {
                    entities.remove(livingEntity);
                    if(livingEntity instanceof Player player) {
                        player.sendSystemMessage(Component.translatable(msg.getKey(), statusComponent));
                    }
                }
            });
            source.sendSuccess(() -> Component.translatable(
                    TranslatableLang.COMMAND_SUCCESS_TO.getKey(),
                    entities.size(), statusComponent
            ), true);
            return Command.SINGLE_SUCCESS;
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
