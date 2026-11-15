# Client

> A Dawn-inspired Minecraft Java client, beginning as a Fabric mod for **Minecraft 1.21.11**. The final client name is intentionally undecided.

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-62A56B?logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![Fabric](https://img.shields.io/badge/Mod%20loader-Fabric-DBB36A)](https://fabricmc.net/)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Build](https://github.com/GDSlash/Client/actions/workflows/build.yml/badge.svg)](https://github.com/GDSlash/Client/actions/workflows/build.yml)

The goal is to build a polished, modular client with a Dawn-inspired visual direction. The first milestone is the in-game client mod; a standalone launcher is a later phase.

## Project status

**Early foundation — not yet a playable feature-complete client.**

This first commit series establishes:
- Fabric client entrypoint and Minecraft 1.21.11 build configuration.
- Official Mojang mappings, easing the later transition beyond 1.21.11.
- A module foundation with categories, lifecycle hooks, and a central registry.
- Properties-based persistence for module enabled states.
- GitHub Actions build validation.

The module registry is intentionally empty at this stage. HUD, ClickGUI, keybinds, and gameplay modules are future milestones.

## Requirements

- JDK 21
- Gradle 9.2.1 or newer
- Minecraft Java Edition 1.21.11 for in-game testing

## Build and run

Run `gradle build` to compile and remap the mod. The JAR is produced in `build/libs/`.

Run `gradle runClient` to launch the development client.

## Architecture

| Package | Responsibility |
| --- | --- |
| `dev.gdslash.client` | Fabric entrypoint and shared bootstrap |
| `dev.gdslash.client.module` | Feature base class, categories, and registry |
| `dev.gdslash.client.config` | Local client configuration persistence |

## Roadmap

1. **Client foundation:** ClickGUI, HUD framework/editor, keybind management, theme tokens, and settings.
2. **Gameplay modules:** PvP, movement, render, player, and quality-of-life features.
3. **Standalone launcher:** Microsoft sign-in, isolated profiles, version management, and loader selection.
4. **Expanded compatibility:** additional Java versions and loaders; investigate vanilla Bedrock launching separately.

## License

No open-source license has been selected yet. Until one is added, all rights are reserved by default.
