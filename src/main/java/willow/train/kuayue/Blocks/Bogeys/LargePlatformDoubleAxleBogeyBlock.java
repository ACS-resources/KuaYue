package willow.train.kuayue.Blocks.Bogeys;

import com.simibubi.create.content.trains.bogey.BogeySizes;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.init.KYBogeyStyles;

public class LargePlatformDoubleAxleBogeyBlock extends KYBogeyBlock {

    public LargePlatformDoubleAxleBogeyBlock(Properties props) {
        super(props, KYBogeyStyles.FREIGHT, BogeySizes.SMALL);
    }

    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 5 / 32f, 42 / 32f);
    }
}
