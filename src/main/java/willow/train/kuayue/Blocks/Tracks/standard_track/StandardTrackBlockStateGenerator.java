package willow.train.kuayue.Blocks.Tracks.standard_track;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackBlockStateGenerator;
import com.simibubi.create.content.trains.track.TrackShape;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;
import willow.train.kuayue.Blocks.Tracks.CustomTrackBlockStateGenerator;
import willow.train.kuayue.Main;

public class StandardTrackBlockStateGenerator extends CustomTrackBlockStateGenerator {

    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        return state.getValue(StandardTrackBlock.SHAPE)
                .getModelRotation();
    }

    /*@Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        TrackShape value = state.getValue(StandardTrackBlock.SHAPE);
        if (value == TrackShape.NONE)
            return prov.models()
                    .getExistingFile(prov.mcLoc("block/air"));
        return prov.models()
                .getExistingFile(Main.asResource("block/track/" + value.getModel()));
    }*/
}
