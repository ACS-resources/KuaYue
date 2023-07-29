package willow.train.kuayue.mixin.client;

import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.track.BezierConnection;
import com.simibubi.create.content.trains.track.TrackInstance;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import willow.train.kuayue.init.KYBlockPartials;
import willow.train.kuayue.init.KYTrackMaterials;
import willow.train.kuayue.mixin_interfaces.IStandardBezier;

@Mixin(targets = "com.simibubi.create.content.trains.track.TrackInstance$BezierTrackInstance", remap = false)
public abstract class MixinTrackInstance_BezierTrackInstance {

    @Final
    @Shadow(remap = false)
    TrackInstance this$0;

    @Mutable
    @Shadow(remap = false)
    @Final
    private ModelData[] ties;

    @Shadow(remap = false)
    @Final
    @Mutable
    private ModelData[] right;

    @Shadow(remap = false)
    @Final
    @Mutable
    private ModelData[] left;

    @Shadow(remap = false)
    @Final
    @Mutable
    private BlockPos[] tiesLightPos;

    @Shadow(remap = false)
    @Final
    @Mutable
    private BlockPos[] leftLightPos;

    @Shadow(remap = false)
    @Final
    @Mutable
    private BlockPos[] rightLightPos;

    @Shadow(remap = false)
    abstract void updateLight();


    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/track/BezierConnection;getSegmentCount()I"))
    private int messWithCtor(BezierConnection instance) {
        String trackMaterialId = String.valueOf(instance.getMaterial().id);
        return trackMaterialId.equals("kuayue:standard") ? 0 : instance.getSegmentCount();
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/track/BezierConnection;getBakedSegments()[Lcom/simibubi/create/content/trains/track/BezierConnection$SegmentAngles;"))
    private BezierConnection.SegmentAngles[] messWithCtor2(BezierConnection instance) {
        String trackMaterialId = String.valueOf(instance.getMaterial().id);
        return trackMaterialId.equals("kuayue:standard") ? new BezierConnection.SegmentAngles[0] : instance.getBakedSegments();
        //return instance.getBakedSegments();
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addActualStandard(TrackInstance trackInstance, BezierConnection bc, CallbackInfo ci) {

        String trackMaterialId = String.valueOf(bc.getMaterial().id);

        if (trackMaterialId.equals("kuayue:standard")) {
            BlockPos tePosition = bc.tePositions.getFirst();
            PoseStack pose = new PoseStack();
            TransformStack.cast(pose)
                    .translate(this$0.getInstancePosition())
                    .nudge((int) bc.tePositions.getFirst()
                            .asLong());

            BlockState air = Blocks.AIR.defaultBlockState();
            IStandardBezier.StandardAngles[] standards = ((IStandardBezier) bc).getBakedStandards();

            //girder = bc.hasGirder ? new TrackInstance.BezierTrackInstance.GirderInstance(bc) : null;

            var mat = ((AccessorInstance) this$0).getMaterialManager().cutout(RenderType.cutoutMipped())
                    .material(Materials.TRANSFORMED);

            /*TransformStack.cast(pose)
                    .translate(this$0.getInstancePosition());*/

            right = new ModelData[standards.length-1];
            ties = new ModelData[standards.length-1];
            left = new ModelData[standards.length-1];
            tiesLightPos = new BlockPos[standards.length-1];
            leftLightPos = new BlockPos[standards.length-1];
            rightLightPos = new BlockPos[standards.length-1];

            mat.getModel(KYBlockPartials.KY_TRACK_TIE)
                    .createInstances(ties);
            mat.getModel(KYBlockPartials.KY_TRACK_SEGMENT_LEFT)
                    .createInstances(left);
            mat.getModel(KYBlockPartials.KY_TRACK_SEGMENT_RIGHT)
                    .createInstances(right);

            for (int i = 1; i < standards.length; i++) {
                IStandardBezier.StandardAngles segment = standards[i];
                var modelIndex = i - 1;

                PoseStack.Pose beamTransform = segment.tieTransform;

                ties[modelIndex].setTransform(pose)
                        .mulPose(beamTransform.pose())
                        .mulNormal(beamTransform.normal());
                tiesLightPos[modelIndex] = segment.lightPosition.offset(tePosition);

                for (boolean first : Iterate.trueAndFalse) {
                    PoseStack.Pose transform = segment.railTransforms.get(first);
                    (first ? this.left : this.right)[modelIndex].setTransform(pose)
                            .mulPose(transform.pose())
                            .mulNormal(transform.normal());
                    (first ? leftLightPos : rightLightPos)[modelIndex] = segment.lightPosition.offset(tePosition);
                }
            }

            updateLight();
        }
    }
}
