# Client

> A Dawn-inspired Minecraft Java client built on Fabric, starting with Minecraft **1.21.11**.

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-62A56B?logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![Fabric](https://img.shields.io/badge/Mod%20loader-Fabric-DBB36A)](https://fabricmc.net/)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Build](https://github.com/GDSlash/Client/actions/workflows/build.yml/badge.svg)](https://github.com/GDSlash/Client/actions/workflows/build.yml)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Client is an actively developed Minecraft client project focused on a polished, modular experience and a clean, Dawn-inspired interface. The initial implementation is a Fabric client mod. A standalone launcher is a later project milestone.

## Project status

**Early development.** The repository currently contains the Fabric project foundation and bundled resource-pack integration. It is not yet a feature-complete or release-ready client.

### Current foundation

- Fabric client entrypoint for Minecraft 1.21.11.
- Java 21 and official Mojang mappings.
- Module categories, a module lifecycle, and a central module registry.
- Properties-based persistence for module enabled states.
- Bundled **Client Default** resource pack, registered as enabled by default and still user-disableable.
- GitHub Actions build workflow.

The module registry is intentionally being built out incrementally. Input/keybind handling, HUD elements, gameplay modules, and the Dawn-inspired ClickGUI will be implemented in stages.

## Requirements

- Minecraft Java Edition **1.21.11** for in-game testing.
- **JDK 21**.
- Gradle **9.2.1 or newer** (or use a Gradle wrapper once one is added).

## Build and run

Clone the repository, then run:

```bash
gradle build
```

The remapped mod JAR is generated in `build/libs/`.

To launch the Fabric development client:

```bash
gradle runClient
```

## Project structure

| Path | Purpose |
| --- | --- |
| `src/main/java/dev/gdslash/client` | Fabric entrypoint and shared bootstrap |
| `src/main/java/dev/gdslash/client/module` | Module base class, categories, and registry |
| `src/main/java/dev/gdslash/client/config` | Client configuration persistence |
| `src/main/resources/resourcepacks/client-default` | Bundled resource pack |
| `.github/workflows` | Automated build and resource-pack maintenance |

## Resource pack

The bundled **Client Default** pack is stored at `src/main/resources/resourcepacks/client-default/` and registered through Fabric's built-in resource-pack API. It is enabled by default, but players can turn it off in Minecraft's Resource Packs screen.

The pack combines selected Vanilla Tweaks assets with **Motschen's Better Leaves: Lite** and other credited community assets. Their original notices and attribution files are retained where applicable. The license in the resource-pack directory applies to Better Leaves: Lite; it is separate from the license for this client project.

## Roadmap

1. **Core systems:** finish module management, settings, configuration, and lifecycle infrastructure.
2. **Input system:** keybinds, shortcuts, and reliable module toggling.
3. **HUD framework:** draggable elements, positioning, scaling, and visibility.
4. **Core HUD modules:** coordinates, FPS, keystrokes, armor status, and other useful overlays.
5. **Gameplay modules:** PvP, movement, rendering, player, and quality-of-life features.
6. **Resource-pack polish:** verify compatibility and refine bundled-pack behavior.
7. **Dawn-inspired ClickGUI:** build the visual module interface after the underlying systems are ready.
8. **Standalone launcher:** investigate Microsoft sign-in, isolated profiles, version management, and loader selection.

## License

The original Client source code in this repository is licensed under the [MIT License](LICENSE).

**This license does not relicense third-party content.** Bundled resource-pack assets, textures, and other third-party materials remain subject to their respective authors' licenses and notices. See the notices retained in `src/main/resources/resourcepacks/client-default/`.
