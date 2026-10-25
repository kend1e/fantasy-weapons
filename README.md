# Fantasy Weapons

A Minecraft mod with fantasy weapons for Forge, NeoForge, and Fabric. A companion to Fantasy Armor.

Downloads: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fantasy-weapons-medieval-series) · [Modrinth](https://modrinth.com/mod/fantasy_weapons).

Fabric requires [Fabric API](https://modrinth.com/mod/fabric-api) and [Cloth Config](https://modrinth.com/mod/cloth-config).

| Loader   | Minecraft                                                    |
|----------|--------------------------------------------------------------|
| Forge    | 1.19.4, 1.20.1, 1.21.1, 1.21.10, 1.21.11, 26.1.2, 26.2, 26.3 |
| NeoForge | 1.21.1, 1.21.10, 1.21.11, 26.1.2, 26.2, 26.3                 |
| Fabric   | 1.20.1, 1.21.1, 1.21.10, 1.21.11, 26.1.2, 26.2, 26.3         |

Build with JDK 25 and the shared [fantasy-build](https://github.com/kend1e/fantasy-build) repository checked out at `../fantasy-build`. Toolchains select Java 17, 21, or 25 for each target.

```bash
# Build all platforms or one platform
./gradlew assemble
./gradlew :platforms:fabric-1.21.1:assemble

# Run a development client or server
./gradlew :platforms:fabric-1.21.1:runClient
./gradlew :platforms:fabric-1.21.1:runServer
```

Result JARs go to `dist/`. `./gradlew clean` removes build outputs and `dist/`.


Tests run unit checks and Minecraft server scenarios. Run both profiles before a release:

```bash
./gradlew test --no-parallel --max-workers=2
./gradlew test -PtestProfile=configured --no-parallel --max-workers=2

# Test one platform
./gradlew :platforms:fabric-1.21.1:test --no-parallel --max-workers=2
```

The default profile checks default settings; `configured` checks modified settings.
Success means `BUILD SUCCESSFUL` and `PASS` for every selected platform in the final summary.
Server reports are in `platforms/<target>/build/reports/test/<profile>/results.xml`.
Client rendering still needs a manual check.

- [definitions/content.kt](definitions/content.kt) — weapons, model sizes, materials, and recipes.
- [definitions/translations.kt](definitions/translations.kt) — translations.
- [definitions/targets.kt](definitions/targets.kt) — supported platforms and release version.
- [definitions/mod.kt](definitions/mod.kt) — mod metadata.
- `resources/` — shared textures and models.
- `core/`, `minecraft/`, `loaders/`, `platforms/` — shared logic and version/loader-specific code.
- `build-logic/` — connects project definitions to Fantasy Build.

Resources are generated automatically during builds and Gradle game launches. To generate them separately, run `./gradlew generateResources`.

_P.S. I also want to note that part of this code was written using AI, which isn't something I'm proud of, but I'm tired of Minecraft development and constantly having to update the API for every new version. I’m familiar with 1.7.10, but I don’t want to delve deeply into how the new versions of Minecraft work, let alone three or more loaders at the same time. I tried to keep it under control as best I could. Sorry if there’s any garbage code or bad design choices here._
