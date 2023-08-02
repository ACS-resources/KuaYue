package willow.train.kuayue.Blocks.Bogeys;

import com.simibubi.create.content.trains.bogey.BogeySizes;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.init.KYBogeyStyles;

public class TripleAxleBogeyBlock extends KYBogeyBlock {

    public TripleAxleBogeyBlock(Properties props) {
        super(props, KYBogeyStyles.HEAVYWEIGHT, BogeySizes.SMALL);
    }

    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 6 / 32f, 42 / 32f);
    }

    @Override
    public double getWheelPointSpacing() {
        return 3;
    }
}
