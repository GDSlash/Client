package dev.gdslash.client.module;

import java.util.Objects;

/** Base class for independently toggleable client features. */
public abstract class Module {
    private final String id;
    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean enabled;

    protected Module(String id, String name, String description, ModuleCategory category) {
        this.id = requireText(id, "id");
        this.name = requireText(name, "name");
        this.description = Objects.requireNonNull(description, "description");
        this.category = Objects.requireNonNull(category, "category");
    }

    public final String id() { return id; }
    public final String name() { return name; }
    public final String description() { return description; }
    public final ModuleCategory category() { return category; }
    public final boolean isEnabled() { return enabled; }

    public final void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    protected void onEnable() {}
    protected void onDisable() {}

    private static String requireText(String value, String field) {
        Objects.requireNonNull(value, field);
        if (value.isBlank()) throw new IllegalArgumentException(field + " cannot be blank");
        return value;
    }
}
