package willow.train.kuayue.Blocks.catenary;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import willow.train.kuayue.Util.HorizontalBlockBase;

public class CatenaryPoleBlock extends HorizontalBlockBase {

    public CatenaryPoleBlock(Properties p_49795_) {
        super(p_49795_);
    }

    protected static final VoxelShape SOUTH_AABB = Shapes.or(Block.box(8, 0, 6.5, 16, 16, 9.5));
    protected static final VoxelShape WEST_AABB = Shapes.or(Block.box(6.5, 0, 8, 9.5, 16, 16));
    protected static final VoxelShape NORTH_AABB = Shapes.or(Block.box(0, 0, 6.5, 8, 16, 9.5));
    protected static final VoxelShape EAST_AABB = Shapes.or(Block.box(6.5, 0, 0, 9.5, 16, 8));


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
}
