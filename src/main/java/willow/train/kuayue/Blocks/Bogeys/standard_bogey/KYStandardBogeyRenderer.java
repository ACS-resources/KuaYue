package willow.train.kuayue.Blocks.Bogeys.standard_bogey;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.Transform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.nbt.CompoundTag;

import static willow.train.kuayue.init.KYBlockPartials.KYSTANDARDBOGEY_FRAME;
import static willow.train.kuayue.init.KYBlockPartials.KYSTANDARDBOGEY_WHEEL;

public class KYStandardBogeyRenderer {

    public static class SmallKYStandardBogeyRenderer extends BogeyRenderer {

        /**
         * 方法变更
         * <p>
         * 1. 传参增加一个 CarriageBogey 参数
         * <p>
         * 2. createModelInstances() -> createModelInstance()
         * <p>
         * @param materialManager The material manager
         * @param carriageBogey The bogey to create data for
         */
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            this.createModelInstance(materialManager, KYSTANDARDBOGEY_FRAME);
            this.createModelInstance(materialManager, KYSTANDARDBOGEY_WHEEL, 2);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.SMALL;
        }

        /**
         * 方法变更
         * <p>
         * 1. Transform -> BogeyModelData
         * <p>
         * 2. getTransformPartialModel -> getTransForm
         * <p>
         * 3. finalize() 已经被弃用 -> 调用 BogeyModelData.render()
         * <p>
         * @param bogeyData Custom data stored on the bogey able to be used for rendering
         * @param wheelAngle The angle of the wheel
         * @param ms The posestack to render to
         * @param light (Optional) Light used for in-world rendering
         * @param vb (Optional) Vertex Consumer used for in-world rendering
         * @param inContraption
         */
        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

            boolean inInstancedContraption = vb == null;
            BogeyModelData frame = getTransform(KYSTANDARDBOGEY_FRAME, ms, inInstancedContraption);
            frame.render(ms, light, vb);

            BogeyModelData[] wheels = getTransform(KYSTANDARDBOGEY_WHEEL, ms, inInstancedContraption, 2);

            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                BogeyModelData wheel = wheels[(side + 1)/2];
                wheel.translate(0, 0.805, ((double)side) *1.2d)
                        .rotateX(wheelAngle);
                wheel.render(ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }
}
