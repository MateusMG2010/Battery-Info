package net.mgstudios.batteryinfo.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.mgstudios.batteryinfo.util.BatterySupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.systems.RenderSystem;

public class BatteryInfoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if(!Minecraft.getInstance().options.hideGui && !Minecraft.getInstance().getDebugOverlay().showDebugScreen()){
                final int batteryLevel = BatterySupplier.getLevel();
                final int Y = Minecraft.getInstance().getWindow().getGuiScaledHeight() - 30;

                if (batteryLevel != -1) {
                    final ResourceLocation batteryImage = ResourceLocation.fromNamespaceAndPath("batteryinfo", "textures/battery_%s.png".formatted(getBatteryBlocks(batteryLevel)));
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, batteryImage);

                    context.blit(batteryImage,5,Y,0,0,50,25,50,25);
                }
            }
        });
    }
    private int getBatteryBlocks(int batteryLevel) {
        if (batteryLevel > 80) return 5;
        if (batteryLevel > 60) return 4;
        if (batteryLevel > 40) return 3;
        if (batteryLevel > 20) return 2;
        return 1;
    }
}