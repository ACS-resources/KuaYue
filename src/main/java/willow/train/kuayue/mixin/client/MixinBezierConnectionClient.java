package willow.train.kuayue.mixin.client;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.track.BezierConnection;
import com.simibubi.create.content.trains.track.TrackRenderer;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import willow.train.kuayue.mixin_interfaces.IStandardBezier;

@Mixin(value = BezierConnection.class, remap = false)
public abstract class MixinBezierConnectionClient implements IStandardBezier {

    @Shadow
    public abstract int getSegmentCount();

    private StandardAngles[] bakedStandards;

    @Override
    public StandardAngles[] getBakedStandards() {
        if (bakedStandards != null)
            return bakedStandards;

        int segmentCount = getSegmentCount();
        bakedStandards = new StandardAngles[segmentCount + 1];
        Couple<Vec3> previousOffsets = null;

        BezierConnection this_ = (BezierConnection) (Object) this;

        for (BezierConnection.Segment segment : this_) {
            int i = segment.index;
            boolean end = i == 0 || i == segmentCount;

            StandardAngles angles = bakedStandards[i] = new StandardAngles();
            Couple<Vec3> railOffsets = Couple.create(segment.position.add(segment.normal.scale(.755f)),
                    segment.position.subtract(segment.normal.scale(.755f)));
            Vec3 railMiddle = railOffsets.getFirst()
                    .add(railOffsets.getSecond())
                    .scale(.5);

            if (previousOffsets == null) {
                previousOffsets = railOffsets;
                continue;
            }

            // Tie
            Vec3 prevMiddle = previousOffsets.getFirst()
                    .add(previousOffsets.getSecond())
                    .scale(.5);
            Vec3 tieAngles = TrackRenderer.getModelAngles(segment.normal, railMiddle.subtract(prevMiddle));
            angles.lightPosition = new BlockPos(railMiddle);
            angles.railTransforms = Couple.create(null, null);

            PoseStack poseStack = new PoseStack();
            TransformStack.cast(poseStack)
                    .translate(prevMiddle)
                    .rotateYRadians(tieAngles.y)
                    .rotateXRadians(tieAngles.x)
                    .rotateZRadians(tieAngles.z)
                    .translate(-1 / 2f, -2 / 16f - 1 / 256f, 0);
            angles.tieTransform = poseStack.last();

            // Rails
            float scale = end ? 2.2f : 2.1f;
            for (boolean first : Iterate.trueAndFalse) {
                Vec3 railI = railOffsets.get(first);
                Vec3 prevI = previousOffsets.get(first);
                Vec3 diff = railI.subtract(prevI);
                Vec3 anglesI = TrackRenderer.getModelAngles(segment.normal, diff);

                poseStack = new PoseStack();
                TransformStack.cast(poseStack)
                        .translate(prevI)
                        .rotateYRadians(anglesI.y)
                        .rotateXRadians(anglesI.x)
                        .rotateZRadians(anglesI.z)
                        .translate(0, -2 / 16f - 1 / 256f, -1 / 32f)
                        .scale(1, 1, (float) diff.length() * scale);
                angles.railTransforms.set(first, poseStack.last());
            }

            previousOffsets = railOffsets;

        }

        return bakedStandards;
    }
}
