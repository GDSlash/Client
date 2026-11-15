package dev.gdslash.client.config;

import dev.gdslash.client.module.Module;
import dev.gdslash.client.module.ModuleRegistry;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Persists module toggle state in Minecraft's config directory. */
public final class ConfigManager {
    private final ModuleRegistry modules;
    private final Path configFile;

    public ConfigManager(ModuleRegistry modules) {
        this.modules = modules;
        this.configFile = FabricLoader.getInstance().getConfigDir().resolve("gdslash-client.properties");
    }

    public void load() {
        if (!Files.exists(configFile)) return;
        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(configFile)) {
            properties.load(reader);
            for (Module module : modules.all()) {
                String value = properties.getProperty("module." + module.id());
                if (value != null) module.setEnabled(Boolean.parseBoolean(value));
            }
        } catch (IOException exception) {
            System.err.println("Unable to load client config: " + exception.getMessage());
        }
    }

    public void save() {
        Properties properties = new Properties();
        for (Module module : modules.all()) {
            properties.setProperty("module." + module.id(), Boolean.toString(module.isEnabled()));
        }
        try {
            Files.createDirectories(configFile.getParent());
            try (Writer writer = Files.newBufferedWriter(configFile)) {
                properties.store(writer, "Client module settings");
            }
        } catch (IOException exception) {
            System.err.println("Unable to save client config: " + exception.getMessage());
        }
    }
}
