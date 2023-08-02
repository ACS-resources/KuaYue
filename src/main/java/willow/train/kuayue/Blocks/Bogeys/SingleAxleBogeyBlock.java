package willow.train.kuayue.Blocks.Bogeys;

import com.simibubi.create.content.trains.bogey.BogeySizes;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.init.KYBogeyStyles;

public class SingleAxleBogeyBlock extends KYBogeyBlock {

    public SingleAxleBogeyBlock(BlockBehaviour.Properties props) {
        super(props, KYBogeyStyles.SINGLEAXLE, BogeySizes.SMALL);
    }

    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 7 / 32f, 8 / 32f);
    }

    @Override
    public double getWheelPointSpacing() {
        return 1;
    }
}
