package willow.train.kuayue.Blocks.Tracks.standard_track;

import com.simibubi.create.content.trains.track.TrackBlockOutline;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class StandardTrackBlockOutline extends TrackBlockOutline {

    public static final VoxelShape STANDARD_LONG_CROSS = Shapes.or(StandardTrackVoxelShapes.longOrthogonalZ(), StandardTrackVoxelShapes.longOrthogonalX());
    public static final VoxelShape STANDARD_LONG_ORTHO = StandardTrackVoxelShapes.longOrthogonalZ();
    public static final VoxelShape STANDARD_LONG_ORTHO_OFFSET = StandardTrackVoxelShapes.longOrthogonalZOffset();

    /*public static final Map<VoxelShape, VoxelShape> TRACK_TO_STANDARD = Map.of(
            AllShapes.TRACK_ORTHO.get(Direction.EAST), KYShapes.STANDARD_LONG_ORTHO.get(Direction.EAST),
            AllShapes.TRACK_ORTHO.get(Direction.SOUTH), KYShapes.STANDARD_LONG_ORTHO.get(Direction.SOUTH),
            AllShapes.TRACK_CROSS, KYShapes.STANDARD_LONG_CROSS
//			AccessorTrackBlockOutline.getLONG_ORTHO_OFFSET(), MONORAIL_LONG_ORTHO_OFFSET,
//			AccessorTrackBlockOutline.getLONG_ORTHO(), MONORAIL_LONG_ORTHO,
//			AccessorTrackBlockOutline.getLONG_CROSS(), MONORAIL_LONG_CROSS
    );*/

    public static VoxelShape convert(Object o, boolean standard) {
        if (o instanceof VoxelShape shape)
            return convert(shape, standard);
        throw new IllegalArgumentException("object is not a VoxelShape");
    }

    /*public static VoxelShape convert(VoxelShape trackShape, boolean standard) {
        return standard ? TRACK_TO_STANDARD.getOrDefault(trackShape, trackShape) : trackShape;
    }*/
}
