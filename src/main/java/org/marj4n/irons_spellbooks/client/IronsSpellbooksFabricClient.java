package org.marj4n.irons_spellbooks.client;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.config.ClientConfigs;
import io.redspace.ironsspellbooks.setup.PacketDistributor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.fml.config.ModConfig;
import org.marj4n.irons_spellbooks.client.network.FabricChannelClient;
import io.redspace.ironsspellbooks.setup.ClientSetup;

/** Fabric client setup. */
@Environment(EnvType.CLIENT)
public final class IronsSpellbooksFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricChannelClient.initialize(PacketDistributor.channel());
        ForgeConfigRegistry.INSTANCE.register(IronsSpellbooks.MODID, ModConfig.Type.CLIENT,
                ClientConfigs.SPEC, "irons_spellbooks-client.toml");
        net.fabricmc.fabric.api.resource.ResourceManagerHelper.registerBuiltinResourcePack(
                IronsSpellbooks.id("legacy_dead_king_resource_pack"),
                net.fabricmc.loader.api.FabricLoader.getInstance().getModContainer(IronsSpellbooks.MODID).orElseThrow(),
                net.minecraft.network.chat.Component.literal("Legacy Dead King"),
                net.fabricmc.fabric.api.resource.ResourcePackActivationType.NORMAL);
        FluidClientSetup.register();
        ClientRuntimeHooks.register();
        org.marj4n.irons_spellbooks.client.item.NativeItemRendering.register();
        ClientSetup.registerDimensionEffects();
        ClientSetup.registerDyeables(net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry.ITEM);
        ClientSetup.onRegisterLayers();
        ClientSetup.registerMenuScreen();
        ClientSetup.replaceRenderers();
        ClientSetup.rendererRegister();
        ClientSetup.registerParticles(net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.getInstance());
        io.github.fabricators_of_create.porting_lib.event.client.EntityAddedLayerCallback.EVENT.register(ClientSetup::registerRenderers);
        net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin.register(context -> {
            org.marj4n.irons_spellbooks.client.item.NativePerspectiveModels.register(context);
            ClientSetup.registerSpecialModels(context);
            context.modifyModelAfterBake().register(ClientSetup::replaceItemModels);
        });
        ClientSetup.clientSetup();
        io.redspace.ironsspellbooks.registries.OverlayRegistry.register();
        io.redspace.ironsspellbooks.player.KeyMappings.onRegisterKeybinds();
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(
                client -> io.redspace.ironsspellbooks.player.ClientInputEvents.onClientTick());
        io.github.fabricators_of_create.porting_lib.event.client.MouseInputEvents.BEFORE_SCROLL.register(
                io.redspace.ironsspellbooks.player.ClientInputEvents::clientMouseScrolled);
        io.github.fabricators_of_create.porting_lib.event.client.InteractEvents.USE.register(
                (client, hit, hand) -> io.redspace.ironsspellbooks.player.ClientSpellCastHelper.shouldSuppressRightClicks()
                        ? net.minecraft.world.InteractionResult.FAIL : net.minecraft.world.InteractionResult.PASS);
        io.github.fabricators_of_create.porting_lib.event.client.InteractEvents.ATTACK.register(
                (client, hit) -> io.redspace.ironsspellbooks.player.ClientMagicData.isCasting()
                        ? net.minecraft.world.InteractionResult.FAIL : net.minecraft.world.InteractionResult.PASS);
    }
}
