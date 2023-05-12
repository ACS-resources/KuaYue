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

public class Catenary1600WithPlumbBlock extends HorizontalBlockBase {

    public Catenary1600WithPlumbBlock(Properties p_49795_) {
        super(p_49795_);
    }

    protected static final VoxelShape WEST_AABB = Shapes.or(Block.box(0, 0, 6, 16, 16, 10));
    protected static final VoxelShape SOUTH_AABB = Shapes.or(Block.box(6, 0, 0,10 , 16, 16));
    protected static final VoxelShape EAST_AABB = Shapes.or(Block.box(0, 0, 6, 16, 16, 10));
    protected static final VoxelShape NORTH_AABB = Shapes.or(Block.box(6, 0, 0, 10, 16, 16));


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
