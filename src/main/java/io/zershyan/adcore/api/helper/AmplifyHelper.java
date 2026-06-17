package io.zershyan.adcore.api.helper;

import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.common.event.ADCAttackEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;

/**
 * This helper uses to calculate amplifying
 * @see ADCoreAPI#amplifyHelper
 */
public class AmplifyHelper extends AttackHelper {
    public AmplifyHelper(LivingEntity attacker) {
        super(attacker);
    }

    public float getAmplifyWithTarget(Entity target) {
        return isRangedAtk(target) ? getRangedAmplify() : getMeleeAmplify();
    }

    private float calculateAmplify(Entity target, float damage) {
        if(!(attacker.level() instanceof ServerLevel)) return damage;
        if(target.asLivingEntity() == null) return damage;
        return damage + damage * getAmplifyWithTarget(target);
    }

    /**
     * Try to apply amplifying to damage value.
     * @param target target
     * @param damage damage value
     * @return Amplified value
     */
    public float tryAmplify(Entity target, float damage) {
        ADCAttackEvent.PreCalAmplify event = NeoForge.EVENT_BUS.post(
                new ADCAttackEvent.PreCalAmplify(attacker, damage));
        if(event.isCanceled()) return damage;
        return calculateAmplify(target, event.getNewDamage());
    }
}
