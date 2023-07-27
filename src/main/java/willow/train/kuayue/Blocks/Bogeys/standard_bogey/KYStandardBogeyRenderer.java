package willow.train.kuayue.Blocks.Bogeys.standard_bogey;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.Transform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.nbt.CompoundTag;

import static com.simibubi.create.content.trains.entity.CarriageBogey.UPSIDE_DOWN_KEY;
import static willow.train.kuayue.init.KYBlockPartials.KYSTANDARDBOGEY_FRAME;
import static willow.train.kuayue.init.KYBlockPartials.KYSTANDARDBOGEY_WHEEL;

public class KYStandardBogeyRenderer {

    public static class SmallKYStandardBogeyRenderer extends BogeyRenderer {

        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KYSTANDARDBOGEY_WHEEL, 1);
            createModelInstances(materialManager, KYSTANDARDBOGEY_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.SMALL;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(KYSTANDARDBOGEY_FRAME, ms, inInstancedContraption);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KYSTANDARDBOGEY_WHEEL, ms, inInstancedContraption, 4);
            /*for (boolean left : Iterate.trueAndFalse) {
                for (int front : Iterate.positiveAndNegative) {
                    if (!inInstancedContraption)
                        ms.pushPose();
                    Transform<?> wheel = wheels[(left ? 1 : 0) + (front + 1)];
                    wheel.translate(left ? -12 / 16f : 12 / 16f, specialUpsideDown ? 35 /16f : 3 / 16f, front * 15 / 16f) //base position
                            .rotateY(left ? wheelAngle : -wheelAngle)
                            .translate(15/16f, 0, 0/16f);
                    finalize(wheel, ms, light, vb);
//                        .light(light)
                    //                      .renderInto(ms, vb);
                    if (!inInstancedContraption)
                        ms.popPose();
                }
            }*/
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1)/2];
                wheel.translate(0, 12 / 16f, side)
                        .rotateX(wheelAngle);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }
}
