package io.zershyan.adcore.example;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.api.helper.ADCHelper;
import io.zershyan.adcore.common.event.ADCAttackEffectAttachEvent;
import io.zershyan.adcore.common.registry.ADCAttributes;
import io.zershyan.adcore.common.registry.entry.condition.modifier.ConditionModifier;
import io.zershyan.adcore.example.registry.ExampleAttackEffects;
import io.zershyan.adcore.example.registry.ExampleConditions;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * You can see all events of ADCore Mod in package.
 * @see io.zershyan.adcore.common.event
 */
public class ExampleHandler {

    /**
     * An example to attach Attack effect to entity.
     * @param event ADCAttackEffectAttachEvent
     */
    @SubscribeEvent
    public void register(ADCAttackEffectAttachEvent event) {
        if(event.getEntity() instanceof ServerPlayer) {
            event.attachIfNewEntity(Identifier.fromNamespaceAndPath(ADCore.MODID, "quick_attack_instance"), ExampleAttackEffects.QUICK_ATTACK.get());
            event.attachIfNewEntity(Identifier.fromNamespaceAndPath(ADCore.MODID, "repeat_attack_instance"), ExampleAttackEffects.REPEAT_ATTACK.get());
        }
    }

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ADCHelper helper = ADCoreAPI.helper(player);
            //加100近战攻击力，生命值条件
            helper.attribute(ADCAttributes.MELEE_ATK).modifyAttributeCondition(new ConditionModifier(
                    Identifier.fromNamespaceAndPath(ADCore.MODID, "test"), 100,
                    AttributeModifier.Operation.ADD_VALUE, ExampleConditions.HEALTH_MORE.get()
            ));
            //加50%生命偷取，攻速条件
            helper.attribute(ADCAttributes.ATTACK_LIFE_STEAL).modifyAttributeCondition(new ConditionModifier(
                    Identifier.fromNamespaceAndPath(ADCore.MODID, "test1"), 0.5,
                    AttributeModifier.Operation.ADD_VALUE, ExampleConditions.ATK_MORE.get()
            ));
        }
    }

    /**
     * @see ADCore
     * @param modEventBus modEventBus
     * @param neoEventBus neoEventBus
     */
    public static void register(IEventBus modEventBus, IEventBus neoEventBus) {
        neoEventBus.register(new ExampleHandler());
        ExampleAttackEffects.register(modEventBus);
        ExampleConditions.register(modEventBus);
    }
}
