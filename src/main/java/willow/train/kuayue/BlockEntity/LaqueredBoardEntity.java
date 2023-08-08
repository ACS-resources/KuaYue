package willow.train.kuayue.BlockEntity;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.Main;
import willow.train.kuayue.init.BlockEntitiesInit;

import java.io.IOException;
import java.util.function.Function;

public class LaqueredBoardEntity extends BlockEntity implements MenuProvider {

    private final Component[] messages = new Component[]{Component.literal("原   神"), Component.literal("启   动"), Component.literal("YUAN   SHEN"), Component.literal("QI     DONG"), Component.literal("特          快"), Component.literal("K114/514")};

    private FormattedCharSequence[] renderMessages;

    public static final float WIDTH = 1.0f, HEIGHT = 0.2f; // 水牌的宽高
    public static final Component BELT = Component.literal("■"); // 充当丝带和背景板的占位符
    public static final int NUMBER_OF_TYPES = 1; // 水牌的种类数

    //public static final SimpleTexture LOGO = new SimpleTexture(new ResourceLocation(Main.MOD_ID, "textures/laquered_board/laquered_board_logo.png")); // 国铁标志

    private int backGroundColor = 15216648, forGroundColor = 0x0;// 背景色和前景色
    private int type = 0; // 当前处于的种类

    public LaqueredBoardEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

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
        return null;
    }

    public int getBackGroundColor(){
        return backGroundColor;
    }

    public int getForGroundColor(){
        return forGroundColor;
    }

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
}
