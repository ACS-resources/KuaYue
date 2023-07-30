package willow.train.kuayue.Blocks.TrainCarriage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class Train25KTEndFaceBlock extends TrapDoorBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;



    public Train25KTEndFaceBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)).setValue(HALF, Half.BOTTOM).setValue(POWERED, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));

    }

    protected static final VoxelShape NORTH_AABB = Shapes.or(Block.box(-16, -16, -1, 32, 21, 1),
            Block.box(-1, 17.75, -7.975, 17, 19, -1),
            Block.box(15.2, -14, -7.975, 17.2, 18, -1),
            Block.box(0, -16, -7.7, 2, -11, -1.25),
            Block.box(-1.2, -14, -7.975, 0.8, 18, -1),
            Block.box(-1, -16, -7.75, 17, -14, -6.15),
            Block.box(-1, -16, -6.375, 17, -14, 1),
            Block.box(1, 18, -1, 15, 21, 1),
            Block.box(14, -16, -7.7, 16, -11, -1.25)
    );
    protected static final VoxelShape SOUTH_AABB =Shapes.or(Block.box(-16, -16, 15, 32, 21, 17),
            Block.box(-1, 17.75, 17, 17, 19, 23.975),
            Block.box(-1.2, -14, 17, 0.8, 18, 23.975),
            Block.box(14, -16, 17.25, 16, -11, 23.7),
            Block.box(15.2, -14, 17, 17.2, 18, 23.975),
            Block.box(-1, -16, 22.15, 17, -14, 23.75),
            Block.box(-1, -16, 15, 17, -14, 22.375),
            Block.box(1, 18, 15, 15, 21, 17),
            Block.box(0, -16, 17.25, 2, -11, 23.7)
    );
    protected static final VoxelShape EAST_AABB =Shapes.or(Block.box(15, -16, -16, 17, 21, 32),
            Block.box(17, 17.75, -1, 23.975, 19, 17),
            Block.box(17, -14, 15.2, 23.975, 18, 17.2),
            Block.box(17.25, -16, 0, 23.7, -11, 2),
            Block.box(17, -14, -1.2, 23.975, 18, 0.8),
            Block.box(22.15, -16, -1, 23.75, -14, 17),
            Block.box(15, -16, -1, 22.375, -14, 17),
            Block.box(15, 18, 1, 17, 21, 15),
            Block.box(17.25, -16, 14, 23.7, -11, 16)
    );
    protected static final VoxelShape  WEST_AABB=Shapes.or(Block.box(-1, -16, -16, 1, 21, 32),
            Block.box(-7.975, 17.75, -1, -1, 19, 17),
            Block.box(-7.975, -14, -1.2, -1, 18, 0.8),
            Block.box(-7.699999999999999, -16, 14, -1.25, -11, 16),
            Block.box(-7.975, -14, 15.2, -1, 18, 17.2),
            Block.box(-7.75, -16, -1, -6.15, -14, 17),
            Block.box(-6.375, -16, -1, 1, -14, 17),
            Block.box(-1, 18, 1, 1, 21, 15),
            Block.box(-7.7, -16, 0, -1.25, -11, 2));


    protected static final VoxelShape NORTH_AABBo =Shapes.or(Block.box(15, -16, -1, 32, 21, 1),
            Block.box(-16, -16, -1, 1, 21, 1),
            Block.box(1, 18, -1, 15, 21, 1),
            Block.box(-1, 17.75, -7.9750, 17, 19, -1),
            Block.box(15.2, -14, -7.9750, 17.2, 18, -1),
            Block.box(0, -16, -7.7, 2, -11, -1.25),
            Block.box(-1.2, -14, -7.975, 0.8, 18, -1),
            Block.box(-1, -16, -7.75, 17, -14, -6.15),
            Block.box(-1, -16, -6.375, 17, -14, 1),
            Block.box(14, -16, -7.7, 16, -11, -1.25),
            Block.box(-6, -14, 1, 1, 1, 2),
            Block.box(-4.75, 15, 1, -0.25, 18, 2),
            Block.box(-0.25, 1, 1, 1, 18, 2),
            Block.box(-6, 1, 1, -4.75, 18, 2),
            Block.box(15, -14, 1, 22, 1, 2),
            Block.box(16.25, 15, 1, 20.75, 18, 2),
            Block.box(15, 1, 1, 16.25, 18, 2),
            Block.box(20.75, 1, 1, 22, 18, 2));
    protected static final VoxelShape WEST_AABBo = Shapes.or(
            Block.box(-1, -16, -16, 1, 21, 1),
            Block.box(-1, -16, 15, 1, 21, 32),
            Block.box(-1, 18, 1, 1, 21, 15),
            Block.box(-7.975, 17.75, -1, -1, 19, 17),
            Block.box(-7.975, -14, -1.2, -1, 18, 0.80),
            Block.box(-7.7, -16, 14, -1.25, -11, 16),
            Block.box(-7.975, -14, 15.2, -1, 18, 17.2),
            Block.box(-7.75, -16, -1, -6.15, -14, 17),
            Block.box(-6.375, -16, -1, 1, -14, 17),
            Block.box(-7.7, -16, 0, -1.25, -11, 2),
            Block.box(1, -14, 15, 2, 1, 22),
            Block.box(1, 15, 16.25, 2, 18, 20.75),
            Block.box(1, 1, 15, 2, 18, 16.25),
            Block.box(1, 1, 20.75, 2, 18, 22),
            Block.box(1, -14, -6, 2, 1, 1),
            Block.box(1, 15, -4.75, 2, 18, -0.25),
            Block.box(1, 1, -0.25, 2, 18, 1),
            Block.box(1, 1, -6, 2, 18, -4.75));
    protected static final VoxelShape SOUTH_AABBo = Shapes.or(Block.box(-16, -16, 15, 1, 21, 17),
            Block.box(15, -16, 15, 32, 21, 17),
            Block.box(1, 18, 15, 15, 21, 17),
            Block.box(-1, 17.75, 17, 17, 19, 23.975),
            Block.box(-1.2, -14, 17, 0.8, 18, 23.975),
            Block.box(14, -16, 17.25, 16, -11, 23.7),
            Block.box(15.2, -14, 17, 17.2, 18, 23.975),
            Block.box(-1, -16, 22.15, 17, -14, 23.75),
            Block.box(-1, -16, 15, 17, -14, 22.375),
            Block.box(0, -16, 17.25, 2, -11, 23.7),
            Block.box(15, -14, 14, 22, 1, 15),
            Block.box(16.25, 15, 14, 20.75, 18, 15),
            Block.box(15, 1, 14, 16.25, 18, 15),
            Block.box(20.75, 1, 14, 22, 18, 15),
            Block.box(-6, -14, 14, 1, 1, 15),
            Block.box(-4.75, 15, 14, -0.25, 18, 15),
            Block.box(-0.25, 1, 14, 1, 18, 15),
            Block.box(-6, 1, 14, -4.75, 18, 15));
    protected static final VoxelShape EAST_AABBo =Shapes.or(Block.box(15, -16, 15, 17, 21, 32),
            Block.box(15, -16, -16, 17, 21, 1),
            Block.box(15, 18, 1, 17, 21, 15),
            Block.box(17, 17.75, -1, 23.975, 19, 17),
            Block.box(17, -14, 15.2, 23.975, 18, 17.2),
            Block.box(17.25, -16, 0, 23.7, -11, 2),
            Block.box(17, -14, -1.2, 23.975, 18, 0.8),
            Block.box(22.15, -16, -1, 23.75, -14, 17),
            Block.box(15, -16, -1, 22.375, -14, 17),
            Block.box(17.25, -16, 14, 23.7, -11, 16),
            Block.box(14, -14, -6, 15, 1, 1),
            Block.box(14, 15, -4.75, 15, 18, -0.25),
            Block.box(14, 1, -0.25, 15, 18, 1),
            Block.box(14, 1, -6, 15, 18, -4.75),
            Block.box(14, -14, 15, 15, 1, 22),
            Block.box(14, 15, 16.25, 15, 18, 20.75),
            Block.box(14, 1, 15, 15, 18, 16.25),
            Block.box(14, 1, 20.75, 15, 18, 22));




//    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
//        return true;
//    }

    public VoxelShape getShape(BlockState pState, BlockGetter p_54373_, BlockPos p_54374_, CollisionContext p_54375_) {
        if(!pState.getValue(OPEN)){

            switch (pState.getValue(FACING)) {
                case NORTH:
                    return NORTH_AABB;
                case SOUTH:
                    return SOUTH_AABB;
                case WEST:
                    return WEST_AABB;
                case EAST:
                default:
                    return EAST_AABB;
            }}
        else{
            switch ( pState.getValue(FACING)) {
                case NORTH:
                    return NORTH_AABBo;
                case SOUTH:
                    return SOUTH_AABBo;
                case WEST:
                    return WEST_AABBo;
                case EAST:
                default:
                    return EAST_AABBo;
            }}
}

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if(!pState.getValue(OPEN)){

            switch (pState.getValue(FACING)) {
                case NORTH:
                    return NORTH_AABB;
                case SOUTH:
                    return SOUTH_AABB;
                case WEST:
                    return WEST_AABB;
                case EAST:
                default:
                    return EAST_AABB;
            }}
        else{
            switch ( pState.getValue(FACING)) {
                case NORTH:
                    return NORTH_AABBo;
                case SOUTH:
                    return SOUTH_AABBo;
                case WEST:
                    return WEST_AABBo;
                case EAST:
                default:
                    return EAST_AABBo;
            }}
    }

    private Half getHinge(BlockPlaceContext pContext) {
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
        //boolean flag = blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        //boolean flag1 = blockstate2.is(this) && blockstate2.getValue(HALF) == DoubleBlockHalf.LOWER;
        if (i <= 0) {
            if (i >= 0) {
                int j = direction.getStepX();
                int k = direction.getStepZ();
                Vec3 vec3 = pContext.getClickLocation();
                double d0 = vec3.x - (double) blockpos.getX();
                double d1 = vec3.z - (double) blockpos.getZ();
                return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? Half.BOTTOM : Half.TOP;
            } else {
                return Half.BOTTOM;
            }
        } else {
            return Half.TOP;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection())
                .setValue(HALF, this.getHinge(pContext)).setValue(OPEN,false).setValue(POWERED,false)
                .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }



    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        //pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

@Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

            pState = pState.cycle(OPEN);
            pLevel.setBlock(pPos, pState, 10);
            //pLevel.levelEvent(pPlayer, pState.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), pPos, 0);
            pLevel.gameEvent(pPlayer, this.isOpen(pState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);

    }

    public boolean isOpen(BlockState pState) {
        return pState.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity p_153166_, Level pLevel, BlockState pState, BlockPos pPos, boolean pOpen) {
        if (pState.is(this) && pState.getValue(OPEN) != pOpen) {
            pLevel.setBlock(pPos, pState.setValue(OPEN, Boolean.valueOf(pOpen)), 10);
            //this.playSound(pLevel, pPos, pOpen);
            pLevel.gameEvent(p_153166_, pOpen ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
        }
    }


    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
//        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
//        if (pFacing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (pFacing == Direction.UP)) {
//            return pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf ? pState.setValue(FACING, pFacingState.getValue(FACING)).setValue(OPEN, pFacingState.getValue(OPEN)).setValue(HINGE, pFacingState.getValue(HINGE)).setValue(POWERED, pFacingState.getValue(POWERED)) : Blocks.AIR.defaultBlockState();
//        } else {
//            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
//        }

        return pState;
    }
//    public BlockState mirror(BlockState pState, Mirror pMirror) {
//        return pMirror == Mirror.NONE ? pState : pState.rotate(pMirror.getRotation(pState.getValue(FACING))).cycle(HINGE);
//    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }
//    @Override
//    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
//
//    }
//    @Override
//    public PushReaction getPistonPushReaction(BlockState pState) {
//        return PushReaction.PUSH_ONLY;
//    }
//    @Override
//    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
////        if (!pLevel.isClientSide && pPlayer.isCreative()) {
////            DoublePlantBlock.preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
////        }
//
//        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
//    }
}
