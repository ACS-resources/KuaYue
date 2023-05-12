package willow.train.kuayue.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.Util.HorizontalBlockBase;
import willow.train.kuayue.Util.ShuntingSignalBlockBase;

public class SeatedSignalBlock extends ShuntingSignalBlockBase {

    public SeatedSignalBlock(Properties p_49795_) {
        super(p_49795_);
    }

    protected static final VoxelShape SOUTH_AABB = Shapes.or(Block.box(2.5, 0, 4, 13.5, 31.25, 12));
    protected static final VoxelShape WEST_AABB = Shapes.or(Block.box(4, 0, 2.5, 12, 31.25, 13.5));
    protected static final VoxelShape NORTH_AABB = Shapes.or(Block.box(2.5, 0, 4, 13.5, 31.25, 12));
    protected static final VoxelShape EAST_AABB = Shapes.or(Block.box(4, 0, 2.5, 12, 31.25, 13.5));


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

    public void neighborChanged(BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        pLevel.setBlock(pPos, pState.setValue(POWERED, flag), 2);
    }
}
