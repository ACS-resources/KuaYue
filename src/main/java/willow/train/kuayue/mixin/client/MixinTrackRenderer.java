package willow.train.kuayue.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.track.BezierConnection;
import com.simibubi.create.content.trains.track.TrackRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import willow.train.kuayue.init.KYBlockPartials;
import willow.train.kuayue.mixin_interfaces.IStandardBezier;

@Mixin(value = TrackRenderer.class, remap = false)
public class MixinTrackRenderer {

    @Inject(method = "renderBezierTurn",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/track/TrackRenderer;renderGirder(Lnet/minecraft/world/level/Level;Lcom/simibubi/create/content/trains/track/BezierConnection;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/core/BlockPos;)V", shift = At.Shift.AFTER, remap = true),
            cancellable = true)
    private static void renderMonorailMaybe(Level level, BezierConnection bc, PoseStack ms, VertexConsumer vb, CallbackInfo ci) {

        String trackMaterialId = String.valueOf(bc.getMaterial().id);

        if (trackMaterialId.equals("kuayue:standard")) {
            renderActualStandard(level, bc, ms, vb, bc.tePositions.getFirst());
            ms.popPose(); // clean up pose, since cancelled
            ci.cancel(); // Don't do normal rendering
        }
    }


    private static void renderActualStandard(Level level, BezierConnection bc, PoseStack ms, VertexConsumer vb,
                                                    BlockPos tePosition) {

        BlockState air = Blocks.AIR.defaultBlockState();
        IStandardBezier.StandardAngles[] standards = ((IStandardBezier) bc).getBakedStandards();

        for (int i = 1; i < standards.length; i++) {
            IStandardBezier.StandardAngles segment = standards[i];
            int light = LevelRenderer.getLightColor(level, segment.lightPosition.offset(tePosition));

            PoseStack.Pose beamTransform = segment.tieTransform;

            CachedBufferer.partial(KYBlockPartials.KY_TRACK_TIE, air)
                    .mulPose(beamTransform.pose())
                    .mulNormal(beamTransform.normal())
                    .light(light)
                    .renderInto(ms, vb);

            for (boolean top : Iterate.trueAndFalse) {
                PoseStack.Pose beamCapTransform = segment.railTransforms.get(top);
                CachedBufferer.partial(top ? KYBlockPartials.KY_TRACK_SEGMENT_RIGHT : KYBlockPartials.KY_TRACK_SEGMENT_LEFT, air)
                        .mulPose(beamCapTransform.pose())
                        .mulNormal(beamCapTransform.normal())
                        .light(light)
                        .renderInto(ms, vb);
            }
        }
    }
}
