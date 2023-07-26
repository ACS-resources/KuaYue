package willow.train.kuayue.Screen;

import Network.CarriageTypeSignUpdatePacket;
import Network.KuayueNetworkHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
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
            new TranslatableComponent("container." + Main.MOD_ID + ".carriage_type_sign_edit_menu.no")  // 编号
    };

    private static int editBoxWidth = 300;
    private static int editBoxHeight = 20;
    private static int offset = 20;

    private CarriageTypeSignEntity entity;

    EditBox TypeChs, TypePinyin, TypeAbbr, SubType, No;

    private Button[] buttons = new Button[2];

    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/gui/gui.png");

    public CarriageTypeSignEditScreen(CarriageTypeSignEditMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        loadEntity(pMenu.getCtse());
    }

    public void init(){
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        Minecraft minecraft = Minecraft.getInstance();
        TypeChs = new EditBox(minecraft.font, 100, 45, editBoxWidth, editBoxHeight, TEXT[0]);
        TypePinyin = new EditBox(minecraft.font, 100, 80, editBoxWidth, editBoxHeight, TEXT[1]);
        TypeAbbr = new EditBox(minecraft.font, 100, 115, editBoxWidth, editBoxHeight, TEXT[2]);
        SubType = new EditBox(minecraft.font, 100, 150, editBoxWidth, editBoxHeight, TEXT[3]);
        No = new EditBox(minecraft.font, 100, 185, editBoxWidth, editBoxHeight, TEXT[4]);

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

        for(int i = 0; i< 2; i++){
            buttons[i] = addRenderableWidget(new Button(100, 220+i*30, editBoxWidth, editBoxHeight, TEXT[i], b -> {
                if(b.equals(buttons[0])){
                    if(entity != null){
                        entity.setMessages(new String[]{TypeChs.getValue(), TypePinyin.getValue(), TypeAbbr.getValue(), SubType.getValue(), No.getValue()});
                        KuayueNetworkHandler.sendToServer(new CarriageTypeSignUpdatePacket(this.entity.getBlockPos(), TypeChs.getValue(), TypePinyin.getValue(), TypeAbbr.getValue(), SubType.getValue(), No.getValue()));
                        //entity.getLevel().getChunk(entity.getBlockPos()).getBlockEntityNbtForSaving(entity.getBlockPos());
                        //entity.setData(new String[]{TypeChs.getValue(), TypePinyin.getValue(), TypeAbbr.getValue(), SubType.getValue(), No.getValue()});
                        onClose();
                    }
                } else if (b.equals(buttons[1])){
                    onClose();
                }
            }));
        }
    }

    public void loadEntity(CarriageTypeSignEntity entity){
        this.entity = entity;
    }

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
        return x+(Minecraft.getInstance().getWindow().getWidth()-imageWidth)/2+offset;
    }

    public boolean isPauseScreen(){
        return true;
    }
}
