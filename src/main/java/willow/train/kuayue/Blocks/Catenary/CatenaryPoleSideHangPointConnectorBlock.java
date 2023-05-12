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

public class CatenaryPoleSideHangPointConnectorBlock extends HorizontalBlockBase {

    public CatenaryPoleSideHangPointConnectorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    protected static final VoxelShape AABB = Shapes.or(Block.box(0, -2, 0, 16, 2, 16));


    public VoxelShape getShape(BlockState p_54372_, BlockGetter p_54373_, BlockPos p_54374_, CollisionContext p_54375_) {
        return AABB;
    }
}
