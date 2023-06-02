package willow.train.kuayue.renderer;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.Blocks.Signs.CarriageTypeSignBlock;
import willow.train.kuayue.Entity.CarriageTypeSignEntity;

import java.util.List;

public class CarriageTypeSignRenderer implements BlockEntityRenderer<CarriageTypeSignEntity> {

    private final Font font;

    public CarriageTypeSignRenderer(BlockEntityRendererProvider.Context pContext) {
        this.font = pContext.getFont();
    }

    @Override
    public boolean shouldRenderOffScreen(CarriageTypeSignEntity pBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 512;
    }

    @Override
    public boolean shouldRender(CarriageTypeSignEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public void render(CarriageTypeSignEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState blockstate = pBlockEntity.getBlockState();
        pPoseStack.pushPose();

        FormattedCharSequence[] aformattedcharsequence = pBlockEntity.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (p_173653_) -> {
            List<FormattedCharSequence> list = this.font.split(p_173653_, 90);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        Direction direction = blockstate.getValue(CarriageTypeSignBlock.FACING);
        switch (direction){
            case EAST :
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees(270));
                pPoseStack.translate(0.5D, 0.5, -1.92);
                pPoseStack.translate(-1.0D, 0, 0);
                pPoseStack.scale(0.010416667F*2f, -0.010416667F*3.0f, 0.010416667F*2f);// size参数
                renderText(aformattedcharsequence, 0, 0, 0, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                pPoseStack.translate(-0.15F*(-getOffset(aformattedcharsequence, 1)), 9.0, 0.0);
                pPoseStack.scale(1.2f/2f, 1.2f/3.0f, 1.2f/2f);// size参数
                renderText(aformattedcharsequence, 1, 0, 0, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                pPoseStack.translate(0.10416667F*(getOffset(aformattedcharsequence, 1)) + 70.0D, -20.0D, 0.0);
                pPoseStack.scale(4.6f/1.2f, 4.6f/1.2f, 4.6f/1.2f);// size参数
                renderText(aformattedcharsequence, 2, 0, 0, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                pPoseStack.translate(12.0D, 4.0D, 0.0);
                pPoseStack.scale(1.6f/4.6f, 1.6f/4.6f, 1.6f/4.6f);// size参数
                renderText(aformattedcharsequence, 3, 0, 0, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                pPoseStack.translate(24.0D, -10.0D, 0.0);
                pPoseStack.scale(4.0f/1.6f, 4.0f/1.6f, 4.0f/1.6f);// size参数
                renderText(aformattedcharsequence, 4, 0, 0, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                break;
            case NORTH :
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees(0));
                pPoseStack.translate(0.5D, (double)0.5F, (double)0.0F);
                pPoseStack.scale(0.010416667F*2, -0.010416667F*2, 0.010416667F*2);// size参数
                renderText(aformattedcharsequence, 0, -1, 0, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);// size参数
                renderText(aformattedcharsequence, 1, -1, -1, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                break;
            case SOUTH :
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
                pPoseStack.translate(-0.5D, (double)0.5F, (double)-1.0F);
                pPoseStack.scale(0.010416667F*2, -0.010416667F*2, 0.010416667F*2);// size参数
                renderText(aformattedcharsequence, 0, -1, 0, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);// size参数
                renderText(aformattedcharsequence, 1, -1, -1, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                break;
            case WEST :
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                pPoseStack.translate(-0.5D, (double)0.5F, (double)0.0F);
                pPoseStack.scale(0.010416667F*2, -0.010416667F*2, 0.010416667F*2);// size参数
                renderText(aformattedcharsequence, 0, -1, 0, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);// size参数
                renderText(aformattedcharsequence, 1, -1, -1, pBlockEntity, pPoseStack, pBufferSource, pPackedLight);
                break;
        }
        /*
        FormattedCharSequence[] aformattedcharsequence = pBlockEntity.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (p_173653_) -> {
            List<FormattedCharSequence> list = this.font.split(p_173653_, 90);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        pPoseStack.scale(0.010416667F*2, -0.010416667F*2, 0.010416667F*2);// size参数
        int i = getDarkColor(pBlockEntity)*2; // 这是控制亮度的参数
        int l = pPackedLight;

        for(int i1 = 0; i1 < 4; ++i1) {
            FormattedCharSequence formattedcharsequence = aformattedcharsequence[i1];
            float f3 = (float)(-this.font.width(formattedcharsequence) / 2);
                //this.font.drawInBatch8xOutline(formattedcharsequence, f3, (float)(i1 * 10 - 20), k, i, pPoseStack.last().pose(), pBufferSource, l);

            this.font.drawInBatch(formattedcharsequence, f3, (float)(i1 * 10 - 20), i, false, pPoseStack.last().pose(), pBufferSource, false, 0, l);
        }

         */
        pPoseStack.popPose();
    }

    private void renderText(FormattedCharSequence[] aformattedcharsequence, int index,int pX, int pY, CarriageTypeSignEntity pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight){
        FormattedCharSequence formattedcharsequence = aformattedcharsequence[index];
        this.font.drawInBatch(formattedcharsequence, pX, pY, getDarkColor(pBlockEntity)*2, false, pPoseStack.last().pose(), pBufferSource, false, 0, pPackedLight);
    }

    private float getOffset(FormattedCharSequence[] aformattedcharsequence, int index){
        FormattedCharSequence formattedcharsequence = aformattedcharsequence[index];
        return (float)(-this.font.width(formattedcharsequence) / 2);
    }

    private static int getDarkColor(CarriageTypeSignEntity pBlockEntity) {
        int i = pBlockEntity.getColor().getTextColor();
        double d0 = 0.4D;
        int j = (int)((double) NativeImage.getR(i) * 0.4D);
        int k = (int)((double)NativeImage.getG(i) * 0.4D);
        int l = (int)((double)NativeImage.getB(i) * 0.4D);
        //return i == DyeColor.BLACK.getTextColor() && pBlockEntity.hasGlowingText() ? -988212 : NativeImage.combine(0, l, k, j);
    return 0xc0b305;
    }
}
