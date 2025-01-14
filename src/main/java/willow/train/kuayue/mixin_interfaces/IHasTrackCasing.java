package willow.train.kuayue.mixin_interfaces;

import com.simibubi.create.content.trains.track.TrackBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface IHasTrackCasing {

    @Nullable
    SlabBlock getTrackCasing();
    void setTrackCasing(@Nullable SlabBlock trackCasing);

    default boolean isAlternate() {
        return false;
    }

    default void setAlternate(boolean alternate) {}

    static @Nullable SlabBlock getTrackCasing(Level world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof willow.train.kuayue.mixin_interfaces.IHasTrackCasing te) {
            return te.getTrackCasing();
        }
        return null;
    }

    static void setTrackCasing(Level world, BlockPos pos, @Nullable SlabBlock trackCasing) {
        BlockState state = world.getBlockState(pos);
        if (state.hasProperty(TrackBlock.HAS_BE))
            world.setBlockAndUpdate(pos, state.setValue(TrackBlock.HAS_BE, true));
        if (world.getBlockEntity(pos) instanceof willow.train.kuayue.mixin_interfaces.IHasTrackCasing te) {
            te.setTrackCasing(trackCasing);
        }
    }

    static boolean isAlternate(Level world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof willow.train.kuayue.mixin_interfaces.IHasTrackCasing te) {
            return te.isAlternate();
        }
        return false;
    }

    static boolean setAlternateModel(Level world, BlockPos pos, boolean useAlternateModel) {
        if (world.getBlockEntity(pos) instanceof willow.train.kuayue.mixin_interfaces.IHasTrackCasing te) {
            te.setAlternate(useAlternateModel);
            return true;
        } else {
            return false;
        }
    }
}
