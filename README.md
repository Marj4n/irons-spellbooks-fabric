# Iron's Spells 'n Spellbooks — Fabric

Fabric 1.20.1 port of Iron's Spells 'n Spellbooks 3.16.3: spells, equipment, crafting and world content. Fabric integration lives in `org.marj4n.irons_spellbooks`; upstream packages and content IDs are preserved.

## Build

Use JDK 21 with the included Gradle wrapper. The mod targets Java 17.

```sh
./gradlew build
```

Install the regular JAR from `build/libs/`. Required: Fabric API, Trinkets (and its Cardinal Components dependencies), GeckoLib, Player Animator, Forge Config API Port and Porting Lib. Exact versions are in `gradle.properties` and `fabric.mod.json`. JEI integration is optional. This port does not require the original Forge mod, Curios or IronLib.

## Source layout

`src/main/java` contains the port and preserved upstream code; `src/main/resources` contains game data and assets. Use `./gradlew runClient` for a development client. A successful build does not replace gameplay or dedicated-server testing.

## Credits and license

Fabric port by Marj4n. Original mod by Iron431 and Lab3, based on upstream commit `cae63a6999e24ed3deaa012ecb8c262e0e377816`. IronLib 2.1.0 functionality by Iron431 is adapted for statues, mining attributes and Chronicle integration; the milk texture originates from MinecraftForge. Original mod IDs, translations and source attribution are retained. See [LICENSE.md](LICENSE.md).
