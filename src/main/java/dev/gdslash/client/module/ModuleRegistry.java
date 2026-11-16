package dev.gdslash.client.module;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/** Central registry for client feature modules. */
public final class ModuleRegistry {
    private final Map<String, Module> modules = new LinkedHashMap<>();
    private Consumer<Module> registrationListener = module -> {};

    public void register(Module module) {
        if (modules.putIfAbsent(module.id(), module) != null) {
            throw new IllegalArgumentException("Duplicate module id: " + module.id());
        }
        registrationListener.accept(module);
    }

    /** Installs a listener and immediately sends it all modules already registered. */
    public void setRegistrationListener(Consumer<Module> listener) {
        registrationListener = listener == null ? module -> {} : listener;
        modules.values().forEach(registrationListener);
    }

    public Optional<Module> find(String id) {
        return Optional.ofNullable(modules.get(id));
    }

    public Collection<Module> all() {
        return Collections.unmodifiableCollection(modules.values());
    }

    public Collection<Module> inCategory(ModuleCategory category) {
        return modules.values().stream()
                .filter(module -> module.category() == category)
                .toList();
    }
}
