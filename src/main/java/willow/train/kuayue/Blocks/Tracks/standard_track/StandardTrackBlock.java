package willow.train.kuayue.Blocks.Tracks.standard_track;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.schematics.requirement.ISpecialBlockItemRequirement;
import com.simibubi.create.content.trains.track.*;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;


public class StandardTrackBlock extends TrackBlock
        implements IBE<TrackBlockEntity>, IWrenchable, ITrackBlock, ISpecialBlockItemRequirement, ProperWaterloggedBlock {

    public StandardTrackBlock(Properties properties, TrackMaterial material) {
        super(properties, material);
    }

}
