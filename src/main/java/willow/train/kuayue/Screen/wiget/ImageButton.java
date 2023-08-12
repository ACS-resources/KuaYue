package willow.train.kuayue.Screen.wiget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ImageButton extends Button {

    ResourceLocation image;
    private int imgU;
    private int imgV;

    private int width;
    private int height;

    private int imgWidth;

    private int imgHeight;
    public ImageButton(int pX, int pY, int width, int height, Component pMessage, OnPress pOnPress, ResourceLocation location, int imgU, int imgV, int imgWidth, int imgHeight) {
        super(pX, pY, width, height, pMessage, pOnPress);
        this.image = location;
        this.imgU = imgU;
        this.imgV = imgV;
        this.width = width;
        this.height = height;
        this.imgHeight = imgHeight;
        this.imgWidth = imgWidth;
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int mouseX, int mouseY, float partialTicks){
        if(!visible) return;

        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();

        int x = this.x;
        int y = this.y;

        if(!this.active){
            RenderSystem.setShaderColor(0.5f, 0.5f, 0.5f, 0.5f);
        }
        RenderSystem.setShaderTexture(0, this.image);

        drawTexture(this.x, this.y, 0, 0, width, height);
    }

    private void drawTexture(int x, int y, int textureX, int textureY, int width, int height){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(x, y, 0.0).uv(0, 0).endVertex();
        buffer.vertex(x, y + height, 0.0).uv(0, 1).endVertex();
        buffer.vertex(x + width, y + height, 0.0).uv(1, 1).endVertex();
        buffer.vertex(x + width, y, 0.0).uv(1, 0).endVertex();
        BufferUploader.drawWithShader(buffer.end());
    }
}
