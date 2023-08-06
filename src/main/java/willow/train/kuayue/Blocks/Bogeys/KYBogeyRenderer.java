package willow.train.kuayue.Blocks.Bogeys;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.Transform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.nbt.CompoundTag;
import willow.train.kuayue.init.KYBogeySizes;

import static willow.train.kuayue.init.KYBlockPartials.*;

public class KYBogeyRenderer {

    public static class SingleaxleBogeyRenderer extends BogeyRenderer {

        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 1);
            createModelInstance(materialManager, SINGLEAXLE_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.SMALL;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            BogeyModelData bogeyFrame = getTransform(SINGLEAXLE_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            bogeyFrame.render(ms, light, vb);

            BogeyModelData bogeyWheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption)
                    .translate(0, 12 / 16f, 0)
                    .rotateX(wheelAngle)
                    .translate(0, -7 / 16f, 0);
            bogeyWheels.render(ms, light, vb);
        }
    }

    public static class LeafspringBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 1);
            createModelInstance(materialManager, LEAFSPRING_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.LARGE;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(LEAFSPRING_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?> bogeyWheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption)
                    .translate(0, 12 / 16f, 0)
                    .rotateX(wheelAngle)
                    .translate(0, -7 / 16f, 0);
            finalize(bogeyWheels, ms, light, vb);
        }
    }

    public static class CoilspringBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 1);
            createModelInstance(materialManager, COILSPRING_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(COILSPRING_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?> bogeyWheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption)
                    .translate(0, 12 / 16f, 0)
                    .rotateX(wheelAngle)
                    .translate(0, -7 / 16f, 0);
            finalize(bogeyWheels, ms, light, vb);
        }
    }

    //TWOAXLESONE:

    public static class FreightBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstance(materialManager, FREIGHT_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.SMALL;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(FREIGHT_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, ((double)side) *1.5d)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }

    public static class ArchbarBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstance(materialManager, ARCHBAR_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.LARGE;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(ARCHBAR_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, ((double)side) *1.5d)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }

    public static class PassengerBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstance(materialManager, PASSENGER_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(PASSENGER_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, ((double)side) *1.5d)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }

    //TWOAXLESTWO

    public static class ModernBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstance(materialManager, MODERN_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(MODERN_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, ((double)side) *1.5d)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }

    public static class BlombergBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstance(materialManager, BLOMBERG_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(BLOMBERG_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, ((double)side) *1.5d)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }

    public static class Y25BogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstance(materialManager, Y25_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(Y25_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, ((double)side) *1.5d)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }

    //TRIPLEAXLES

    public static class HeavyweightBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 3);
            createModelInstance(materialManager, HEAVYWEIGHT_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(HEAVYWEIGHT_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption, 3);
            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[side + 1];
                wheel.translate(0, 12 / 16f, ((double)side) *1.5d)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }

    public static class RadialBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
            createModelInstance(materialManager, KY_BOGEY_WHEELS, 3);
            createModelInstance(materialManager, RADIAL_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransform(RADIAL_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransform(KY_BOGEY_WHEELS, ms, inInstancedContraption, 3);
            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[side + 1];
                wheel.translate(0, 12 / 16f, ((double)side) *1.5d)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }
}
