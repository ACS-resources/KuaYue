package willow.train.kuayue.init;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.trains.track.TrackShape;
import willow.train.kuayue.Main;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class KYBlockPartials {

    public record ModelTransform(double x, double y, double z, float rx, float ry, float rz) {
        public static final willow.train.kuayue.init.KYBlockPartials.ModelTransform ZERO = new willow.train.kuayue.init.KYBlockPartials.ModelTransform(0, 0, 0, 0, 0, 0);

        public static willow.train.kuayue.init.KYBlockPartials.ModelTransform of(double x, double y, double z, float rx, float ry, float rz) {
            return new willow.train.kuayue.init.KYBlockPartials.ModelTransform(x, y, z, rx, ry, rz);
        }

        public static willow.train.kuayue.init.KYBlockPartials.ModelTransform of(double x, double y, double z) {
            return of(x, y, z, 0, 0, 0);
        }

        public static willow.train.kuayue.init.KYBlockPartials.ModelTransform of(double x, double z) {
            return of(x, 0, z);
        }
    }

    public static final class TrackCasingSpec {
        public final PartialModel model;
        public final willow.train.kuayue.init.KYBlockPartials.ModelTransform transform;
        public final List<willow.train.kuayue.init.KYBlockPartials.ModelTransform> additionalTransforms = new ArrayList<>();

        private willow.train.kuayue.init.KYBlockPartials.TrackCasingSpec altSpec;
        private final int topSurfacePixelHeight;

        private double xShift = 0;
        private double zShift = 0;

        public TrackCasingSpec(PartialModel model, willow.train.kuayue.init.KYBlockPartials.ModelTransform transform, int topSurfacePixelHeight) {
            this.model = model;
            this.transform = transform;
            this.topSurfacePixelHeight = topSurfacePixelHeight;
        }

        public willow.train.kuayue.init.KYBlockPartials.TrackCasingSpec addTransform(willow.train.kuayue.init.KYBlockPartials.ModelTransform transform) {
            additionalTransforms.add(transform);
            return this;
        }

        public willow.train.kuayue.init.KYBlockPartials.TrackCasingSpec addTransform(double x, double z) {
            return addTransform(willow.train.kuayue.init.KYBlockPartials.ModelTransform.of(x, z));
        }

        public willow.train.kuayue.init.KYBlockPartials.TrackCasingSpec withAltSpec(@Nullable willow.train.kuayue.init.KYBlockPartials.TrackCasingSpec altSpec) {
            this.altSpec = altSpec;
            return this;
        }

        public willow.train.kuayue.init.KYBlockPartials.TrackCasingSpec getAltSpec() {
            return altSpec != null ? altSpec : this;
        }

        public int getTopSurfacePixelHeight(boolean alt) {
            return (alt && altSpec != null) ? altSpec.topSurfacePixelHeight : topSurfacePixelHeight;
        }

        public willow.train.kuayue.init.KYBlockPartials.TrackCasingSpec overlayShift(double x, double z) {
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

    public static final EnumMap<TrackShape, willow.train.kuayue.init.KYBlockPartials.TrackCasingSpec> TRACK_CASINGS = new EnumMap<>(TrackShape.class);

    public static final PartialModel
            KY_TRACK_SEGMENT_LEFT = block("track/standard/segment_left"),
            KY_TRACK_SEGMENT_RIGHT = block("track/standard/segment_right"),
            KY_TRACK_TIE = block("track/standard/tie"),
            KYSTANDARDBOGEY_FRAME = block("bogey/ky_standard/bogey_cw2_temple"),
            KYSTANDARDBOGEY_WHEEL = block("bogey/ky_standard/cw2_wheel"),
            DF11G_FRAME = block("bogey/df11g/df11g_bogey_temple"),
            DF11G_WHEEL = block("bogey/df11g/df11g_wheel");

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
