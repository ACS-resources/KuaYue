package willow.train.kuayue.Blocks.Bogeys.standard_bogey;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.Transform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.nbt.CompoundTag;

import static willow.train.kuayue.init.KYBlockPartials.KYSTANDARDBOGEY_FRAME;
import static willow.train.kuayue.init.KYBlockPartials.KYSTANDARDBOGEY_WHEEL;

public class KYStandardBogeyRenderer {

    public static class SmallKYStandardBogeyRenderer extends BogeyRenderer {

        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            this.createModelInstances(materialManager, KYSTANDARDBOGEY_FRAME);
            this.createModelInstances(materialManager, KYSTANDARDBOGEY_WHEEL, 2);
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

            Transform<?>[] wheels = getTransformsFromPartial(KYSTANDARDBOGEY_WHEEL, ms, inInstancedContraption, 2);

            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1)/2];
                wheel.translate(0, 0.805, ((double)side) *1.2d)
                        .rotateX(wheelAngle);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }
}
