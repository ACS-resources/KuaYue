package willow.train.kuayue.Blocks.Bogeys;

import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.init.KYBogeyStyles;

public class DoubleAxleBogeyBlock extends KYBogeyBlock{

    public DoubleAxleBogeyBlock(Properties props) {
        this(props, KYBogeyStyles.PASSENGER, BogeySizes.SMALL);
    }

    protected DoubleAxleBogeyBlock(Properties props, BogeyStyle style, BogeySizes.BogeySize size) {
        super(props, style, size);
    }

    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 7 / 32f, 8 / 32f);
    }
}
