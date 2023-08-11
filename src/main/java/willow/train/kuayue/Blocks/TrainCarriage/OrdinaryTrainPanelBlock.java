package willow.train.kuayue.Blocks.TrainCarriage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import willow.train.kuayue.BlockEntity.CarriageTypeSignEntity;
import willow.train.kuayue.BlockEntity.LaqueredBoardEntity;
import willow.train.kuayue.Blocks.Signs.TrainPanelBlock;
import willow.train.kuayue.Screen.CarriageTypeSignEditScreen;
import willow.train.kuayue.Util.PanelTypes;
import willow.train.kuayue.Util.SideMirrorBlock;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;
import willow.train.kuayue.init.MenuInit;

public class OrdinaryTrainPanelBlock extends SideMirrorBlock {

    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape SOUTH_AABB = Block.box(0, 0, 15, 16, 16, 17);
    protected static final VoxelShape EAST_AABB = Block.box(15, 0, 0, 17, 16, 16);
    protected static final VoxelShape NORTH_AABB = Block.box(0, 0, -1, 16, 16, 1);
    protected static final VoxelShape WEST_AABB = Block.box(-1, 0, 0, 1, 16, 16);

    public OrdinaryTrainPanelBlock(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState p_54372_, BlockGetter p_54373_, BlockPos p_54374_, CollisionContext p_54375_) {
        switch ((Direction) p_54372_.getValue(FACING)) {
            case NORTH:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case WEST:
                return WEST_AABB;
            case EAST:
            default:
                return EAST_AABB;
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide) return InteractionResult.SUCCESS;
        if(pPlayer.getItemInHand(pHand).is(ItemInit.Brush.get())) {
            onBrush(pLevel, pPlayer, pHand, pHit, pState);
            Minecraft minecraft = Minecraft.getInstance();
            //minecraft.setScreen(new CarriageTypeSignEditScreen(MenuInit.CARRIAGE_TYPE_SIGN_EDIT_MENU.get().create(
                    //0, pPlayer.getInventory()).setCtse((CarriageTypeSignEntity) pLevel.getBlockEntity(pPos)),
                    //pPlayer.getInventory(), Component.empty()));
        }
        if(pPlayer.getItemInHand(pHand).is(ItemInit.LaqueredBoard.get())) {
            onLaqueredBoard(pLevel, pPlayer, pHand, pHit, pState);
        }
        return InteractionResult.SUCCESS;
    }

    public void onBrush(Level pLevel, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, BlockState blockState){
        if(this.equals(BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25B, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25G_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25G, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25Z_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25Z, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25KA, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_ORIGINAL_LINE.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25KB, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_ORIGINAL_SYMBOL.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25KC, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25T_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25TA, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25T_ORIGINAL_BOTTOM_B.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25TB, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25B_MARSHALLED_SYMBOL.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25B, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25G_MARSHALLED_SYMBOL.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25G, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_MARSHALLED_SYMBOL.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25KA, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25_MARSHALLED_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25Z, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25_MARSHALLED_BOTTOM_LINE.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25T, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_CR200J_MARSHALLED_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25KB, blockState.getValue(HINGE)), 2);
            return;
        }
    }

    public void onLaqueredBoard(Level pLevel, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, BlockState blockState){
        if(this.equals(BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25B, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25G_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25G, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25Z_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25Z, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25KA, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_ORIGINAL_LINE.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25KB, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_ORIGINAL_SYMBOL.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25KC, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25T_ORIGINAL_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25TA, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25T_ORIGINAL_BOTTOM_B.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.P25TB, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25B_MARSHALLED_SYMBOL.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25B, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25G_MARSHALLED_SYMBOL.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25G, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_MARSHALLED_SYMBOL.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25KA, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25_MARSHALLED_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25Z, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25_MARSHALLED_BOTTOM_LINE.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25T, blockState.getValue(HINGE)), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_CR200J_MARSHALLED_BOTTOM.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.LAQUERED_BOARD_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), PanelTypes.M25KB, blockState.getValue(HINGE)), 2);
            return;
        }
    }
}
