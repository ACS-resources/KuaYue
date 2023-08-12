package willow.train.kuayue.Blocks.Bogeys.standard_bogey;

import com.jozufozu.flywheel.api.MaterialManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import willow.train.kuayue.Blocks.Bogeys.df11g_bogey.DF11GBogeyRenderer;
import willow.train.kuayue.base.Constants;
import willow.train.kuayue.init.KYBogeySizes;
import willow.train.kuayue.init.KYBogeyStyles;

import static willow.train.kuayue.init.KYBlockPartials.*;
import static willow.train.kuayue.init.KYBlockPartials.DF11G_WHEEL;

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

    public static class PK209PBogeyRender extends BogeyRenderer{
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            this.createModelInstance(materialManager,
                    PK209P_MAIN,
                    PK209P_WHEEL,
                    PK209P_WHEEL2,
                    PK209P_MOTORWHEEL);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.PK209P;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

            boolean inInstancedContraption = vb == null;
            //转向架架体
            BogeyModelData main = getTransform(PK209P_MAIN, ms, inInstancedContraption);
            //发电轮对
            BogeyModelData wheel = getTransform(PK209P_WHEEL, ms, inInstancedContraption);
            //普通轮对
            BogeyModelData wheel2 = getTransform(PK209P_WHEEL2, ms, inInstancedContraption);
            //发电机小轮
            BogeyModelData motorWheel = getTransform(PK209P_MOTORWHEEL, ms, inInstancedContraption);

            main.translate(0,0.91,0).render(ms, light, vb);
            wheel.translate(0, 0.8, 1.2)
                    .rotateX(wheelAngle)
                    .render(ms, light, vb);
            wheel2.translate(0, 0.8, -1.2)
                    .rotateX(wheelAngle)
                    .render(ms, light, vb);
            motorWheel.translate(1.117, 0.82, 2.165)
                    .rotateX(wheelAngle * 3.256)
                    .render(ms, light, vb);
        }
    }
}
