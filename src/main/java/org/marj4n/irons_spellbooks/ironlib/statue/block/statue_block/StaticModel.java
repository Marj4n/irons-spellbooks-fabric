package org.marj4n.irons_spellbooks.ironlib.statue.block.statue_block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.state.BoneSnapshot;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

public class StaticModel
extends GeoModel<StaticModel.Instance> {
    private final ResourceLocation model;
    private final ResourceLocation texture;
    private static final ResourceLocation ANIMATION = new ResourceLocation((String)"irons_lib", (String)"animations/statue_poses.animation.json");

    public StaticModel(String modid, String name) {
        this(new ResourceLocation((String)modid, (String)String.format("geo/statue/%s.geo.json", name)), new ResourceLocation((String)modid, (String)String.format("textures/entity/statue/%s.png", name)));
    }

    public StaticModel(ResourceLocation model, ResourceLocation texture) {
        this.model = model;
        this.texture = texture;
    }

    public void render(PoseStack poseStack, Function<ResourceLocation, RenderType> renderType, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        this.render(poseStack, renderType.apply(this.texture), bufferSource, packedLight, packedOverlay);
    }

    public void render(PoseStack poseStack, RenderType renderType, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        VertexConsumer buffer = bufferSource.getBuffer(renderType);
        this.render(poseStack, buffer, packedLight, packedOverlay);
    }

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        BakedGeoModel model = this.getBakedModel(this.model);
        for (GeoBone group : model.topLevelBones()) {
            this.renderRecursively(poseStack, group, vertexConsumer, packedLight, packedOverlay);
        }
    }

    protected void renderRecursively(PoseStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        RenderUtils.prepMatrixForBone((PoseStack)poseStack, (CoreGeoBone)bone);
        if (!bone.isHidden()) {
            this.renderBone(poseStack, bone, buffer, packedLight, packedOverlay);
        }
        if (!bone.isHidingChildren()) {
            for (GeoBone childBone : bone.getChildBones()) {
                this.renderRecursively(poseStack, childBone, buffer, packedLight, packedOverlay);
            }
        }
        poseStack.popPose();
    }

    protected void renderBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay) {
        for (GeoCube cube : bone.getCubes()) {
            poseStack.pushPose();
            this.renderCube(poseStack, cube, buffer, packedLight, packedOverlay);
            poseStack.popPose();
        }
    }

    protected void renderCube(PoseStack poseStack, GeoCube cube, VertexConsumer buffer, int packedLight, int packedOverlay) {
        RenderUtils.translateToPivotPoint((PoseStack)poseStack, (GeoCube)cube);
        RenderUtils.rotateMatrixAroundCube((PoseStack)poseStack, (GeoCube)cube);
        RenderUtils.translateAwayFromPivotPoint((PoseStack)poseStack, (GeoCube)cube);
        Matrix3f normalisedPoseState = poseStack.last().normal();
        Matrix4f poseState = new Matrix4f((Matrix4fc)poseStack.last().pose());
        for (GeoQuad quad : cube.quads()) {
            if (quad == null) continue;
            Vector3f normal = normalisedPoseState.transform(new Vector3f((Vector3fc)quad.normal()));
            RenderUtils.fixInvertedFlatCube((GeoCube)cube, (Vector3f)normal);
            this.createVerticesOfQuad(quad, poseState, normal, buffer, packedLight, packedOverlay);
        }
    }

    protected void createVerticesOfQuad(GeoQuad quad, Matrix4f poseState, Vector3f normal, VertexConsumer buffer, int packedLight, int packedOverlay) {
        for (GeoVertex vertex : quad.vertices()) {
            Vector3f position = vertex.position();
            Vector4f vector4f = poseState.transform(new Vector4f(position.x(), position.y(), position.z(), 1.0f));
            buffer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), 1.0f, 1.0f, 1.0f, 1.0f, vertex.texU(), vertex.texV(), packedOverlay, packedLight, normal.x(), normal.y(), normal.z());
        }
    }

    public ResourceLocation getModelResource() {
        return this.model;
    }

    public ResourceLocation getTextureResource() {
        return this.texture;
    }

    public ResourceLocation getModelResource(Instance animatable) {
        return this.model;
    }

    public ResourceLocation getTextureResource(Instance animatable) {
        return this.texture;
    }

    public ResourceLocation getAnimationResource(Instance animatable) {
        return ANIMATION;
    }

    public static class Instance
    implements GeoAnimatable {
        public static final Instance INSTANCE = new Instance();
        AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

        public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        }

        public AnimatableInstanceCache getAnimatableInstanceCache() {
            return this.cache;
        }

        public double getTick(Object object) {
            return 0.0;
        }
    }
}

