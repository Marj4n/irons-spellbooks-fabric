package io.redspace.ironsspellbooks.api.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.fabricmc.api.EnvType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class BossbarManager {
    public record BossbarSprite(ResourceLocation spriteLocation, int width, int height, int buffer, int yBarOffset) {
    }

    private static final Map<UUID, BossbarSprite> CUSTOM_BARS = new HashMap<>();

    public static void startTracking(UUID uuid, BossbarSprite sprite) {
        CUSTOM_BARS.put(uuid, sprite);
    }

    public static void stopTracking(UUID uuid) {
        CUSTOM_BARS.remove(uuid);
    }

    public static boolean renderCustomBossbar(net.minecraft.client.gui.GuiGraphics guiGraphics, int xPosition, int yPosition, net.minecraft.world.BossEvent bossEvent) {
        BossbarSprite customSprite = CUSTOM_BARS.get(bossEvent.getId());
        if (customSprite != null) {
            int y = yPosition + customSprite.yBarOffset;
            int x = (guiGraphics.guiWidth() - customSprite.width) / 2;

            RenderSystem.enableBlend();
            var sprite = customSprite.spriteLocation.withPrefix("textures/gui/sprites/").withSuffix(".png");

//            guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY, imageWidth, IMAGE_HEIGHT, 256, 256);
            guiGraphics.blit(sprite, x, y, 0, 0, customSprite.width, customSprite.height, customSprite.width, customSprite.height * 2);
            int progress = Mth.lerpInt(bossEvent.getProgress(), 0, customSprite.width - customSprite.buffer * 2) + customSprite.buffer;
            if (progress > 0) {
                guiGraphics.blit(sprite, x, y, 0, customSprite.height, progress, customSprite.height, customSprite.width, customSprite.height * 2);
            }
//            guiGraphics.blit(customSprite.spriteLocation, customSprite.width, customSprite.height * 2, 0, 0, x, y, customSprite.width, customSprite.height);
//            int progress = Mth.lerpInt(bossEvent.getProgress(), 0, customSprite.width - customSprite.buffer * 2) + customSprite.buffer;
//            if (progress > 0) {
//                guiGraphics.blit(customSprite.spriteLocation, customSprite.width, customSprite.height * 2, 0, customSprite.height, x, y, progress, customSprite.height);
//            }
            RenderSystem.disableBlend();

            return true;
        }
        return false;
    }

    public static int getIncrement(net.minecraft.world.BossEvent event) {
        BossbarSprite sprite = CUSTOM_BARS.get(event.getId());
        return sprite == null ? 19 : 14 + sprite.height + sprite.yBarOffset;
    }
}
