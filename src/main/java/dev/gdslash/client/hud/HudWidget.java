package dev.gdslash.client.hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

/** A reusable, configurable piece of the on-screen HUD. */
public abstract class HudWidget {
    private final String id;
    private final String name;
    private boolean visible = true;
    private int offsetX = 8;
    private int offsetY = 8;
    private float scale = 1.0f;
    private HudAnchor anchor = HudAnchor.TOP_LEFT;

    protected HudWidget(String id, String name) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("HUD id cannot be blank");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("HUD name cannot be blank");
        this.id = id;
        this.name = name;
    }

    public final String id() { return id; }
    public final String name() { return name; }
    public final boolean isVisible() { return visible; }
    public final void setVisible(boolean visible) { this.visible = visible; }
    public final int offsetX() { return offsetX; }
    public final int offsetY() { return offsetY; }
    public final float scale() { return scale; }
    public final HudAnchor anchor() { return anchor; }

    public final void setPosition(HudAnchor anchor, int offsetX, int offsetY) {
        this.anchor = java.util.Objects.requireNonNull(anchor, "anchor");
        this.offsetX = Math.max(0, offsetX);
        this.offsetY = Math.max(0, offsetY);
    }

    public final void setScale(float scale) {
        if (!Float.isFinite(scale)) throw new IllegalArgumentException("Scale must be finite");
        this.scale = Math.max(0.5f, Math.min(3.0f, scale));
    }

    /** Width before scaling, used to align the widget to screen corners. */
    public abstract int width();

    /** Height before scaling, used to align the widget to screen corners. */
    public abstract int height();

    /** Draw at local coordinate (0, 0); HudManager applies position and scale. */
    public abstract void render(GuiGraphics graphics, DeltaTracker deltaTracker);
}
