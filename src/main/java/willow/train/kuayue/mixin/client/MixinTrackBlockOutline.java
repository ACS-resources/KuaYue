package willow.train.kuayue.mixin.client;

import com.simibubi.create.content.trains.track.BezierConnection;
import com.simibubi.create.content.trains.track.TrackBlockOutline;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import willow.train.kuayue.Blocks.Tracks.standard_track.StandardTrackBlock;
import willow.train.kuayue.Blocks.Tracks.standard_track.StandardTrackBlockOutline;
import willow.train.kuayue.init.KYTrackMaterials;

@Mixin(value = TrackBlockOutline.class, remap = false)
public class MixinTrackBlockOutline {

    @Unique
    private static boolean railway$resultIsStandard;

    @ModifyVariable(
            method = "pickCurves",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/track/BezierTrackPointLocation;<init>(Lnet/minecraft/core/BlockPos;I)V",
                    remap = true
            )
    )
    private static BezierConnection railway$grabResultStandardState(BezierConnection bc) {
        railway$resultIsStandard = bc.getMaterial() == KYTrackMaterials.STANDARD;
        return bc;
    }

    @ModifyArg(
            method = "drawCurveSelection",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/track/TrackBlockOutline;renderShape(Lnet/minecraft/world/phys/shapes/VoxelShape;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Ljava/lang/Boolean;)V",
                    remap = true
            )
    )
    private static VoxelShape railway$renderCurvedStandardShape(VoxelShape shape) {
        return StandardTrackBlockOutline.convert(shape, railway$resultIsStandard);
    }

    private static boolean railway$walkingStandard;

    @ModifyVariable(
            method = "drawCustomBlockSelection",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V",
                    remap = true
            )
    )
    private static BlockState railway$grabStandardState(BlockState state) {
        railway$walkingStandard = state.getBlock() instanceof StandardTrackBlock;
        return state;
    }

    @ModifyArg(
            method = "walkShapes",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"
            )
    )
    private static Object railway$renderStandardBlockShape(Object o) {
        return StandardTrackBlockOutline.convert(o, railway$walkingStandard);
    }
}
