package willow.train.kuayue.Blocks.Bogeyss.standard_bogey;

import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackShape;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import willow.train.kuayue.Blocks.Tracks.standard_track.StandardTrackBlock;
import willow.train.kuayue.Main;
import willow.train.kuayue.init.KYTrackMaterials;

public class StandardTrackBlockStateGenerator extends SpecialBlockStateGen {

    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        return state.getValue(TrackBlock.SHAPE)
                .getModelRotation();
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        TrackMaterial material = ((TrackBlock) ctx.getEntry()).getMaterial();
        String prefix = "block/";

        TrackShape value = state.getValue(StandardTrackBlock.SHAPE);
        if (value == TrackShape.NONE)
            return prov.models()
                    .getExistingFile(prov.mcLoc("block/air"));

        /*ModelFile.ExistingModelFile builder = prov.models()
                .getExistingFile(Main.asResource("block/track/standard" + value.getModel()));*/
        BlockModelBuilder builder = prov.models()
                .withExistingParent("block/track/standard/" + value.getModel(),
                        //Create.asResource("block/track/" + value.getModel()))
                        Main.asResource("block/track/standard/" + value.getModel()))
                .texture("particle", material.particle);

        for (String k : new String[]{"segment_left", "segment_right", "tie"}) { // obj_track
            prov.models()
                    .withExistingParent("block/track/standard/" + k,
                            Main.asResource("block/track/standard/" + k))
                    .texture("0", prefix + "standard_track")
                    .texture("1", prefix + "standard_track_mip")
                    .texture("particle", KYTrackMaterials.STANDARD.particle);
        }

        return builder;
    }
}

