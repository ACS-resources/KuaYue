package willow.train.kuayue.Blocks.TrainCarriage;

import Network.KuayueNetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import willow.train.kuayue.Blocks.Signs.KuayueSignBlock;
import willow.train.kuayue.Entity.CarriageTypeSignEntity;
import willow.train.kuayue.Items.TrainPanelItem;
import willow.train.kuayue.Util.PanelTypes;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;

public class TrainPanelBlock extends KuayueSignBlock {

    public static Integer UUID;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final EnumProperty<PanelTypes> TYPE = EnumProperty.create("type", PanelTypes.class);
    CarriageTypeSignEntity ctse;


    private static VoxelShape shape;
    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape SOUTH_AABB = Block.box(0, 0, 15, 16, 16, 17);
    protected static final VoxelShape EAST_AABB = Block.box(15, 0, 0, 17, 16, 16);
    protected static final VoxelShape NORTH_AABB = Block.box(0, 0, -1, 16, 16, 1);
    protected static final VoxelShape WEST_AABB = Block.box(-1, 0, 0, 1, 16, 16);

    public TrainPanelBlock(Properties p_49795_) {
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

    //猪
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
        //boolean flag = blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        //boolean flag1 = blockstate2.is(this) && blockstate2.getValue(HALF) == DoubleBlockHalf.LOWER;
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Player pPlayer = context.getPlayer();
        ItemStack itemStack = pPlayer.getMainHandItem();

        if (itemStack.is(BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get().asItem())){
            TrainPanelItem item = (TrainPanelItem) itemStack.getItem();
            return super.getStateForPlacement(context).setValue(HINGE, this.getHinge(context)).setValue(TYPE, item.panelTypes);
        }
        return super.getStateForPlacement(context).setValue(HINGE, this.getHinge(context)).setValue(TYPE, PanelTypes.P25B);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(   pBuilder);
        pBuilder.add(HINGE, TYPE);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
        if(pLevel.getBlockEntity(pPos) != null) pLevel.getBlockEntity(pPos).setRemoved();
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        super.wasExploded(pLevel, pPos, pExplosion);
        if(pLevel.getBlockEntity(pPos) != null) pLevel.getBlockEntity(pPos).setRemoved();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        ctse = new CarriageTypeSignEntity(pPos, pState);
        return ctse;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getMainHandItem().is(ItemInit.CA_25T.get())){
            if(!pLevel.isClientSide){
                NetworkHooks.openGui((ServerPlayer) pPlayer, (CarriageTypeSignEntity) pLevel.getBlockEntity(pPos), pPos);
                pLevel.getChunk(pPos).getBlockEntity(pPos).setChanged();
            }
            return InteractionResult.PASS;
        }
        if(pPlayer.getMainHandItem().is(ItemStack.EMPTY.getItem())) {
            pState = pState.cycle(MIRROR);
            pLevel.setBlock(pPos, pState, 10);
            pLevel.gameEvent(pPlayer, this.isMirrored(pState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
            pLevel.getChunk(pPos).getBlockEntity(pPos).setChanged();
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public boolean isMirrored(BlockState pState) {
        return pState.getValue(MIRROR);
    }

    public boolean isPossibleToRespawnInThis() {
        return true;
    }

    /*
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        this.ctse = new CarriageTypeSignEntity(pPos, pState);
        ctse.setColor(CarriageTypeSignEntity.YELLOW);
        return ctse;
    }
     */
}
