package willow.train.kuayue.Blocks.Bogeys.df11g_bogey;

import com.jozufozu.flywheel.api.MaterialManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.base.Constants;
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

            /*if (yaw != null){
                double tickYaw = yaw.getValue();
                //System.out.println("tickYaw:"+tickYaw);
            }*/

            boolean forwards = DF11GBogeyRenderer.isForwards(bogeyData, inContraption);

            Direction direction = bogeyData.contains(Constants.BOGEY_ASSEMBLY_DIRECTION_KEY)
                    ? NBTHelper.readEnum(bogeyData, Constants.BOGEY_ASSEMBLY_DIRECTION_KEY, Direction.class)
                    : Direction.NORTH;

            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(DF11G_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(DF11G_WHEEL, ms, inInstancedContraption, 3);

            if (direction == Direction.NORTH || direction == Direction.WEST){
                if (forwards){
                    frame.translate(0,0.375,0).render(ms, light, vb);

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
                }else {
                    frame.rotateY(180).translate(0,0.375,0).render(ms, light, vb);

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
            }else {
                frame.translate(0,0.375,0).render(ms, light, vb);

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
    }

    public static class BackwardDF11GBogeyRenderer extends BogeyRenderer {


        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

            boolean forwards = DF11GBogeyRenderer.isForwards(bogeyData, inContraption);

            Direction direction = bogeyData.contains(Constants.BOGEY_ASSEMBLY_DIRECTION_KEY)
                    ? NBTHelper.readEnum(bogeyData, Constants.BOGEY_ASSEMBLY_DIRECTION_KEY, Direction.class)
                    : Direction.NORTH;

            wheelAngle = -wheelAngle;
            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(DF11G_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(DF11G_WHEEL, ms, inInstancedContraption, 3);

            if (direction == Direction.NORTH || direction == Direction.WEST){
                if (forwards){
                    frame.rotateY(180).translate(0,0.375,0).render(ms, light, vb);

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
                }else {
                    frame.translate(0,0.375,0).render(ms, light, vb);

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
            }else {
                frame.rotateY(180).translate(0,0.375,0).render(ms, light, vb);

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

    protected static boolean isForwards(CompoundTag bogeyData, boolean inContraption) {
        boolean isForwards = bogeyData.contains(Constants.BOGEY_DIRECTION_KEY) && bogeyData.getBoolean(Constants.BOGEY_DIRECTION_KEY);

        Direction direction = bogeyData.contains(Constants.BOGEY_ASSEMBLY_DIRECTION_KEY)
                ? NBTHelper.readEnum(bogeyData, Constants.BOGEY_ASSEMBLY_DIRECTION_KEY, Direction.class)
                : Direction.NORTH;

        boolean isLinked = true;
        if (bogeyData.contains(Constants.BOGEY_LINK_KEY))
            isLinked = bogeyData.getBoolean(Constants.BOGEY_LINK_KEY);

        boolean isPosotive = isDirectionPosotive(direction);

        if (isLinked && inContraption && isPosotive)
            isForwards = !isForwards;

        return isPosotive == isForwards;
    }

    public static boolean isDirectionPosotive(Direction direction) {
        return switch (direction) { case NORTH, WEST, UP -> true; case SOUTH, DOWN, EAST -> false; };
    }
}
