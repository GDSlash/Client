package dev.gdslash.client.hud.widgets;

import dev.gdslash.client.hud.HudWidget;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

/** Small proof-of-life widget for the HUD rendering pipeline. */
public final class WatermarkHudWidget extends HudWidget {
    private static final String LABEL = "Client";

    public WatermarkHudWidget() {
        super("watermark", "Watermark");
    }

    @Override
    public int width() {
        return Minecraft.getInstance().font.width(LABEL);
    }

    @Override
    public int height() {
        return Minecraft.getInstance().font.lineHeight;
    }

    @Override
    public void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        graphics.drawString(Minecraft.getInstance().font, LABEL, 0, 0, 0xFFFFFFFF, true);
    }
}
