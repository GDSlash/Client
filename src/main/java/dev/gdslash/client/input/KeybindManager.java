package dev.gdslash.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import dev.gdslash.client.ClientInitializer;
import dev.gdslash.client.module.Module;
import dev.gdslash.client.module.ModuleRegistry;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Registers one native Minecraft key mapping per module and dispatches presses.
 * Rebinding and persistence are handled by Minecraft's Controls screen/options.
 */
public final class KeybindManager {
    private final ModuleRegistry modules;
    private final Map<String, KeyMapping> bindings = new LinkedHashMap<>();
    private KeyMapping.Category category;
    private boolean initialized;

    public KeybindManager(ModuleRegistry modules) {
        this.modules = modules;
    }

    public void initialize() {
        if (initialized) return;
        initialized = true;

        category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(ClientInitializer.MOD_ID, "modules")
        );
        modules.setRegistrationListener(this::register);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (Map.Entry<String, KeyMapping> entry : bindings.entrySet()) {
                while (entry.getValue().consumeClick()) {
                    modules.find(entry.getKey()).ifPresent(module -> {
                        module.setEnabled(!module.isEnabled());
                        ClientInitializer.CONFIG.save();
                    });
                }
            }
        });
    }

    private void register(Module module) {
        if (bindings.containsKey(module.id())) return;

        String translationKey = "key." + ClientInitializer.MOD_ID + ".module." + module.id();
        KeyMapping binding = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        translationKey,
                        InputConstants.Type.KEYSYM,
                        InputConstants.UNKNOWN.getValue(),
                        category
                )
        );
        bindings.put(module.id(), binding);
    }

    public Optional<KeyMapping> bindingFor(String moduleId) {
        return Optional.ofNullable(bindings.get(moduleId));
    }

    /** Returns another module using this key, if one exists. */
    public Optional<Module> findConflict(String moduleId) {
        KeyMapping selected = bindings.get(moduleId);
        if (selected == null || selected.isUnbound()) return Optional.empty();

        InputConstants.Key selectedKey = KeyBindingHelper.getBoundKeyOf(selected);
        return bindings.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(moduleId))
                .filter(entry -> KeyBindingHelper.getBoundKeyOf(entry.getValue()).equals(selectedKey))
                .map(entry -> modules.find(entry.getKey()).orElse(null))
                .filter(module -> module != null)
                .findFirst();
    }
}
