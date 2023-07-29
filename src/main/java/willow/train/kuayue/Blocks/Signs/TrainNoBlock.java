package willow.train.kuayue.Blocks.Signs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.BlockEntity.CarriageNoSignEntity;

public class TrainNoBlock extends KuayueSignBlock{

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
        return super.getStateForPlacement(context);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CarriageNoSignEntity(pPos, pState);
    }
}
