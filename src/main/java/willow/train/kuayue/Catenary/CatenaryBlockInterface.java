package willow.train.kuayue.Catenary;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public interface CatenaryBlockInterface {
    Vec3[] getCatenaryPorts();

    void discardCatenary(Level level , BlockPos pos , double areaWidth);

    Vec3 getCatenaryPort(BlockPos pPos, BlockState pState , Direction direction);
}
