# Iron’s Spells ’n Spellbooks — Fabric 1.20.1

Fabric port author: **Marj4n**. Original mod: Iron431 and Lab3. Upstream version: **3.16.3**. Fabric build: **3.16.3-fabric.1**.

## Open in IntelliJ IDEA

Extract the complete project into `D:\Development\minecraft\Irons Spellbooks Fabric`, then open that folder in IntelliJ. Import `build.gradle` as a Gradle project. Set the **Gradle JVM to Java 21**. The generated mod targets **Java 17 / Minecraft 1.20.1**.

Fabric integration is under `src/main/java/org/marj4n/irons_spellbooks`. Upstream source remains under `io/redspace/ironsspellbooks` to preserve public API names and content compatibility.

## Build

Use the path to your installed JDK 21 in `JAVA_HOME`; the path below is an example. The archive also includes a prebuilt normal JAR in `artifacts/`.

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21.0.12"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\gradlew clean build
```

Install the normal JAR from `build/libs`, together with the required Fabric dependencies below. The `-sources.jar` is for development.

```powershell
.\gradlew runClient
```

## Runtime dependencies

- Minecraft 1.20.1 and Fabric Loader 0.19.5 or newer.
- Fabric API 0.92.12+1.20.1.
- Trinkets 3.7.2, including its Cardinal Components dependencies.
- GeckoLib Fabric 4.8.2.
- Player Animator Fabric 1.0.2-rc1+1.20.
- Forge Config API Port Fabric 8.0.3.
- Porting Lib 2.3.15+1.20.1, using the complete distribution.
- JEI Fabric 15.2.0.22 is optional. Create recipes load when Create is installed.

The Forge versions of Iron’s Spellbooks, Curios and IronLib are not runtime dependencies of this port.

## Local testing

Start with a separate Fabric 1.20.1 profile containing the required dependencies. Check login, casting through scrolls/staff/spellbook, Trinkets slots, mana/HUD, spell wheel, armor rendering, scroll forge, arcane anvil, cauldron recipes and automation, summons, portals/pocket dimension, loot and structures. Then test in the complete modpack and on a dedicated server.

Client gameplay and complete-modpack compatibility require local testing. See `VERIFICATION.md` for completed engineering checks. Keep `latest.log` and any crash report when reporting an issue.

See `CREDITS.md` and the preserved upstream `LICENSE.md`.
