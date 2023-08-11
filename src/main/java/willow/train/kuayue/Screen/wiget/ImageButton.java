package willow.train.kuayue.Screen.wiget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
    public ImageButton(int pX, int pY, Component pMessage, OnPress pOnPress, ResourceLocation location, int imgU, int imgV) {
        super(pX, pY, 20, 20, pMessage, pOnPress);
        this.image = location;
        this.imgU = imgU;
        this.imgV = imgV;
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int mouseX, int mouseY, float partialTicks){
        if(!visible) return;

        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
        int offset = this.getYImage(this.isHoveredOrFocused());

        int x = this.x;
        int y = this.y;

        blit(pPoseStack, x, y, 0, 46 + offset *20, width / 2, height);
        blit(pPoseStack, x + width / 2, y, 200 - width / 2, 46 + offset * 20, width / 2, height);
        if(!this.active){
            RenderSystem.setShaderColor(0.5f, 0.5f, 0.5f, 0.5f);
        }
        RenderSystem.setShaderTexture(0, this.image);

        blit(pPoseStack, x + 2, y + 2, imgU, imgV, 16, 16);
    }
}
