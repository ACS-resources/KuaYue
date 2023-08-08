package willow.train.kuayue.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.actors.roller.RollerMovementBehaviour;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import willow.train.kuayue.init.KYCreateBlock;

@Mixin(RollerMovementBehaviour.class)
public class MixinRollerMovementBehaviour extends BlockBreakingMovementBehaviour {

    /**
     * @author zlw380
     * @reason 使压路机不会破坏标准轨
     */
    @Overwrite(remap = false)
    public boolean canBreak(Level world, BlockPos breakingPos, BlockState state) {

        return super.canBreak(world, breakingPos, state) && !state.getCollisionShape(world, breakingPos)
                .isEmpty() && !AllBlocks.TRACK.has(state) && !KYCreateBlock.STANDARD_TRACK.has(state);
    }

}
