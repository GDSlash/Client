package dev.gdslash.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.resources.Identifier;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;

/** Owns HUD widgets, their Fabric render layers, and persisted layout settings. */
public final class HudManager {
    private final Map<String, HudWidget> widgets = new LinkedHashMap<>();
    private final Path configFile = FabricLoader.getInstance().getConfigDir()
            .resolve("gdslash-client-hud.properties");
    private boolean initialized;

    public void register(HudWidget widget) {
        if (initialized) throw new IllegalStateException("Register HUD widgets before initialization");
        if (widgets.putIfAbsent(widget.id(), widget) != null) {
            throw new IllegalArgumentException("Duplicate HUD widget id: " + widget.id());
        }
    }

    public void initialize() {
        if (initialized) return;
        initialized = true;

        for (HudWidget widget : widgets.values()) {
            Identifier layerId = Identifier.fromNamespaceAndPath("gdslash_client", "hud/" + widget.id());
            HudElementRegistry.addLast(layerId, (graphics, deltaTracker) -> {
                if (!widget.isVisible()) return;

                float scale = widget.scale();
                float scaledWidth = widget.width() * scale;
                float scaledHeight = widget.height() * scale;
                int x = switch (widget.anchor()) {
                    case TOP_LEFT, BOTTOM_LEFT -> widget.offsetX();
                    case TOP_RIGHT, BOTTOM_RIGHT -> Math.round(graphics.guiWidth() - widget.offsetX() - scaledWidth);
                };
                int y = switch (widget.anchor()) {
                    case TOP_LEFT, TOP_RIGHT -> widget.offsetY();
                    case BOTTOM_LEFT, BOTTOM_RIGHT -> Math.round(graphics.guiHeight() - widget.offsetY() - scaledHeight);
                };

                graphics.pose().pushMatrix();
                graphics.pose().translate(x, y);
                graphics.pose().scale(scale, scale);
                widget.render(graphics, deltaTracker);
                graphics.pose().popMatrix();
            });
        }
    }

    public Optional<HudWidget> find(String id) {
        return Optional.ofNullable(widgets.get(id));
    }

    public Collection<HudWidget> all() {
        return Collections.unmodifiableCollection(widgets.values());
    }

    public void load() {
        if (!Files.exists(configFile)) return;
        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(configFile)) {
            properties.load(reader);
            for (HudWidget widget : widgets.values()) {
                String prefix = "widget." + widget.id() + ".";
                widget.setVisible(Boolean.parseBoolean(properties.getProperty(prefix + "visible", "true")));
                HudAnchor anchor = parseAnchor(properties.getProperty(prefix + "anchor"));
                int x = parseInt(properties.getProperty(prefix + "x"), widget.offsetX());
                int y = parseInt(properties.getProperty(prefix + "y"), widget.offsetY());
                float scale = parseFloat(properties.getProperty(prefix + "scale"), widget.scale());
                widget.setPosition(anchor, x, y);
                widget.setScale(scale);
            }
        } catch (IOException exception) {
            System.err.println("Unable to load HUD config: " + exception.getMessage());
        }
    }

    public void save() {
        Properties properties = new Properties();
        for (HudWidget widget : widgets.values()) {
            String prefix = "widget." + widget.id() + ".";
            properties.setProperty(prefix + "visible", Boolean.toString(widget.isVisible()));
            properties.setProperty(prefix + "anchor", widget.anchor().name());
            properties.setProperty(prefix + "x", Integer.toString(widget.offsetX()));
            properties.setProperty(prefix + "y", Integer.toString(widget.offsetY()));
            properties.setProperty(prefix + "scale", Float.toString(widget.scale()));
        }
        try {
            Files.createDirectories(configFile.getParent());
            try (Writer writer = Files.newBufferedWriter(configFile)) {
                properties.store(writer, "Client HUD layout");
            }
        } catch (IOException exception) {
            System.err.println("Unable to save HUD config: " + exception.getMessage());
        }
    }

    private static HudAnchor parseAnchor(String value) {
        if (value == null) return HudAnchor.TOP_LEFT;
        try {
            return HudAnchor.valueOf(value);
        } catch (IllegalArgumentException exception) {
            return HudAnchor.TOP_LEFT;
        }
    }

    private static int parseInt(String value, int fallback) {
        try { return value == null ? fallback : Integer.parseInt(value); }
        catch (NumberFormatException exception) { return fallback; }
    }

    private static float parseFloat(String value, float fallback) {
        try { return value == null ? fallback : Float.parseFloat(value); }
        catch (NumberFormatException exception) { return fallback; }
    }
}
