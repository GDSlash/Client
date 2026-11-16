package dev.gdslash.client;

import dev.gdslash.client.config.ConfigManager;
import dev.gdslash.client.hud.HudManager;
import dev.gdslash.client.hud.widgets.WatermarkHudWidget;
import dev.gdslash.client.input.KeybindManager;
import dev.gdslash.client.module.ModuleRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Fabric entrypoint. Keep bootstrap code here; features belong in modules. */
public final class ClientInitializer implements ClientModInitializer {
    public static final String MOD_ID = "gdslash_client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ModuleRegistry MODULES = new ModuleRegistry();
    public static final ConfigManager CONFIG = new ConfigManager(MODULES);
    public static final KeybindManager KEYBINDS = new KeybindManager(MODULES);
    public static final HudManager HUD = new HudManager();

    @Override
    public void onInitializeClient() {
        CONFIG.load();
        KEYBINDS.initialize();

        HUD.register(new WatermarkHudWidget());
        HUD.load();
        HUD.initialize();

        boolean registered = ResourceLoader.registerBuiltinPack(
                Identifier.fromNamespaceAndPath(MOD_ID, "client-default"),
                FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                PackActivationType.DEFAULT_ENABLED
        );

        if (registered) {
            LOGGER.info("Registered the bundled Client Default resource pack (enabled by default).");
        } else {
            LOGGER.warn("Could not register the bundled Client Default resource pack.");
        }

        LOGGER.info("Client foundation initialized for Minecraft 1.21.11.");
    }
}
