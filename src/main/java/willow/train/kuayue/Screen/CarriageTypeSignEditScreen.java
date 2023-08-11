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
import willow.train.kuayue.BlockEntity.CarriageTypeSignEntity;
import willow.train.kuayue.Client.CarriageTypeSignEditMenu;
import willow.train.kuayue.Main;

public class CarriageTypeSignEditScreen extends AbstractContainerScreen<CarriageTypeSignEditMenu> {

    private static final Component[] TEXT = new Component[]{
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.type_chs"),  // 类型(中文) 0
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.type_chs_py"),  // 类型(拼音) 1
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.type_abbr"),  // 类型(缩写) 2
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.sub_type"),  // 子类 3
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.no"),  // 编号 4
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color"),  // 颜色label 5
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.predefine_color"),  // 预定义颜色 6
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.confirm"),  // 确认 7
            Component.translatable("container." + Main.MOD_ID + ".laquered_board_edit_menu.edit_color"),  // 编辑颜色 8
            Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.cancel"),  // 取消 9
            Component.translatable("container." + Main.MOD_ID + ".laquered_board_edit_menu.edit_text")  // 编辑字体 10
    };

    private boolean editColor = false;
    private static int editBoxWidth = 200;
    private static int editBoxHeight = 20;
    private static int offsetX = 70;
    private static int offsetY = 15;

    private CarriageTypeSignEntity entity;

    EditBox TypeChs, TypePinyin, TypeAbbr, SubType, No, Color;

    private Button[] buttons = new Button[3];

    private Button[] colorButtons = new Button[7];

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
        Color = new EditBox(minecraft.font, xPos, 45 - offsetY, editBoxWidth, editBoxHeight, TEXT[6]);

        TypeChs.setValue(entity.getMessage(0,false).getString());
        TypePinyin.setValue(entity.getMessage(1,false).getString());
        TypeAbbr.setValue(entity.getMessage(2,false).getString());
        SubType.setValue(entity.getMessage(3,false).getString());
        No.setValue(entity.getMessage(4,false).getString());
        Color.setValue(entity.getColor() + "");

        for(int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new Button(xPos + (i%2) * editBoxWidth / 2 + 5, 90 - offsetY + ((int)(((float)i)/2)) * 25,
                    editBoxWidth/2 - 10, editBoxHeight, predefineColorMapping(i), b -> {
                for(int bx = 0; bx < colorButtons.length; bx++) {
                    if(colorButtons[bx].equals(b)) {
                        Color.setValue(predefineColorMapping2(bx) + "");
                    }
                }
            });
        }

        /*
            这个位置是定义按钮逻辑的
         */
        for(int i = 0; i< 3; i++){
            buttons[i] = addRenderableWidget(new Button(xPos + i*editBoxWidth/3 + 5, 200, editBoxWidth/3 - 10, editBoxHeight, TEXT[i+7], b -> {
                if(b.equals(buttons[0])){
                    if(entity != null){
                        boolean error = false;
                        int c = entity.getColor();
                        if(Color.getValue().equals("")) Color.setValue(entity.getColor() + "");
                        try{
                            if(Color.getValue().startsWith("0x")) c = Integer.parseInt(Color.getValue().substring(2), 16);
                            else c = Integer.parseInt(Color.getValue());
                        }catch (Exception e){
                            error = true;
                        }
                        if(!error) {
                            entity.setColor(c);
                            entity.setMessages(new String[]{TypeChs.getValue(), TypePinyin.getValue(), TypeAbbr.getValue(), SubType.getValue(), No.getValue()});
                            entity.markUpdated();
                            onClose();
                        }
                    }
                } else if (b.equals(buttons[1])){
                    editColor = !editColor;
                    if(editColor){
                        b.setMessage(TEXT[10]);
                        removeTextPanel();
                        renderColorPanel();
                    } else {
                        b.setMessage(TEXT[8]);
                        removeColorPanel();
                        renderTextPanel();
                    }
                } else if (b.equals(buttons[2])) {
                    onClose();
                }
            }));
        }

        renderTextPanel();
    }

    public void renderTextPanel(){
        addRenderableWidget(TypeChs);
        addRenderableWidget(TypePinyin);
        addRenderableWidget(TypeAbbr);
        addRenderableWidget(SubType);
        addRenderableWidget(No);
    }

    public void removeTextPanel(){
        removeWidget(TypeChs);
        removeWidget(TypePinyin);
        removeWidget(TypeAbbr);
        removeWidget(SubType);
        removeWidget(No);
    }

    public void renderColorPanel() {
        addRenderableWidget(Color);
        for(Button b : colorButtons) {
            addRenderableWidget(b);
        }
    }

    public void removeColorPanel() {
        removeWidget(Color);
        for(Button b : colorButtons){
            removeWidget(b);
        }
    }

    public Component predefineColorMapping(int x){
        switch (x){
            case 0 -> {return Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color.yellow");}
            case 1 -> {return Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color.yellow_2");}
            case 2 -> {return Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color.red");}
            case 3 -> {return Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color.blue");}
            case 4 -> {return Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color.blue_2");}
            case 5 -> {return Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color.blue_3");}
            case 6 -> {return Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color.black");}
            default -> {return Component.translatable("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.color.yellow");}
        }
    }

    public int predefineColorMapping2(int x){
        switch (x){
            case 0 -> {return CarriageTypeSignEntity.YELLOW;}
            case 1 -> {return CarriageTypeSignEntity.YELLOW2;}
            case 2 -> {return CarriageTypeSignEntity.RED;}
            case 3 -> {return CarriageTypeSignEntity.BLUE;}
            case 4 -> {return CarriageTypeSignEntity.BLUE2;}
            case 5 -> {return CarriageTypeSignEntity.BLUE3;}
            case 6 -> {return CarriageTypeSignEntity.BLACK;}
            default -> {return CarriageTypeSignEntity.YELLOW;}
        }
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int windowWidth = this.getMinecraft().screen.width;
        int windowHeight = this.getMinecraft().getWindow().getHeight();

        int centreX = windowWidth/2;
        int centreY = windowHeight/2;

        if(!editColor) {
            this.font.draw(pPoseStack, TEXT[0], centreX - this.font.width(TEXT[0].getString()) / 2 - offsetX, 35 - offsetY, 0xFFFFFF);
            this.font.draw(pPoseStack, TEXT[1], centreX - this.font.width(TEXT[1].getString()) / 2 - offsetX, 70 - offsetY, 0xFFFFFF);
            this.font.draw(pPoseStack, TEXT[2], centreX - this.font.width(TEXT[2].getString()) / 2 - offsetX, 105 - offsetY, 0xFFFFFF);
            this.font.draw(pPoseStack, TEXT[3], centreX - this.font.width(TEXT[3].getString()) / 2 - offsetX, 140 - offsetY, 0xFFFFFF);
            this.font.draw(pPoseStack, TEXT[4], centreX - this.font.width(TEXT[4].getString()) / 2 - offsetX, 175 - offsetY, 0xFFFFFF);
        } else {
            this.font.draw(pPoseStack, TEXT[5], centreX - this.font.width(TEXT[5].getString()) / 2 - offsetX, 35 - offsetY, 0xFFFFFF);
            this.font.draw(pPoseStack, TEXT[6], centreX - this.font.width(TEXT[6].getString()) / 2 - offsetX, 80 - offsetY, 0xFFFFFF);
        }
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
