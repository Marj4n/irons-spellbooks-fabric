package org.marj4n.irons_spellbooks;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.SpellConfigManager;
import io.redspace.ironsspellbooks.api.magic.MagicHelper;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicEvents;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.data.IronsDataStorage;
import io.redspace.ironsspellbooks.registries.*;
import io.redspace.ironsspellbooks.setup.CommonSetup;
import io.redspace.ironsspellbooks.setup.PacketDistributor;
import io.redspace.ironsspellbooks.worldgen.VillageAddition;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.fml.config.ModConfig;

/** Registers the Fabric port's content, events, configuration and networking. */
public final class IronsSpellbooksFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        IronsSpellbooks.MAGIC_MANAGER = new MagicManager();
        MagicHelper.MAGIC_MANAGER = IronsSpellbooks.MAGIC_MANAGER;

        org.marj4n.irons_spellbooks.ironlib.MiningSpeed.register();
        AttributeRegistry.register();
        SoundRegistry.register();
        SchoolRegistry.register();
        SpellRegistry.register();
        FluidRegistry.register();
        BlockRegistry.register();
        ItemRegistry.register();
        MenuRegistry.register();
        net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage.SIDED.registerForBlockEntity(
                (tile, side) -> tile.fabricFluidStorage, BlockRegistry.ALCHEMIST_CAULDRON_TILE.get());
        net.fabricmc.fabric.api.transfer.v1.item.ItemStorage.SIDED.registerForBlockEntity(
                (tile, side) -> net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage.of(tile.getItemHandler(), side),
                BlockRegistry.SCROLL_FORGE_TILE.get());
        EntityRegistry.register();
        MobEffectRegistry.register();
        ParticleRegistry.register();
        FeatureRegistry.register();
        LootRegistry.register();
        RecipeRegistry.register();
        StructureProcessorRegistry.register();
        StructureElementRegistry.register();
        PoiTypeRegistry.register();
        CreativeTabRegistry.register();
        CommandArgumentRegistry.register();
        UpgradeOrbTypeRegistry.registerDatapackRegistries();
        PotionRegistry.register();

        CommonSetup.registerEntityAttributes();
        CommonSetup.registerSpawnPlacements();
        ModConfigEvents.loading(IronsSpellbooks.MODID).register(CommonSetup::onModConfigLoadingEvent);
        ModConfigEvents.reloading(IronsSpellbooks.MODID).register(CommonSetup::onModConfigReloadingEvent);
        ForgeConfigRegistry.INSTANCE.register(IronsSpellbooks.MODID, ModConfig.Type.SERVER,
                ServerConfigs.SPEC, "irons_spellbooks-server.toml");

        PacketDistributor.register();
        io.redspace.ironsspellbooks.setup.ModSetup.init();
        io.redspace.ironsspellbooks.player.AdditionalWanderingTrades.addWanderingTrades();
        net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents.CUSTOM.register(
                (entity, tick) -> entity.hasEffect(MobEffectRegistry.ANGEL_WINGS.get()));
        org.marj4n.irons_spellbooks.events.ServerRuntimeHooks.register();
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(SpellConfigManager.INSTANCE);
        CommandRegistrationCallback.EVENT.register(CommandRegistry::onCommandsRegister);
        ServerTickEvents.END_WORLD_TICK.register(MagicEvents::onWorldTick);
        ServerLifecycleEvents.SERVER_STARTING.register(VillageAddition::addNewVillageBuilding);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            IronsSpellbooks.MCS = server;
            IronsSpellbooks.OVERWORLD = server.overworld();
            IronsDataStorage.init(server.overworld().getDataStorage());
        });
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(SpellConfigManager::onDatapackSync);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            IronsSpellbooks.MCS = null;
            IronsSpellbooks.OVERWORLD = null;
        });
    }
}
