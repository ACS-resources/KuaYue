package willow.train.kuayue.init;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.trains.track.TrackShape;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import willow.train.kuayue.Main;

import javax.annotation.Nullable;
import java.util.*;

public class KYBlockPartials {

    public record ModelTransform(double x, double y, double z, float rx, float ry, float rz) {
        public static final ModelTransform ZERO = new ModelTransform(0, 0, 0, 0, 0, 0);

        //                                                            pitch?    YAW       roll?
        public static ModelTransform of(double x, double y, double z, float rx, float ry, float rz) {
            return new ModelTransform(x, y, z, rx, ry, rz);
        }

        public static ModelTransform of(double x, double y, double z) {
            return of(x, y, z, 0, 0, 0);
        }

        public static ModelTransform of(double x, double z) {
            return of(x, 0, z);
        }
    }

    public static final class TrackCasingSpec {
        public final PartialModel model;
        public final ModelTransform transform;
        public final List<ModelTransform> additionalTransforms = new ArrayList<>();

        private TrackCasingSpec altSpec;
        private final int topSurfacePixelHeight;

        private double xShift = 0;
        private double zShift = 0;

        public TrackCasingSpec(PartialModel model, ModelTransform transform, int topSurfacePixelHeight) {
            this.model = model;
            this.transform = transform;
            this.topSurfacePixelHeight = topSurfacePixelHeight;
        }

        public TrackCasingSpec addTransform(ModelTransform transform) {
            additionalTransforms.add(transform);
            return this;
        }

        public TrackCasingSpec addTransform(double x, double z) {
            return addTransform(ModelTransform.of(x, z));
        }

        public TrackCasingSpec withAltSpec(@Nullable TrackCasingSpec altSpec) {
            this.altSpec = altSpec;
            return this;
        }

        public TrackCasingSpec getAltSpec() {
            return altSpec != null ? altSpec : this;
        }

        public int getTopSurfacePixelHeight(boolean alt) {
            return (alt && altSpec != null) ? altSpec.topSurfacePixelHeight : topSurfacePixelHeight;
        }

        public TrackCasingSpec overlayShift(double x, double z) {
            this.xShift = x;
            this.zShift = z;
            return this;
        }

        public double getXShift() {
            return xShift;
        }

        public double getZShift() {
            return zShift;
        }
    }

    public static final EnumMap<TrackShape, TrackCasingSpec> TRACK_CASINGS = new EnumMap<>(TrackShape.class);

    public static final PartialModel
            KY_TRACK_SEGMENT_LEFT = block("track/standard/segment_left"),
            KY_TRACK_SEGMENT_RIGHT = block("track/standard/segment_right"),
            KY_TRACK_TIE = block("track/standard/tie"),
            KYSTANDARDBOGEY_FRAME = block("bogey/ky_standard/bogey_cw2_temple"),
            KYSTANDARDBOGEY_WHEEL = block("bogey/ky_standard/cw2_wheel");

    private static PartialModel createBlock(String path) {
        return new PartialModel(Main.asResource("block/" + path));
    }

    private static PartialModel block(String path) {
        return new PartialModel(Main.asResource("block/" + path));
    }

    @SuppressWarnings("EmptyMethod")
    public static void init() {
    }
}
