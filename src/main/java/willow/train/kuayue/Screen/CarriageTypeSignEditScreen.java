package willow.train.kuayue.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import willow.train.kuayue.Client.CarriageTypeSignEditMenu;
import willow.train.kuayue.Entity.CarriageTypeSignEntity;
import willow.train.kuayue.Main;

public class CarriageTypeSignEditScreen extends AbstractContainerScreen<CarriageTypeSignEditMenu> {

    private static final Component[] TEXT = new Component[]{
            new TranslatableComponent("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.type_chs"),  // 类型(中文)
            new TranslatableComponent("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.type_chs_py"),  // 类型(拼音)
            new TranslatableComponent("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.type_abbr"),  // 类型(缩写)
            new TranslatableComponent("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.sub_type"),  // 子类
            new TranslatableComponent("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.no"),  // 编号
            new TranslatableComponent("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.confirm"),  // 确认
            new TranslatableComponent("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.cancel")  // 取消
    };

    private static int editBoxWidth = 200;
    private static int editBoxHeight = 20;
    private static int offsetX = 70;
    private static int offsetY = 15;

    private CarriageTypeSignEntity entity;

    EditBox TypeChs, TypePinyin, TypeAbbr, SubType, No;

    private Button[] buttons = new Button[2];

    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/gui/gui.png");

    /**
     * 在 Menu 中将方块实体传递进来
     * @param pMenu -
     * @param pPlayerInventory -
     * @param pTitle -
     */
    public CarriageTypeSignEditScreen(CarriageTypeSignEditMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        TypeChs = new EditBox(minecraft.font, xPos, 45 - offsetY, editBoxWidth, editBoxHeight, TEXT[0]);
        TypePinyin = new EditBox(minecraft.font, xPos, 80 - offsetY, editBoxWidth, editBoxHeight, TEXT[1]);
        TypeAbbr = new EditBox(minecraft.font, xPos, 115 - offsetY, editBoxWidth, editBoxHeight, TEXT[2]);
        SubType = new EditBox(minecraft.font, xPos, 150 - offsetY, editBoxWidth, editBoxHeight, TEXT[3]);
        No = new EditBox(minecraft.font, xPos, 185 - offsetY, editBoxWidth, editBoxHeight, TEXT[4]);

        TypeChs.setValue(entity.getMessage(0,false).getString());
        TypePinyin.setValue(entity.getMessage(1,false).getString());
        TypeAbbr.setValue(entity.getMessage(2,false).getString());
        SubType.setValue(entity.getMessage(3,false).getString());
        No.setValue(entity.getMessage(4,false).getString());

        addRenderableWidget(TypeChs);
        addRenderableWidget(TypePinyin);
        addRenderableWidget(TypeAbbr);
        addRenderableWidget(SubType);
        addRenderableWidget(No);

        /*
            这个位置是定义按钮逻辑的
         */
        for(int i = 0; i< 2; i++){
            buttons[i] = addRenderableWidget(new Button(xPos + i*editBoxWidth/2 + 5, 200, editBoxWidth/2 - 10, editBoxHeight, TEXT[i+5], b -> {
                if(b.equals(buttons[0])){
                    if(entity != null){
                        entity.setMessages(new String[]{TypeChs.getValue(), TypePinyin.getValue(), TypeAbbr.getValue(), SubType.getValue(), No.getValue()});
                        entity.markUpdated();
                        onClose();
                    }
                } else if (b.equals(buttons[1])){
                    onClose();
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
        int centreY = windowHeight/2;

        this.font.draw(pPoseStack,TEXT[0], centreX-this.font.width(TEXT[0].getString())/2 - offsetX, 35 - offsetY, 0xFFFFFF);
        this.font.draw(pPoseStack,TEXT[1], centreX-this.font.width(TEXT[1].getString())/2 - offsetX, 70 - offsetY, 0xFFFFFF);
        this.font.draw(pPoseStack,TEXT[2], centreX-this.font.width(TEXT[2].getString())/2 - offsetX, 105 - offsetY, 0xFFFFFF);
        this.font.draw(pPoseStack,TEXT[3], centreX-this.font.width(TEXT[3].getString())/2 - offsetX, 140 - offsetY, 0xFFFFFF);
        this.font.draw(pPoseStack,TEXT[4], centreX-this.font.width(TEXT[4].getString())/2 - offsetX, 175 - offsetY, 0xFFFFFF);
    }

    /**
     * 装载对应的方块实体
     * @param entity 装载实体
     */
    public void loadEntity(CarriageTypeSignEntity entity){
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
