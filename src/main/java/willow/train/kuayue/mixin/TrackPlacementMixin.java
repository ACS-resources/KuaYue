package willow.train.kuayue.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.content.logistics.trains.track.TrackPlacement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

@Mixin(TrackPlacement.class)
public class TrackPlacementMixin {
    @Redirect(method = "tryConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;distSqr(Lnet/minecraft/core/Vec3i;)D"))
    private static double redirect(BlockPos pos, Vec3i vec3i) {
        return pos.distSqr(vec3i) / (double) 128;
    }
}
