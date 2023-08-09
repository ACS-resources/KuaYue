package willow.train.kuayue.Blocks.Signs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.BlockEntity.CarriageTypeSignEntity;
import willow.train.kuayue.BlockEntity.LaqueredBoardEntity;
import willow.train.kuayue.Util.PanelTypes;
import willow.train.kuayue.init.BlockEntitiesInit;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;

public class LaqueredBoardBlock extends KuayueSignBlock{

    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty MIRROR = BlockStateProperties.OPEN;
    public static final EnumProperty<PanelTypes> TYPE = EnumProperty.create("type", PanelTypes.class);

    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape SOUTH_AABB = Block.box(0, 0, 15, 16, 16, 17);
    protected static final VoxelShape EAST_AABB = Block.box(15, 0, 0, 17, 16, 16);
    protected static final VoxelShape NORTH_AABB = Block.box(0, 0, -1, 16, 16, 1);
    protected static final VoxelShape WEST_AABB = Block.box(-1, 0, 0, 1, 16, 16);

    public LaqueredBoardBlock(Properties pProperties) {
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LaqueredBoardEntity(pPos, pState);
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(HINGE, this.getHinge(context)).setValue(TYPE, PanelTypes.P25G);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context, PanelTypes types, DoorHingeSide hinge) {
        return super.getStateForPlacement(context).setValue(HINGE, hinge).setValue(TYPE, types);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(HINGE, TYPE, MIRROR);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getItemInHand(pHand).is(ItemInit.ColoredBrush.get())){
            if(!pLevel.isClientSide){
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (LaqueredBoardEntity) pLevel.getBlockEntity(pPos), pPos);
                ((LaqueredBoardEntity) pLevel.getBlockEntity(pPos)).markUpdated();
                return InteractionResult.PASS;
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        PanelTypes types = state.getValue(TYPE);
        switch (types){
            case P25B -> {return BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance();}
            case P25G -> {return BlockInit.PANEL_25G_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance();}
            case P25Z -> {return BlockInit.PANEL_25Z_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance();}
            case P25KA -> {return BlockInit.PANEL_25K_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance();}
            case P25KB -> {return BlockInit.PANEL_25K_ORIGINAL_LINE.get().asItem().getDefaultInstance();}
            case P25TA -> {return BlockInit.PANEL_25T_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance();}
            case P25TB -> {return BlockInit.PANEL_25T_ORIGINAL_BOTTOM_B.get().asItem().getDefaultInstance();}
            case M25B -> {return BlockInit.PANEL_25B_MARSHALLED_SYMBOL.get().asItem().getDefaultInstance();}
            case M25G -> {return BlockInit.PANEL_25G_MARSHALLED_SYMBOL.get().asItem().getDefaultInstance();}
            case M25Z -> {return BlockInit.PANEL_25_MARSHALLED_BOTTOM.get().asItem().getDefaultInstance();}
            case M25T -> {return BlockInit.PANEL_25_MARSHALLED_BOTTOM_LINE.get().asItem().getDefaultInstance();}
            case M25KA -> {return BlockInit.PANEL_25K_MARSHALLED_SYMBOL.get().asItem().getDefaultInstance();}
            case M25KB -> {return BlockInit.PANEL_CR200J_MARSHALLED_BOTTOM.get().asItem().getDefaultInstance();}
            default -> {return BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance();}
        }
    }
}
