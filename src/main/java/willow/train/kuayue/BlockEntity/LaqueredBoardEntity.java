package willow.train.kuayue.BlockEntity;

import com.mojang.blaze3d.platform.NativeImage;
import com.simibubi.create.content.equipment.clipboard.ClipboardCloneable;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.Client.LaqueredBoardEditMenu;
import willow.train.kuayue.Main;
import willow.train.kuayue.Network.KuayueNetworkHandler;
import willow.train.kuayue.Network.LaqueredBoardPacket;
import willow.train.kuayue.init.BlockEntitiesInit;

import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class LaqueredBoardEntity extends SmartBlockEntity implements MenuProvider, ClipboardCloneable {

    private final Component[] messages = new Component[]{Component.empty(), Component.empty(), Component.empty(), Component.empty(),
            Component.empty(), Component.empty(), Component.empty(), Component.empty(), Component.empty(), Component.empty(),
            Component.empty(), Component.empty(), Component.empty(), Component.empty(), Component.empty(), Component.empty()};
    // 一共预留了16个数据槽位
    private FormattedCharSequence[] renderMessages;

    public static final float WIDTH = 1.0f, HEIGHT = 0.2f; // 水牌的宽高
    public static final Component BELT = Component.literal("■"); // 充当丝带和背景板的占位符
    public static final int NUMBER_OF_TYPES = 1; // 水牌的种类数

    private double x_offset = 0;
    private int backGroundColor = 15216648, forGroundColor = 0x0, beltForGroundColor = 0xffffff;// 背景色和前景色
    private int type = 0; // 当前处于的种类

    public LaqueredBoardEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    public LaqueredBoardEntity(BlockPos pPos, BlockState pBlockState) {
        this(BlockEntitiesInit.LAQUERED_BOARD.get(), pPos, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        LaqueredBoardEditMenu menu = new LaqueredBoardEditMenu(pContainerId, pPlayerInventory, this, new SimpleContainerData(2));
        menu.setLbe(this);
        return menu;
    }

    public void write(CompoundTag tag, boolean clientPacket){
        super.write(tag, clientPacket);
        tag.putInt("type", type);
        for(int i = 0; i < messages.length; i++){
            tag.putString("content " + i, messages[i].getString());
        }
        tag.putInt("beltColor", backGroundColor);
        tag.putInt("textColor", forGroundColor);
        tag.putInt("pinyinColor", beltForGroundColor);
        tag.putDouble("x_offset", x_offset);
    }

    public void read(CompoundTag tag, boolean clientPacket){
        super.read(tag, clientPacket);
        this.type = tag.getInt("type");
        for(int i = 0; i < messages.length; i++){
            messages[i] = Component.literal(tag.getString("content " + i));
        }
        this.backGroundColor = tag.getInt("beltColor");
        this.forGroundColor = tag.getInt("textColor");
        this.beltForGroundColor = tag.getInt("pinyinColor");
        this.x_offset = tag.getDouble("x_offset");
    }

    public double getXOffset(){
        return x_offset;
    }

    public void setXOffset(double offset){
        this.x_offset = offset;
    }

    public void setMessages(String[] messages){
        int length = (messages.length > this.messages.length) ? this.messages.length : messages.length;
        for(int i = 0; i< length; i++){
            this.messages[i] = Component.literal(messages[i]);
        }
    }

    public void setColors(int... colors){
        if(colors.length != 3) {Main.LOGGER.error("Incompatible color number!");return;}
        this.backGroundColor = colors[0];
        this.forGroundColor = colors[1];
        this.beltForGroundColor = colors[2];
    }

    public Component getMessage(int index){
        return this.messages[index];
    }

    public int getBackGroundColor(){
        return backGroundColor;
    }

    public int getForGroundColor(){
        return forGroundColor;
    }

    public int getBeltForGroundColor() {return beltForGroundColor;}

    public FormattedCharSequence[] getRenderMessages(Function<Component, FormattedCharSequence> pMessageTransformer) {

        this.renderMessages = new FormattedCharSequence[this.messages.length + 1];
        this.renderMessages[0] = pMessageTransformer.apply(BELT);
        for(int i = 1; i - 1 < this.messages.length; ++i) {
            this.renderMessages[i] = pMessageTransformer.apply(this.messages[i - 1]);
        }
        return this.renderMessages;
    }

    public int getBoardType(){
        return type;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void markUpdated(){
        this.setChanged();
        KuayueNetworkHandler.sendToServer(new LaqueredBoardPacket(this.getBlockPos(), this.messages, this.x_offset, this.backGroundColor, this.forGroundColor, this.beltForGroundColor));
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public String getClipboardKey() {
        return "laquered_board";
    }

    @Override
    public boolean writeToClipboard(CompoundTag tag, Direction side) {
        tag.putString("sign", "laquered_board");
        tag.putInt("type", type);
        for(int i = 0; i < messages.length; i++){
            tag.putString("content " + i, messages[i].getString());
        }
        tag.putInt("beltColor", backGroundColor);
        tag.putInt("textColor", forGroundColor);
        tag.putInt("pinyinColor", beltForGroundColor);
        tag.putDouble("x_offset", x_offset);
        return true;
    }

    @Override
    public boolean readFromClipboard(CompoundTag tag, Player player, Direction side, boolean simulate) {
        if(simulate) return true;
        if(tag.contains("sign") && tag.contains("type") && tag.contains("beltColor") && tag.contains("textColor") && tag.contains("pinyinColor") && tag.contains("x_offset")){
            if(!tag.getString("sign").equals("laquered_board")) return false;
            for(int i = 0; i < this.messages.length; i++){
                if(!tag.contains("content " + i)) return false;
            }
            this.type = tag.getInt("type");
            for(int i = 0; i < messages.length; i++){
                messages[i] = Component.literal(tag.getString("content " + i));
            }
            this.backGroundColor = tag.getInt("beltColor");
            this.forGroundColor = tag.getInt("textColor");
            this.beltForGroundColor = tag.getInt("pinyinColor");
            this.x_offset = tag.getDouble("x_offset");
            markUpdated();
            return true;
        }
        return false;
    }

    public int[] getRGBAColor(int octColor){
        int[] result = new int[4];
        Color color = new Color(octColor);
        result[0] = color.getRed();
        result[1] = color.getGreen();
        result[2] = color.getBlue();
        result[3] = color.getAlpha();
        return result;
    }
}
