package willow.train.kuayue.EntityModels;
// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import willow.train.kuayue.Main;


/**
 * 模型类，在Renderer中调用，记得在Client/ClientModEventSubscriber中加入注册
 * 在使用前，记得把ResourceLoaction中的namespace和path修改正确，如下例
 * 没有其他事的话其他不用改动
 * @param <T> -
 */
public class CarriageNoSignModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation CARRIAGE_NO_SIGN_MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(Main.MOD_ID, "textures/entity/carriage_no_sign_texture.png"), "main");
	private final ModelPart bb_main;

	public CarriageNoSignModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 5).addBox(-2.5F, -2.5F, -1.0F, 0.5F, 5.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(0, 5).addBox(2.0F, -2.5F, -1.0F, 0.5F, 5.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 0).addBox(-2.5F, -2.0F, -1.0F, 0.5F, 4.0F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(10, 11).addBox(2.0F, -2.0F, -1.0F, 0.5F, 4.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}