package io.zershyan.adcore.common.event;

import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.api.helper.AttackEffectHelper;
import io.zershyan.adcore.common.registry.entry.atkEffect.AttackEffect;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/**
 * This event is fired when an entity joins level.
 * You can attach attack effect to all the living entities who join level.
 * You also can attach with method {@link AttackEffectHelper#putAttackEffect}.
 * The attack effect is persistent data. So you don't need to do this twice.
 * @see EntityJoinLevelEvent
 */
public class ADCAttackEffectAttachEvent extends Event {
    private final LivingEntity entity;
    private final EntityJoinLevelEvent event;
    private final AttackEffectHelper helper;
    public ADCAttackEffectAttachEvent(LivingEntity entity, EntityJoinLevelEvent event) {
        super();
        this.entity = entity;
        this.event = event;
        this.helper = ADCoreAPI.attackEffectHelper(entity);
    }

    public void attach(Identifier identifier, AttackEffect effect) {
        helper.putAttackEffect(identifier, effect);
    }

    public boolean safeAttach(Identifier identifier, AttackEffect effect) {
        if(helper.hasAttackEffect(identifier)) {
            return false;
        } else {
            attach(identifier, effect);
            return true;
        }
    }

    public void attachIfNewEntity(Identifier identifier, AttackEffect effect) {
        if(event.loadedFromDisk()) return;
        safeAttach(identifier, effect);
    }

    public EntityJoinLevelEvent getEvent() {
        return event;
    }

    public LivingEntity getEntity() {
        return entity;
    }
}
