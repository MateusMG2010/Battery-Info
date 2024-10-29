package net.mgstudios.batteryinfo.client;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.mgstudios.batteryinfo.util.BatteryStatusSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class BatteryInfoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LogUtils.getLogger().info("Battery Info has been initialized!");
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if(!Minecraft.getInstance().options.hideGui && !Minecraft.getInstance().getDebugOverlay().showDebugScreen()){
                final int batteryLevel = BatteryStatusSupplier.getBatteryPercent();
                if (batteryLevel != -1) {
                    context.blit(resourceLocation -> RenderType.guiTextured(getBatteryImage(batteryLevel)), getBatteryImage(batteryLevel), 5,Minecraft.getInstance().getWindow().getGuiScaledHeight() - 30,0,0,50,25,50,25);
                }
            }
        });
    }
    private ResourceLocation getBatteryImage(int batteryLevel) {
        if (batteryLevel > 80) return ResourceLocation.fromNamespaceAndPath("batteryinfo", "textures/battery_5.png");
        if (batteryLevel > 60) return ResourceLocation.fromNamespaceAndPath("batteryinfo", "textures/battery_4.png");
        if (batteryLevel > 40) return ResourceLocation.fromNamespaceAndPath("batteryinfo", "textures/battery_3.png");
        if (batteryLevel > 20) return ResourceLocation.fromNamespaceAndPath("batteryinfo", "textures/battery_2.png");
        return ResourceLocation.fromNamespaceAndPath("batteryinfo", "textures/battery_1.png");
    }
}