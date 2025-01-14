package willow.train.kuayue.Blocks.Bogeys.common_renderers;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.Transform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.base.Constants;
import willow.train.kuayue.base.data.BogeyPaintColour;

public abstract class KYCommonBogeyRenderer extends BogeyRenderer {

    public abstract void render(boolean forwards, BogeyPaintColour color, float wheelAngle, PoseStack ms, int light,
                                VertexConsumer vb, boolean inContraption);

    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb,
                       boolean inContraption) {
        this.render(this.isForwards(bogeyData, inContraption), this.getColour(bogeyData), wheelAngle, ms, light, vb, inContraption);
    }

    private boolean isForwards(CompoundTag bogeyData, boolean inContraption) {
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

    @Nullable
    private BogeyPaintColour getColour(CompoundTag bogeyData) {
        if (!bogeyData.contains(Constants.BOGEY_PAINT_KEY))
            return BogeyPaintColour.UNPAINTED;
        return NBTHelper.readEnum(bogeyData, Constants.BOGEY_PAINT_KEY, BogeyPaintColour.class);
    }

    public static boolean isDirectionPosotive(Direction direction) {
        return switch (direction) { case NORTH, WEST, UP -> true; case SOUTH, DOWN, EAST -> false; };
    }

    public abstract void initialiseContraptionModelData(MaterialManager materialManager, BogeyPaintColour colour);
}

