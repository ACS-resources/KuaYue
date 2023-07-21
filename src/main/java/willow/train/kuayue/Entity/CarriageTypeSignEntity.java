package willow.train.kuayue.Entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
import willow.train.kuayue.init.BlockEntitiesInit;

import java.util.function.Function;

public class CarriageTypeSignEntity extends BlockEntity implements MenuProvider {
    CarriageTypeSignEditMenu ctsem;
    private FormattedCharSequence[] renderMessages;
    public static final int YELLOW = 14725893, YELLOW2 = 16776960, RED = 15216648, BLUE = 22220, BLACK = 789516;
    private static int color = YELLOW;
    private final Component[] messages = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
    private static final String[] name = new String[]{"a", "b", "c", "d", "e"};

    public CarriageTypeSignEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesInit.CARRIAGE_TYPE_SIGN.get(), pPos, pBlockState);
        renderMessages = new FormattedCharSequence[0];
        //fromFile(level);
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

        //this.markUpdated();
        return true;
    }

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

    @Override
    protected void saveAdditional(CompoundTag pTag) {

        for(int i = 0; i < 5; i++) {
            pTag.putString(name[i], messages[i].getContents());
            //data[i] = messages[i].getContents();
            //System.out.println(messages[i].getContents());
            //System.out.println("saving datas " + data[i] + " aaaabbbb");
            //System.out.println(data[i]);
        }
        pTag.putInt("Color", this.color);
        System.out.println("saving datas " + pTag.toString());

        super.saveAdditional(pTag);
    }

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

        System.out.println("loading datas " + pTag.toString());
    }

    private Component loadLine(String pLine) {
        Component component = this.deserializeTextSafe(pLine);
        return component;
    }

    private Component deserializeTextSafe(String pText) {
        try {
            Component component = Component.Serializer.fromJson(pText);
            if (component != null) {
                return component;
            }
        } catch (Exception exception) {
        }
        return TextComponent.EMPTY;
    }

    public FormattedCharSequence[] getRenderMessages(Function<Component, FormattedCharSequence> pMessageTransformer) {

        this.renderMessages = new FormattedCharSequence[messages.length];
        for(int i = 0; i < messages.length; ++i) {
            this.renderMessages[i] = pMessageTransformer.apply(messages[i]);
        }
        return this.renderMessages;
    }

    public int getColor() {
        return this.color;
    }

    public boolean setColor(int mark) {
        if (mark != this.getColor()) {
            this.color = mark;
            try {
                this.markUpdated();
            }catch (Exception e){}
            return true;
        } else {
            return false;
        }
    }

    public void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container." + Main.MOD_ID + "carriage_type_sign");
    }
}
