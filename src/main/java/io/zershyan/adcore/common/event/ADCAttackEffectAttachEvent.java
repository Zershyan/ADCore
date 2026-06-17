package io.zershyan.adcore.common.event;

import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.attackEffects.AttackEffect;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/**
 * This event is fired when an entity joins level.
 * You can attach attack effect to all the living entities who join level.
 * @see EntityJoinLevelEvent
 */
public class ADCAttackEffectApplyEvent extends EntityJoinLevelEvent {
    public ADCAttackEffectApplyEvent(EntityJoinLevelEvent event) {
        super(event.getEntity(), event.getLevel());
    }

    public void forceAttach(Identifier identifier, AttackEffect effect) {
        Entity entity = getEntity();
        if(entity instanceof LivingEntity livingEntity) {
            ADCoreAPI.attackEffectHelper(livingEntity).putAttackEffect(identifier, effect);
        }
    }
}
