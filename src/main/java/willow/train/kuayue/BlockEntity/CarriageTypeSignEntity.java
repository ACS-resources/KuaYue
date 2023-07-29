package willow.train.kuayue.BlockEntity;

import willow.train.kuayue.Blocks.Signs.TrainPanelBlock;
import willow.train.kuayue.Network.CarriageTypeSignUpdatePacket;
import willow.train.kuayue.Network.KuayueNetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import willow.train.kuayue.Client.CarriageTypeSignEditMenu;
import willow.train.kuayue.Main;
import willow.train.kuayue.Util.PanelTypes;
import willow.train.kuayue.init.BlockEntitiesInit;

import java.util.function.Function;

public class CarriageTypeSignEntity extends BlockEntity implements MenuProvider {
    CarriageTypeSignEditMenu ctsem;
    private FormattedCharSequence[] renderMessages;
    public static final int YELLOW = 14725893, YELLOW2 = 16776960, RED = 15216648, BLUE = 22220, BLUE2 = 45263,BLUE3 = 468326, BLACK = 789516;
    private int color = YELLOW;
    private final Component[] messages = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
    private static final String[] name = new String[]{"a", "b", "c", "d", "e"};

    /**
     * BlockEntity的构造体
     * @param pPos 方块位置
     * @param pBlockState 方块状态
     */

    public CarriageTypeSignEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesInit.CARRIAGE_TYPE_SIGN.get(), pPos, pBlockState);
        PanelTypes types = pBlockState.getValue(TrainPanelBlock.TYPE);
        switch (types){
            case P25B -> color = YELLOW2;
            case P25G -> color = RED;
            case P25Z -> color = BLUE2;
            case P25TA -> color = BLUE3;
            case P25TB -> color = BLUE3;
            case P25KA -> color = BLUE;
            case P25KB -> color = BLUE;
            default -> color = YELLOW;
        }
        renderMessages = new FormattedCharSequence[0];
    }

    public boolean setMessages(String[] messages){
        if(messages.length != 5){
            return false;
        }

        this.messages[0] = new TextComponent(messages[0]);
        this.messages[1] = new TextComponent(messages[1]);
        this.messages[2] = new TextComponent(messages[2]);
        this.messages[3] = new TextComponent(messages[3]);
        this.messages[4] = new TextComponent(messages[4]);

        return true;
    }


    /**
     * 这个类是创建对应的Menu的类，在存档启动时自动调用
     * @param pContainerId 窗体id
     * @param pInventory 物品栏
     * @param player 玩家
     * @return 返回一个Menu
     */
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player player){
        this.ctsem = new CarriageTypeSignEditMenu(pContainerId, pInventory, this, new SimpleContainerData(2));
        this.ctsem.setCtse(this);
        return ctsem;
    }

    public Component getMessage(int pIndex, boolean pFiltered) {
        return this.getMessages(pFiltered)[pIndex];
    }

    private Component[] getMessages(boolean pFiltered) {
        return this.messages;
    }


    /**
     * 这个方法用于向磁盘写入BlockEntity的额外数据，一般来说我们不需要调用，游戏会自行处理
     * 需要注意的是，这是一个ServerSide方法，因此无法直接访问到送给ClientSide处理的数据
     * 因此，在Network包内有一个Packet(包类)，CarriageTypeSignUpdatePacket，这个类是负责在Client与Server间传递数据的
     * 类里的handle()方法是服务器侧处理的方法
     * 在Network包中的KuayueNetworkHandler则是整个mod注册Packet的地方。每一个新的Packet在这里注册
     * @param pTag 传入的保存Tag
     */
    @Override
    protected void saveAdditional(CompoundTag pTag) {

        for(int i = 0; i < 5; i++) {
            pTag.putString(name[i], this.messages[i].getString());
        }
        pTag.putInt("Color", this.color);
        System.out.println("saving datas " + pTag.toString());

        super.saveAdditional(pTag);
    }


    /**
     * 这个方法用于从磁盘读取数据，一般来说我们不需要调用它
     * 只有经saveAdditional()方法保存过的有效数据才能被读取
     * @param pTag 接收数据的Tag
     */
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        this.color = pTag.getInt("Color");

        for(int i = 0; i < 5; i++) {
            if(pTag.contains(name[i])) {
                String s = pTag.getString(name[i]);
                messages[i] = new TextComponent(s);
            }
        }

        //System.out.println("loading datas " + pTag.toString());
    }


    /**
     * 这个方法是连接Renderer和Entity的类，Renderer从这里获得需要渲染的内容反馈到渲染中。
     * @param pMessageTransformer 传入的信息处理语句
     * @return 返回渲染器包装过的内容给处理器
     * @link renderer 在 BlockEntityRenderer 内
     */
    public FormattedCharSequence[] getRenderMessages(Function<Component, FormattedCharSequence> pMessageTransformer) {

        this.renderMessages = new FormattedCharSequence[this.messages.length];
        for(int i = 0; i < this.messages.length; ++i) {
            this.renderMessages[i] = pMessageTransformer.apply(this.messages[i]);
        }

        return this.renderMessages;
    }

    public int getColor() {
        return this.color;
    }

    public boolean setColor(int mark) {
        if (mark != this.getColor()) {
            this.color = mark;
            return true;
        } else {
            return false;
        }
    }


    /**
     * 这个方法用于在区块更新时调用
     * 在ClientSide和ServerSide间同步数据
     * KuayueNetworkHandler的调用即为同步子句
     * 在一切需要更新的地方，比如牌子内容的改变，都请调用之
     */
    public void markUpdated() {
        this.setChanged();
        KuayueNetworkHandler.sendToServer(new CarriageTypeSignUpdatePacket(this.getBlockPos(), this.messages[0].getString(), this.messages[1].getString(), this.messages[2].getString(), this.messages[3].getString(), this.messages[4].getString(), this.color));
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container." + Main.MOD_ID + "carriage_type_sign");
    }


    /**
     * 这个方法用以自动保存所需信息，只要像这样重写一次即可
     * @return updateTag
     */
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }


    /**
     * 这个方法是接收更新包用的，像这样重写即可
     * @return updatePacket 更新包裹
     */
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public int nextColor(){
        switch (color){
            case YELLOW -> {return YELLOW2;}
            case YELLOW2 -> {return RED;}
            case RED -> {return BLUE;}
            case BLUE -> {return BLUE2;}
            case BLUE2 -> {return BLUE3;}
            case BLUE3 -> {return BLACK;}
            case BLACK -> {return YELLOW;}
            default -> {return YELLOW;}
        }
    }
}
