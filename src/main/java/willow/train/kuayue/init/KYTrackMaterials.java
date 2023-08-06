package willow.train.kuayue.init;

import com.google.common.collect.ImmutableSet;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import willow.train.kuayue.Blocks.Tracks.standard_track.StandardTrackBlock;
import willow.train.kuayue.Main;
import willow.train.kuayue.mixin.AccessorBlockEntityType;

import java.util.Set;

import static com.simibubi.create.content.trains.track.TrackMaterialFactory.make;

public class KYTrackMaterials {

    public static final TrackMaterial
        STANDARD = make(Main.asResource("standard"))
            .lang("KY_Standard")
            .block(() -> KYCreateBlock.STANDARD_TRACK)
            .particle(Create.asResource("block/palettes/stone_types/polished/andesite_cut_polished"))
            .trackType(KYTrackType.STANDARD)
            .sleeper(Blocks.ANDESITE_SLAB)
            .defaultModels()
            .build()
        ;

    public static class KYTrackType extends TrackMaterial.TrackType{

        public static final TrackMaterial.TrackType STANDARD = new KYTrackType(Main.asResource("standard")
                , StandardTrackBlock::new);

        public KYTrackType(ResourceLocation id, TrackBlockFactory factory) {
            super(id, factory);
        }
    }

    protected KYTrackMaterials trackMaterial;

    public KYTrackMaterials getMaterial() {
        return trackMaterial;
    }

    public static void register(){}

    public static void addToBlockEntityType(TrackBlock block) {
        BlockEntityType<?> type;
        try {
            type = block.getBlockEntityType();
        } catch (NullPointerException ignored) {
            return;
        }
        Set<Block> validBlocks = ((AccessorBlockEntityType) type).getValidBlocks();
        validBlocks = new ImmutableSet.Builder<Block>()
                .add(validBlocks.toArray(Block[]::new))
                .add(block)
                .build();
        ((AccessorBlockEntityType) type).setValidBlocks(validBlocks);
    }
}
