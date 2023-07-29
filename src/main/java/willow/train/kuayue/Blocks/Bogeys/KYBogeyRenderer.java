package willow.train.kuayue.Blocks.Bogeys;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.Transform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.nbt.CompoundTag;
import willow.train.kuayue.init.KYBogeySizes;

import static willow.train.kuayue.init.KYBlockPartials.*;

public class KYBogeyRenderer {

    public static class SingleaxleBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 1);
            createModelInstances(materialManager, SINGLEAXLE_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.SMALL;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(SINGLEAXLE_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?> bogeyWheels = getTransformFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption)
                    .translate(0, 12 / 16f, 0)
                    .rotateX(wheelAngle)
                    .translate(0, -7 / 16f, 0);
            finalize(bogeyWheels, ms, light, vb);
        }
    }

    public static class LeafspringBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 1);
            createModelInstances(materialManager, LEAFSPRING_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.LARGE;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(LEAFSPRING_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?> bogeyWheels = getTransformFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption)
                    .translate(0, 12 / 16f, 0)
                    .rotateX(wheelAngle)
                    .translate(0, -7 / 16f, 0);
            finalize(bogeyWheels, ms, light, vb);
        }
    }

    public static class CoilspringBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 1);
            createModelInstances(materialManager, COILSPRING_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(COILSPRING_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?> bogeyWheels = getTransformFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption)
                    .translate(0, 12 / 16f, 0)
                    .rotateX(wheelAngle)
                    .translate(0, -7 / 16f, 0);
            finalize(bogeyWheels, ms, light, vb);
        }
    }

    //TWOAXLESONE:

    public static class FreightBogeyRenderer extends BogeyRenderer {
        @Override
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstances(materialManager, FREIGHT_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.SMALL;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(FREIGHT_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, side)
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
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstances(materialManager, ARCHBAR_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return BogeySizes.LARGE;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(ARCHBAR_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, side)
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
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstances(materialManager, PASSENGER_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(PASSENGER_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, side)
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
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstances(materialManager, MODERN_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(MODERN_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, side)
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
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstances(materialManager, BLOMBERG_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(BLOMBERG_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, side)
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
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 2);
            createModelInstances(materialManager, Y25_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(Y25_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption, 2);
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 12 / 16f, side)
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
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 3);
            createModelInstances(materialManager, HEAVYWEIGHT_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(HEAVYWEIGHT_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption, 3);
            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[side + 1];
                wheel.translate(0, 12 / 16f, side*1.5)
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
        public void initialiseContraptionModelData(MaterialManager materialManager) {
            createModelInstances(materialManager, KY_BOGEY_WHEELS, 3);
            createModelInstances(materialManager, RADIAL_FRAME);
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return KYBogeySizes.EXTRA;
        }

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean inInstancedContraption = vb == null;
            Transform<?> transform = getTransformFromPartial(RADIAL_FRAME, ms, inInstancedContraption)
                    .translate(0, 5 / 16f, 0);
            finalize(transform, ms, light, vb);

            Transform<?>[] wheels = getTransformsFromPartial(KY_BOGEY_WHEELS, ms, inInstancedContraption, 3);
            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption)
                    ms.pushPose();
                Transform<?> wheel = wheels[side + 1];
                wheel.translate(0, 12 / 16f, side*1.5)
                        .rotateX(wheelAngle)
                        .translate(0, -7 / 16f, 0);
                finalize(wheel, ms, light, vb);
                if (!inInstancedContraption)
                    ms.popPose();
            }
        }
    }
}