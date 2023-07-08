package willow.train.kuayue.init;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import static com.simibubi.create.content.trains.track.TrackMaterialFactory.make;

public class KYTrackMaterials {

    public static final TrackMaterial
        STANDARD = make(Create.asResource("standard"))
            .lang("Standard")
            .block(NonNullSupplier.lazy(() -> KYCreateBlock.STANDARD_TRACK))
            .particle(Create.asResource("block/palettes/stone_types/polished/andesite_cut_polished"))
            .defaultModels()
            .build();
}
