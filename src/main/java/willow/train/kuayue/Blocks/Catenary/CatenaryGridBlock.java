package willow.train.kuayue.Blocks.catenary;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.Util.HorizontalBlockBase;
import willow.train.kuayue.init.ItemInit;

import static willow.train.kuayue.Main.CATENARYCONNECTIONHANDLER;

public class CatenaryGridBlock extends HorizontalBlockBase {

    public CatenaryGridBlock(Properties p_49795_) {
        super(p_49795_);
    }

    protected static final VoxelShape SOUTH_AABB = Shapes.or(Block.box(6.75, 6, -5.375, 9, 17, 23.625));
    protected static final VoxelShape WEST_AABB = Shapes.or(Block.box(-5.375, 6, 6.75, 23.625, 17, 9));
    protected static final VoxelShape NORTH_AABB = Shapes.or(Block.box(6.75, 6, -5.375, 9, 17, 23.625));
    protected static final VoxelShape EAST_AABB = Shapes.or(Block.box(-5.375, 6, 6.75, 23.625, 17, 9));


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
