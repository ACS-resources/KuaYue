package willow.train.kuayue.Blocks.TrainCarriage;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.BlockEntity.CarriageNoSignEntity;
import willow.train.kuayue.BlockEntity.CarriageTypeSignEntity;
import willow.train.kuayue.Blocks.Signs.TrainNoBlock;
import willow.train.kuayue.Blocks.Signs.TrainPanelBlock;
import willow.train.kuayue.Util.PanelTypes;
import willow.train.kuayue.Util.SideMirrorBlock;
import willow.train.kuayue.Util.UpperPanelTypes;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;
import willow.train.kuayue.init.MenuInit;

public class OrdinaryTrainUpperPanelBlock extends SideMirrorBlock {

    private static VoxelShape shape;
    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15, 16, 16, 16);
    protected static final VoxelShape WEST_AABB = Block.box(0, 0.0D, 0.0D, 1, 16.0D, 16.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1);
    protected static final VoxelShape EAST_AABB = Block.box(15, 0.0D, 0D, 16.0D, 16.0D, 16.0D);

    public OrdinaryTrainUpperPanelBlock(Properties p_52325_) {
        super(p_52325_);
    }


    public VoxelShape getShape(BlockState p_54372_, BlockGetter p_54373_, BlockPos p_54374_, CollisionContext p_54375_) {
        switch((Direction)p_54372_.getValue(FACING)) {
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
        if(pPlayer.getItemInHand(pHand).is(Items.PAPER.asItem())) {
            onPaper(pLevel, pPlayer, pHand, pHit, pState);
            if(pLevel.getBlockState(pPos).getBlock().equals(BlockInit.TRAIN_NO_BLOCK)){
                //NetworkHooks.openGui((ServerPlayer) pPlayer,(CarriageNoSignEntity) pLevel.getBlockEntity(pPos), pPos);
                //((TrainPanelBlock)pLevel.getBlockState(pPos).getBlock()).use(pLevel.getBlockState(pPos), pLevel, pPos, pPlayer, pHand, pHit);
                //((CarriageNoSignEntity)pLevel.getBlockEntity(pPos)).markUpdated();
            }
        }
        return InteractionResult.SUCCESS;
    }

    public void onPaper(Level pLevel, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, BlockState blockState){
        if(this.equals(BlockInit.PANEL_25B_ORIGINAL_TOP.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.P25BA), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25B_ORIGINAL_TOP_B.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.P25BB), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25G_ORIGINAL_TOP.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.P25G), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25Z_ORIGINAL_TOP.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.P25Z), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25K_ORIGINAL_TOP.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.P25K), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25T_ORIGINAL_TOP.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.P25TA), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25T_ORIGINAL_TOP_B.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.P25TB), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_XL25T_ORIGINAL_TOP.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.P25TC), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25_MARSHALLED_TOP.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.M25A), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_25_MARSHALLED_TOP_B.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.M25B), 2);
            return;
        }
        if(this.equals(BlockInit.PANEL_CR200J_MARSHALLED_TOP.get())) {
            pLevel.setBlock(pHit.getBlockPos(), BlockInit.TRAIN_NO_BLOCK.get().getStateForPlacement(new BlockPlaceContext(pLevel, pPlayer, pHand, ItemStack.EMPTY, pHit), blockState.getValue(HINGE), UpperPanelTypes.CR200J), 2);
            return;
        }
    }

    public @NotNull VoxelShape getVisualShape(BlockState p_48735_, BlockGetter p_48736_, BlockPos p_48737_, CollisionContext p_48738_) {
        return Shapes.empty();
    }

    public float getShadeBrightness(BlockState p_48731_, BlockGetter p_48732_, BlockPos p_48733_) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState p_48740_, BlockGetter p_48741_, BlockPos p_48742_) {
        return true;
    }
}
