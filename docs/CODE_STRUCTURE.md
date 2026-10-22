# Code structure — Iron's Spellbooks Fabric Port

Based on **Smooth Classes**' Marj4n conventions, with project-specific feature folders.

Java namespace: `org.marj4n.irons_spellbooks`.

## Project layout

- `src/main/java/org/marj4n/irons_spellbooks/` — Shared/server Java code
- `src/main/resources/` — Fabric mod metadata, mixins, assets and data

## Existing feature folders

- `src/main/java/org/marj4n/irons_spellbooks/`: `client/`, `equipment/`, `events/`, `fluid/`, `inventory/`, `ironlib/`, `network/`, `registry/`

## Rules

1. Keep `org.marj4n` lowercase and match Java packages to actual folders.
2. Entrypoints stay wired in `fabric.mod.json`; mixin config paths must continue to resolve.
3. Use `api/`, `compat/`, `config/`, `content/`, `integration/`, `mixin/`, `network/`, `registry/`, `runtime/` only when the mod needs them; no empty folder padding.
4. Keep gameplay behavior, mod IDs, public dependency versions, resource namespace and mixin target names unchanged in a layout-only refactor.
5. Shared/server code belongs under `src/main`; client code goes under `src/client` only if this Gradle project uses split source sets.
6. Run `python scripts/check_structure.py` before committing, then build with the repo's Gradle wrapper when dependencies are available.

**Upstream source boundary:** Do not move/rename `src/main/java/io/redspace/ironsspellbooks/**`. Those upstream fully-qualified names and game resources are required by the port.

**Known repository caveat:** Preserve upstream io.redspace.ironsspellbooks packages; only Marj4n-specific code belongs under org.marj4n.irons_spellbooks.
