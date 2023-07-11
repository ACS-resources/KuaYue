package willow.train.kuayue.init;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import willow.train.kuayue.Blocks.Tracks.standard_track.StandardTrackBlock;
import willow.train.kuayue.Main;

import static com.simibubi.create.content.trains.track.TrackMaterialFactory.make;

public class KYTrackMaterials {

    public static final TrackMaterial
        STANDARD = make(Main.asResource("standard"))
            .lang("Standard")
            .block(() -> KYCreateBlock.STANDARD_TRACK)
            .particle(Create.asResource("block/palettes/stone_types/polished/andesite_cut_polished"))
            .sleeper(Blocks.ANDESITE_SLAB)
            .defaultModels()
            .build();
        /*这里改动一点点就容易抛异常退出世界。*/

    public static class KYTrackType extends TrackMaterial.TrackType{

        public static final TrackMaterial.TrackType STANDARD = new KYTrackType(Main.asResource("standard")
                , StandardTrackBlock::new);

        public KYTrackType(ResourceLocation id, TrackBlockFactory factory) {
            super(id, factory);
        }
    }

    public static void register(){}
}
