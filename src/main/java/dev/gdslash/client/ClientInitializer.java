package dev.gdslash.client;

import dev.gdslash.client.config.ConfigManager;
import dev.gdslash.client.module.ModuleRegistry;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Fabric entrypoint. Keep bootstrap code here; features belong in modules. */
public final class ClientInitializer implements ClientModInitializer {
    public static final String MOD_ID = "gdslash_client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ModuleRegistry MODULES = new ModuleRegistry();
    public static final ConfigManager CONFIG = new ConfigManager(MODULES);

    @Override
    public void onInitializeClient() {
        CONFIG.load();
        LOGGER.info("Client foundation initialized for Minecraft 1.21.11.");
    }
}
