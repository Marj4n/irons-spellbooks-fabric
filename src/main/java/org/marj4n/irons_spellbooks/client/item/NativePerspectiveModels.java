package org.marj4n.irons_spellbooks.client.item;
import java.util.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.resources.model.*;
import net.fabricmc.fabric.api.client.model.loading.v1.*;
/** Native models for the separate inventory/held/head views in the upstream assets. */
public final class NativePerspectiveModels {
    private static final Map<ResourceLocation,Map<ItemDisplayContext,ResourceLocation>> MODELS=new HashMap<>();
    static {
        MODELS.put(new ResourceLocation("irons_spellbooks:obsidian_katana"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/obsidian_katana__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:hellrazor"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/hellrazor__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:decrepit_scythe"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/decrepit_scythe__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:druidic_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/druidic_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/druidic_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:netherite_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/netherite_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/netherite_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:blaze_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/blaze_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/blaze_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:keeper_flamberge"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/keeper_flamberge__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:spellbreaker"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/spellbreaker__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:legionnaire_flamberge"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/legionnaire_flamberge__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:evoker_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/evoker_spell_book__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:ice_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/ice_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/ice_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:claymore"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/claymore__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:diamond_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/diamond_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/diamond_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:misery"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/misery__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:amethyst_rapier"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/amethyst_rapier__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:twilight_gale"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/twilight_gale__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:villager_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/villager_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/villager_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:truthseeker"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/truthseeker__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:twilight_gale_throwing"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/twilight_gale_throwing__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:gold_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/gold_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/gold_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:necronomicon_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/necronomicon_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/necronomicon_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:rotten_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/rotten_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/rotten_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:cursed_doll_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/cursed_doll_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/cursed_doll_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:firebrand"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/firebrand__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:magehunter"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/magehunter__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:chronicle"),Map.of(ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/chronicle__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:boreal_blade"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/boreal_blade__gui")));
        MODELS.put(new ResourceLocation("irons_spellbooks:dragonskin_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/dragonskin_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/dragonskin_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:copper_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/copper_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/copper_spell_book__head")));
        MODELS.put(new ResourceLocation("irons_spellbooks:iron_spell_book"),Map.of(ItemDisplayContext.GUI,new ResourceLocation("irons_spellbooks:item/iron_spell_book__gui"),ItemDisplayContext.HEAD,new ResourceLocation("irons_spellbooks:item/iron_spell_book__head")));
    }
    public static void register(ModelLoadingPlugin.Context context){
        MODELS.values().forEach(views -> views.values().forEach(context::addModels));
        context.modifyModelAfterBake().register((model,bake) -> {
            if(bake.id() instanceof ModelResourceLocation id && id.getVariant().equals("inventory")) {
                var views=MODELS.get(new ResourceLocation(id.getNamespace(),id.getPath()));
                if(views!=null) return new PerspectiveModel(model,views,bake.loader());
            }
            return model;
        });
    }
    public static final class PerspectiveModel implements BakedModel {
        private final BakedModel base;
        private final Map<ItemDisplayContext,ResourceLocation> views;
        private final ModelBakery bakery;
        PerspectiveModel(BakedModel base,Map<ItemDisplayContext,ResourceLocation> views,ModelBakery bakery){this.base=base;this.views=views;this.bakery=bakery;}
        public BakedModel forContext(ItemDisplayContext mode){
            var id=views.get(mode); if(id==null)return base;
            return bakery.getBakedTopLevelModels().getOrDefault(id,base);
        }
        public java.util.List<net.minecraft.client.renderer.block.model.BakedQuad> getQuads(net.minecraft.world.level.block.state.BlockState state,net.minecraft.core.Direction side,net.minecraft.util.RandomSource random){return base.getQuads(state,side,random);}
        public boolean useAmbientOcclusion(){return base.useAmbientOcclusion();}
        public boolean isGui3d(){return base.isGui3d();}
        public boolean usesBlockLight(){return base.usesBlockLight();}
        public boolean isCustomRenderer(){return base.isCustomRenderer();}
        public net.minecraft.client.renderer.texture.TextureAtlasSprite getParticleIcon(){return base.getParticleIcon();}
        public net.minecraft.client.renderer.block.model.ItemTransforms getTransforms(){return base.getTransforms();}
        public net.minecraft.client.renderer.block.model.ItemOverrides getOverrides(){return base.getOverrides();}
    }
}
