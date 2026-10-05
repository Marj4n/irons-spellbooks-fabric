# Verification — 3.16.3-fabric.1

Checked on 5 October 2026. Port author: **Marj4n**.

| Check | Result |
| --- | --- |
| Full Gradle build (compile, resources, JAR remap, sources remap) | PASS |
| Whole source compile | 1114 Java source files, zero errors |
| Mixin selectors, shadows, accessors and invocation targets | 163 checks, zero failures |
| Actual Mixin application against the Gradle-mapped game/dependencies | All 45 target classes transformed |
| Native packet channel | Deferred gameplay execution, decoding, invalid direction/ID/truncated payload rejection passed |
| Native fluid storage | Droplet/millibucket units, NBT matching, rollback, nested transactions, commit notification and extraction passed |
| Fabric Loader server launch | Dependency resolution and common mod initialization passed; vanilla stopped at EULA before loading a world |
| Production JAR | Valid ZIP; Java 17 bytecode; Fabric metadata; author Marj4n; upstream contributors retained |

The standalone protocol/fluid checks use real Minecraft, Fabric and Porting Lib classes. `gradle test` has no project test sources; the checks above are separate engineering checks.

Build verification used Gradle 9.7.1, Fabric Loom 1.14.3, JDK 21.0.2 and `--release 17`. Genuine Maven artifacts were mirrored for offline resolution in this execution environment. An environment-only Java agent disables Loom's Unix-domain-socket support probe, which this container blocks; it does not change game APIs or the mod. The shipped project uses normal repositories and does not require that agent on a normal local machine.

Client gameplay, rendering inside the game, server world generation, multiplayer gameplay and full-modpack compatibility have **not** been verified here. These are the next local tests; see the checklist in README.md. The early server launch does not establish a running server/world. Vanilla logs missing data fixers for custom entity types, and some Porting Lib client-only mixin warnings appear during a server launch; neither prevented common initialization.

Production JAR SHA-256: `49928fd568744fb213c05cef9f735bc7a06ed179818c9fa825e1c959713ba1f6`.

Upstream source: https://github.com/iron431/irons-spells-n-spellbooks/tree/cae63a6999e24ed3deaa012ecb8c262e0e377816
