package willow.train.kuayue.Blocks.Signs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.BlockEntity.CarriageNoSignEntity;
import willow.train.kuayue.Network.CarriageNoSignUpdatePacket;
import willow.train.kuayue.Network.KuayueNetworkHandler;
import willow.train.kuayue.Util.PanelTypes;
import willow.train.kuayue.Util.UpperPanelTypes;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;

public class TrainNoBlock extends KuayueSignBlock {

    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final EnumProperty<UpperPanelTypes> TYPE = EnumProperty.create("type", UpperPanelTypes.class);

    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape SOUTH_AABB = Block.box(0, 0, 15, 16, 16, 17);
    protected static final VoxelShape EAST_AABB = Block.box(15, 0, 0, 17, 16, 16);
    protected static final VoxelShape NORTH_AABB = Block.box(0, 0, -1, 16, 16, 1);
    protected static final VoxelShape WEST_AABB = Block.box(-1, 0, 0, 1, 16, 16);

    public TrainNoBlock(Properties p_49795_) {
        super(p_49795_);
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(HINGE, this.getHinge(context)).setValue(TYPE, UpperPanelTypes.P25BA);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context, DoorHingeSide hinge, UpperPanelTypes types) {
        return super.getStateForPlacement(context).setValue(HINGE, hinge).setValue(TYPE, types);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CarriageNoSignEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pState) {
        super.createBlockStateDefinition(pState);
        pState.add(HINGE, TYPE);
    }

    private DoorHingeSide getHinge(BlockPlaceContext pContext) {
        BlockGetter blockgetter = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockpos1 = blockpos.above();
        Direction direction1 = direction.getCounterClockWise();
        BlockPos blockpos2 = blockpos.relative(direction1);
        BlockState blockstate = blockgetter.getBlockState(blockpos2);
        BlockPos blockpos3 = blockpos1.relative(direction1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos3);
        Direction direction2 = direction.getClockWise();
        BlockPos blockpos4 = blockpos.relative(direction2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos4);
        BlockPos blockpos5 = blockpos1.relative(direction2);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos5);
        int i = (blockstate.isCollisionShapeFullBlock(blockgetter,
                blockpos2) ? -1 : 0) + (blockstate1.isCollisionShapeFullBlock(blockgetter, blockpos3) ? -1 : 0)
                + (blockstate2.isCollisionShapeFullBlock(blockgetter, blockpos4) ? 1 : 0)
                + (blockstate3.isCollisionShapeFullBlock(blockgetter, blockpos5) ? 1 : 0);

        if (i <= 0) {
            if (i >= 0) {
                int j = direction.getStepX();
                int k = direction.getStepZ();
                Vec3 vec3 = pContext.getClickLocation();
                double d0 = vec3.x - (double) blockpos.getX();
                double d1 = vec3.z - (double) blockpos.getZ();
                return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        if (pLevel.isClientSide) return InteractionResult.SUCCESS;
        if (pPlayer.getItemInHand(pHand).is(ItemInit.ColoredBrush.get())) {
            CarriageNoSignEntity entity = ((CarriageNoSignEntity)pLevel.getBlockEntity(pPos));
            NetworkHooks.openGui((ServerPlayer) pPlayer, entity, pPos);
            entity.markUpdated();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        switch (state.getValue(TYPE)){
            case P25BA -> {return BlockInit.PANEL_25B_ORIGINAL_TOP.get().asItem().getDefaultInstance();}
            case P25BB -> {return BlockInit.PANEL_25B_ORIGINAL_TOP_B.get().asItem().getDefaultInstance();}
            case P25G -> {return BlockInit.PANEL_25G_ORIGINAL_TOP.get().asItem().getDefaultInstance();}
            case P25Z -> {return BlockInit.PANEL_25Z_ORIGINAL_TOP.get().asItem().getDefaultInstance();}
            case P25K -> {return BlockInit.PANEL_25K_ORIGINAL_TOP.get().asItem().getDefaultInstance();}
            case P25TA -> {return BlockInit.PANEL_25T_ORIGINAL_TOP.get().asItem().getDefaultInstance();}
            case P25TB -> {return BlockInit.PANEL_25T_ORIGINAL_TOP_B.get().asItem().getDefaultInstance();}
            case P25TC -> {return BlockInit.PANEL_XL25T_ORIGINAL_TOP.get().asItem().getDefaultInstance();}
            case M25A -> {return BlockInit.PANEL_25_MARSHALLED_TOP.get().asItem().getDefaultInstance();}
            case M25B -> {return BlockInit.PANEL_25_MARSHALLED_TOP_B.get().asItem().getDefaultInstance();}
            case CR200J -> {return BlockInit.PANEL_CR200J_MARSHALLED_TOP.get().asItem().getDefaultInstance();}
            default -> {return BlockInit.PANEL_25B_ORIGINAL_TOP.get().asItem().getDefaultInstance();}
        }
    }
}
