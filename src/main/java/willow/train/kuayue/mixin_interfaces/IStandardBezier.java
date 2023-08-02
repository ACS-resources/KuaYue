package willow.train.kuayue.mixin_interfaces;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.core.BlockPos;

public interface IStandardBezier {

    StandardAngles[] getBakedStandards();


    class StandardAngles {
        public PoseStack.Pose tieTransform;
        public Couple<PoseStack.Pose> railTransforms;
        public BlockPos lightPosition;
    }
}
