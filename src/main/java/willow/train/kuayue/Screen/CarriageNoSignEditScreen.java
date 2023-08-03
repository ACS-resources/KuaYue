package willow.train.kuayue.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import willow.train.kuayue.BlockEntity.CarriageNoSignEntity;
import willow.train.kuayue.Client.CarriageNoSignEditMenu;
import willow.train.kuayue.Main;

public class CarriageNoSignEditScreen  extends AbstractContainerScreen<CarriageNoSignEditMenu> {
    private static final Component[] TEXT = new Component[]{
            Component.translatable("container." + Main.MOD_ID + ".carriage_no_sign_edit_menu.content"),  // 内容
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.confirm"),  // 确认
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.cancel"),  // 取消
            Component.translatable("container." + Main.MOD_ID + ".carriage_no_sign_edit_menu.hinge")  // 取消
    };

    private static int editBoxWidth = 200;
    private static int editBoxHeight = 20;
    private static int offsetX = 70;
    private static int offsetY = 15;

    private CarriageNoSignEntity entity;

    EditBox content;

    private Button[] buttons = new Button[3];

    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/gui/gui.png");

    /**
     * 在 Menu 中将方块实体传递进来
     * @param pMenu -
     * @param pPlayerInventory -
     * @param pTitle -
     */
    public CarriageNoSignEditScreen(CarriageNoSignEditMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        loadEntity(pMenu.getCtse());
    }

    /**
     * 加载静态 Container 的类，在游戏启动时自动加载
     */
    public void init(){
        int windowWidth = this.getMinecraft().screen.width;
        int centreX = windowWidth/2;

        int xPos = centreX-editBoxWidth/2 - offsetX;

        Minecraft minecraft = Minecraft.getInstance();
        content = new EditBox(minecraft.font, xPos, 150 - offsetY, editBoxWidth, editBoxHeight, TEXT[0]);

        content.setValue(entity.getMessage().getString());

        addRenderableWidget(content);

        /*
            这个位置是定义按钮逻辑的
         */
        for(int i = 0; i< 3; i++){
            buttons[i] = addRenderableWidget(new Button(xPos + i*editBoxWidth/3 + 5, 200, editBoxWidth/3 - 10, editBoxHeight, TEXT[i + 1], b -> {
                if(b.equals(buttons[0])){

                    if(entity != null){
                        entity.setMessage(Component.translatable(content.getValue()));
                        //KuayueNetworkHandler.sendToServer(new CarriageNoSignUpdatePacket(entity.getBlockPos(), new TextComponent(content.getValue()), entity.getColor(), entity.isLeftSide()));
                        entity.markUpdated();
                        onClose();
                    }
                } else if (b.equals(buttons[1])){
                    onClose();
                } else if (b.equals(buttons[2])) {
                    if(entity != null) {
                        entity.setLeftSide(!entity.isLeftSide());
                        entity.markUpdated();
                    }
                }
            }));
        }

    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int windowWidth = this.getMinecraft().screen.width;
        int windowHeight = this.getMinecraft().getWindow().getHeight();

        int centreX = windowWidth/2;

        this.font.draw(pPoseStack,TEXT[0], centreX-this.font.width(TEXT[0].getString())/2 - offsetX, 35 - offsetY, 0xFFFFFF);
    }

    /**
     * 装载对应的方块实体
     * @param entity 装载实体
     */
    public void loadEntity(CarriageNoSignEntity entity){
        this.entity = entity;
    }

    /**
     * 这个方法用于渲染背景
     * @param pPoseStack -
     * @param pPartialTick -
     * @param pMouseX -
     * @param pMouseY -
     */
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    public int offset(int x){
        return x+(Minecraft.getInstance().getWindow().getWidth()-imageWidth)/2+ offsetX;
    }

    public boolean isPauseScreen(){
        return true;
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        if(pKeyCode == 69) return false;
        return super.keyReleased(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if(pKeyCode == 69) return false;
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}
