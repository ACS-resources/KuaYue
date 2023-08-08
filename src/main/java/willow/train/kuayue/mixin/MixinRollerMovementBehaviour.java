package willow.train.kuayue.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.actors.roller.RollerMovementBehaviour;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import willow.train.kuayue.init.KYCreateBlock;

@Mixin(RollerMovementBehaviour.class)
public class MixinRollerMovementBehaviour {

    @Redirect(method = "canBreak",
            at = @At(value = "INVOKE",
                    target = "Lcom/simibubi/create/content/kinetics/base/BlockBreakingMovementBehaviour;canBreak(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean redirectCanBreak(BlockBreakingMovementBehaviour instance, Level world, BlockPos breakingPos, BlockState state) {

        return instance.canBreak(world, breakingPos, state) && !state.getCollisionShape(world, breakingPos)
                .isEmpty() && !AllBlocks.TRACK.has(state) && !KYCreateBlock.STANDARD_TRACK.has(state);
    }

}
