package willow.train.kuayue.BlockEntity.BlockEntityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.BlockEntity.CarriageNoSignEntity;
import willow.train.kuayue.EntityModels.CarriageNoSignModel;

// 渲染器类

public class CarriageNoSignRenderer implements BlockEntityRenderer<CarriageNoSignEntity> {
    private final CarriageNoSignModel model; //模型

    /**
     * 为了调用到我们的模型，这个构造体应该改成这个样子
     * 调用 BlockEntityRendererProvider.Context pContext
     * 之后获得模型就像下面这样写就好了
     * @param pContext -
     */
    public CarriageNoSignRenderer(BlockEntityRendererProvider.Context pContext){
        model = new CarriageNoSignModel(pContext.bakeLayer(CarriageNoSignModel.CARRIAGE_NO_SIGN_MODEL_LAYER));
    }

    /**
     * render() 方法负责渲染，这里的
     * this.model.render()...这个方法负责渲染我们的模型
     * @param pBlockEntity -
     * @param pPartialTick -
     * @param pPoseStack -
     * @param pBufferSource -
     * @param pPackedLight -
     * @param pPackedOverlay -
     */
    @Override
    public void render(CarriageNoSignEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        //这句话是渲染模型用的
        this.model.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entitySolid(CarriageNoSignModel.CARRIAGE_NO_SIGN_MODEL_LAYER.getModel())), pPackedLight, pPackedOverlay);

    }

    @Override
    public boolean shouldRenderOffScreen(CarriageNoSignEntity pBlockEntity) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(pBlockEntity);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public boolean shouldRender(CarriageNoSignEntity pBlockEntity, Vec3 pCameraPos) {
        return BlockEntityRenderer.super.shouldRender(pBlockEntity, pCameraPos);
    }
}
