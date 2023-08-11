package willow.train.kuayue.Blocks.Bogeys.df11g_bogey;

import com.jozufozu.flywheel.api.MaterialManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import net.minecraft.nbt.CompoundTag;
import willow.train.kuayue.init.KYBogeySizes;

import static willow.train.kuayue.init.KYBlockPartials.*;

public class DF11GBogeyRenderer {

    public static class SmallDF11GBogeyRenderer extends BogeyRenderer {

        //public static LerpedFloat yaw;

        //public static LerpedFloat pitch;

        //public static Couple<Vec3> couplingAnchors;

        //public static int tickCount = 0;

        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            this.createModelInstance(materialManager, DF11G_FRAME);
            this.createModelInstance(materialManager, DF11G_WHEEL, 3);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.SMALL;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(DF11G_FRAME, ms, inInstancedContraption);
            frame.translate(0,0.375,0).render(ms, light, vb);

            BogeyModelData[] wheels = getTransform(DF11G_WHEEL, ms, inInstancedContraption, 3);

            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption)
                    ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                wheel.translate(0, 0.88, ((double)side) * 2d)
                        .rotateX(wheelAngle)
                        .render(ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }

    public static class BackwardDF11GBogeyRenderer extends BogeyRenderer {


        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

            wheelAngle = -wheelAngle;
            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(DF11G_FRAME, ms, inInstancedContraption);
            frame.rotateY(180).translate(0,0.375,0).render(ms, light, vb);

            BogeyModelData[] wheels = getTransform(DF11G_WHEEL, ms, inInstancedContraption, 3);

            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption)
                    ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                wheel.translate(0, 0.88, ((double)side) * 2d)
                        .rotateX(-wheelAngle)
                        .render(ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.BACKWARD;
        }

        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            this.createModelInstance(materialManager, DF11G_FRAME);
            this.createModelInstance(materialManager, DF11G_WHEEL, 3);
        }
    }
}
