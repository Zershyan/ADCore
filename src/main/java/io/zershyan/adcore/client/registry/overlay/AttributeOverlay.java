package io.zershyan.adcore.client.registry.overlay;

import io.zershyan.adcore.ADCore;
import io.zershyan.adcore.api.ADCoreAPI;
import io.zershyan.adcore.api.helper.ADCHelper;
import io.zershyan.adcore.config.ClientConfig;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.NonNull;

import java.awt.*;

public class AttributeOverlay implements GuiLayer {
    public static final Identifier overlayId = Identifier.fromNamespaceAndPath(ADCore.MODID, "attribute_overlay");
    private static final Identifier atkSpeedPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/atk_speed.png");
    private static final Identifier meleeAtkPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/melee_atk.png");
    private static final Identifier rangedAtkPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/ranged_atk.png");
    private static final Identifier criticalRatePng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/critical_rate.png");
    private static final Identifier criticalDamagePng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/critical_damage.png");
    private static final Identifier meleeAmplifyPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/melee_amplify.png");
    private static final Identifier rangedAmplifyPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/ranged_amplify.png");
    private static final Identifier hitRatePng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/hit_rate.png");
    private static final Identifier evasionRatePng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/evasion_rate.png");
    private static final Identifier meleePenetrationPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/melee_penetration.png");
    private static final Identifier rangedPenetrationPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/ranged_penetration.png");
    private static final Identifier attackLifeStealPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/attack_life_steal.png");
    private static final Identifier almightyLifeStealPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/almighty_life_steal.png");
    private static final Identifier healAmplifyPng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/heal_amplify.png");
    private static final Identifier damageResistancePng = Identifier.fromNamespaceAndPath(ADCore.MODID, "textures/gui/icon/damage_resistance.png");
    private static final int size = 12;

    @Override
    public void render(@NonNull GuiGraphicsExtractor guiGraphics, @NonNull DeltaTracker deltaTracker) {
        if(!ClientConfig.showAttributeOverlay.get()) return;
        Minecraft instance = Minecraft.getInstance();
        LocalPlayer player = instance.player;
        if(!ADCoreAPI.getModStatus(player)) return;
        if(player == null) return;
        Font font = instance.font;
        int screenHeight = guiGraphics.guiHeight();

        Object2ObjectArrayMap<Identifier, String> needRender = CacheValue.getCachedValue();
        if(!Minecraft.getInstance().options.keyPlayerList.isDown()) filterNeedRender(needRender);

        int xOffset = ClientConfig.showAttributeXOffset.getAsInt();
        float scale = ClientConfig.showAttributeScale.get().floatValue();
        int height = (screenHeight - Mth.ceil(13 * (needRender.size() + 1) * scale) - ClientConfig.showAttributeYOffset.getAsInt()) / 2;
        for (Identifier identifier : needRender.keySet()) {
            Matrix3x2fStack pose = guiGraphics.pose();
            pose.pushMatrix().translate(xOffset, height).scale(scale);
            String string = needRender.get(identifier);
            renderAttribute(guiGraphics, identifier, font, string);
            pose.popMatrix();
            height += Mth.ceil((13 * scale));
        }

    }

    private void renderAttribute(GuiGraphicsExtractor gui, Identifier pngId, Font font, String str) {
        gui.blit(RenderPipelines.GUI_TEXTURED, pngId, 0, 0, 0, 0, size, size, size, size);
        gui.text(font, str, 13, 2, Color.WHITE.getRGB());
    }

    private void filterNeedRender(Object2ObjectArrayMap<Identifier, String> allValues) {
        if(!ClientConfig.showMeleeAtk.get()) allValues.remove(meleeAtkPng);
        if(!ClientConfig.showRangedAtk.get()) allValues.remove(rangedAtkPng);
        if(!ClientConfig.showAtkSpeed.get()) allValues.remove(atkSpeedPng);
        if(!ClientConfig.showCriticalRate.get()) allValues.remove(criticalRatePng);
        if(!ClientConfig.showCriticalDamage.get()) allValues.remove(criticalDamagePng);
        if(!ClientConfig.showAttackLifeSteal.get()) allValues.remove(attackLifeStealPng);
        if(!ClientConfig.showAlmightyLifeSteal.get()) allValues.remove(almightyLifeStealPng);
        if(!ClientConfig.showMeleeAmplify.get()) allValues.remove(meleeAmplifyPng);
        if(!ClientConfig.showRangedAmplify.get()) allValues.remove(rangedAmplifyPng);
        if(!ClientConfig.showMeleePenetration.get()) allValues.remove(meleePenetrationPng);
        if(!ClientConfig.showRangedPenetration.get()) allValues.remove(rangedPenetrationPng);
        if(!ClientConfig.showHealAmplify.get()) allValues.remove(healAmplifyPng);
        if(!ClientConfig.showDamageResistance.get()) allValues.remove(damageResistancePng);
        if(!ClientConfig.showHitRate.get()) allValues.remove(hitRatePng);
        if(!ClientConfig.showEvasionRate.get()) allValues.remove(evasionRatePng);
    }

    @EventBusSubscriber(modid = ADCore.MODID)
    public static class CacheValue {
        private static final Object2ObjectArrayMap<Identifier, String> cachedValue = new Object2ObjectArrayMap<>();
        private static int tickCount = 0;

        @SubscribeEvent
        public static void refreshCache(ClientTickEvent.Pre event) {
            if(tickCount++ % 10 == 0) refreshCache();
        }

        static void refreshCache() {
            LocalPlayer player = Minecraft.getInstance().player;
            if(player == null) return;
            ADCHelper helper = ADCoreAPI.helper(player);
            cachedValue.put(meleeAtkPng, formatNumber(helper.getMeleeAtk()));
            cachedValue.put(rangedAtkPng, formatNumber(helper.getRangedAtk()));
            cachedValue.put(atkSpeedPng, formatNumber(helper.getAtkSpeed()) + "/s");
            cachedValue.put(criticalRatePng, formatPercentage(helper.getCriticalRate()));
            cachedValue.put(criticalDamagePng, formatPercentage(helper.getCriticalDamage()));
            cachedValue.put(attackLifeStealPng, formatPercentage(helper.getAttackLifeSteal()));
            cachedValue.put(almightyLifeStealPng, formatPercentage(helper.getAlmightyLifeSteal()));
            cachedValue.put(meleeAmplifyPng, formatPercentage(helper.getMeleeAmplify()));
            cachedValue.put(rangedAmplifyPng, formatPercentage(helper.getRangedAmplify()));
            cachedValue.put(meleePenetrationPng, formatNumber(helper.getMeleePenetration()) + " | " + formatPercentage(helper.getMeleePenetrationRate()));
            cachedValue.put(rangedPenetrationPng, formatNumber(helper.getRangedPenetration()) + " | " + formatPercentage(helper.getRangedPenetrationRate()));
            cachedValue.put(healAmplifyPng, formatPercentage(helper.getHealAmplify()));
            cachedValue.put(damageResistancePng, formatPercentage(helper.getDamageResistance()));
            cachedValue.put(hitRatePng, formatPercentage(helper.getHitRate()));
            cachedValue.put(evasionRatePng, formatPercentage(helper.getEvasionRate()));
            tickCount = 0;
        }

        static Object2ObjectArrayMap<Identifier, String> getCachedValue() {
            return cachedValue.clone();
        }

        private static String formatNumber(float value) {
            String format = String.format("%.2f", value);
            return format.replaceAll("0$", "").replaceAll("\\.0$", "");
        }

        private static String formatPercentage(float value) {
            return formatNumber(value * 100) + "%";
        }
    }
}
