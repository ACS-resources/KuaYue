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

    public static final Map<DyeColor, PartialModel> TOOLBOX_BODIES = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, PartialModel> CONDUCTOR_WHISTLE_FLAGS = new EnumMap<>(DyeColor.class);
    public static final Map<String, PartialModel> CUSTOM_CONDUCTOR_CAPS = new HashMap<>();
    public static final Map<String, PartialModel> CUSTOM_CONDUCTOR_ONLY_CAPS = new HashMap<>();
    public static final Map<String, ResourceLocation> CUSTOM_CONDUCTOR_SKINS = new HashMap<>();
    public static final Set<String> NO_TILT_CAPS = new HashSet<>();
    public static final Map<String, ResourceLocation> CUSTOM_CONDUCTOR_SKINS_FOR_NAME = new HashMap<>(); // for if a conductor is renamed, rather than the cap

    public static void registerCustomCap(String itemName, String modelLoc) {
        CUSTOM_CONDUCTOR_CAPS.put(itemName, new PartialModel(Main.asResource("item/dev_caps/"+modelLoc)));
    }

    public static void registerCustomCap(String itemName, String modelLoc, boolean preventTilting) {
        CUSTOM_CONDUCTOR_CAPS.put(itemName, new PartialModel(Main.asResource("item/dev_caps/"+modelLoc)));
        if (preventTilting)
            NO_TILT_CAPS.add(itemName);
    }

    public static void registerCustomConductorOnlyCap(String itemName, String modelLoc) {
        CUSTOM_CONDUCTOR_ONLY_CAPS.put(itemName, new PartialModel(Main.asResource("item/dev_caps/"+modelLoc)));
    }

    public static void registerCustomConductorOnlyCap(String itemName, String modelLoc, boolean preventTilting) {
        CUSTOM_CONDUCTOR_ONLY_CAPS.put(itemName, new PartialModel(Main.asResource("item/dev_caps/"+modelLoc)));
        if (preventTilting)
            NO_TILT_CAPS.add(itemName);
    }

    public static boolean shouldPreventTiltingCap(String name) {
        return NO_TILT_CAPS.contains(name);
    }

    public static void registerCustomSkin(String itemName, String textureLoc) {
        CUSTOM_CONDUCTOR_SKINS.put(itemName, Main.asResource("textures/entity/custom_conductors/"+textureLoc));
    }

    public static void registerCustomConductorNameBasedSkin(String conductorName, String textureLoc) {
        CUSTOM_CONDUCTOR_SKINS_FOR_NAME.put(conductorName, Main.asResource("textures/entity/custom_conductors/"+textureLoc));
    }

/*    public static final PartialModel
            SEMAPHORE_ARM_RED = block("semaphore/red_arm"),
            SEMAPHORE_ARM_YELLOW = block("semaphore/yellow_arm"),
            SEMAPHORE_ARM_RED_FLIPPED = block("semaphore/red_arm_flipped"),
            SEMAPHORE_ARM_YELLOW_FLIPPED = block("semaphore/yellow_arm_flipped"),
            SEMAPHORE_ARM_RED_UPSIDE_DOWN = block("semaphore/red_arm_down"),
            SEMAPHORE_ARM_YELLOW_UPSIDE_DOWN = block("semaphore/yellow_arm_down"),
            SEMAPHORE_ARM_RED_FLIPPED_UPSIDE_DOWN = block("semaphore/red_arm_flipped_down"),
            SEMAPHORE_ARM_YELLOW_FLIPPED_UPSIDE_DOWN = block("semaphore/yellow_arm_flipped_down"),
            SEMAPHORE_LAMP_RED = block("semaphore/red_lamp"),
            SEMAPHORE_LAMP_YELLOW = block("semaphore/yellow_lamp"),
            SEMAPHORE_LAMP_WHITE = block("semaphore/white_lamp");*/

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

    public static final PartialModel TRACK_CASING_FLAT = block("track_casing/flat");
    public static final PartialModel TRACK_CASING_FLAT_THICK = block("track_casing/flat_thick");

    static {
        PartialModel xo = block("track_casing/xo");
        PartialModel zo = block("track_casing/zo");
        PartialModel pd = block("track_casing/pd");
        PartialModel nd = block("track_casing/nd");
        PartialModel cr_o = block("track_casing/cr_o");

        TrackCasingSpec spec_3x3 = new TrackCasingSpec(TRACK_CASING_FLAT, ModelTransform.ZERO, 3)
                .addTransform(1, 1).addTransform(0, 1).addTransform(-1, 1)
                .addTransform(1, 0).addTransform(-1, 0)
                .addTransform(1, -1).addTransform(0, -1).addTransform(-1, -1);

        TrackCasingSpec flat_x = new TrackCasingSpec(TRACK_CASING_FLAT, ModelTransform.ZERO, 3)
                .addTransform(0, 1).addTransform(0, -1);
        TrackCasingSpec flat_z = new TrackCasingSpec(TRACK_CASING_FLAT, ModelTransform.ZERO, 3)
                .addTransform(1, 0).addTransform(-1, 0);

        TRACK_CASINGS.put(TrackShape.XO, new TrackCasingSpec(xo, ModelTransform.ZERO, 8).withAltSpec(flat_x));
        TRACK_CASINGS.put(TrackShape.ZO, new TrackCasingSpec(zo, ModelTransform.ZERO, 8).withAltSpec(flat_z));


        TRACK_CASINGS.put(TrackShape.PD, new TrackCasingSpec(pd, ModelTransform.ZERO, 8)
                .withAltSpec(new TrackCasingSpec(TRACK_CASING_FLAT, ModelTransform.ZERO, 3)
                        .addTransform(1, 0).addTransform(1, -1)
                        .addTransform(0, 1).addTransform(-1, 1)
                ));
        TRACK_CASINGS.put(TrackShape.ND, new TrackCasingSpec(nd, ModelTransform.ZERO, 8)
                .withAltSpec(new TrackCasingSpec(TRACK_CASING_FLAT, ModelTransform.ZERO, 3)
                        .addTransform(-1, 0).addTransform(-1, -1)
                        .addTransform(0, 1).addTransform(1, 1)
                ));

        TRACK_CASINGS.put(TrackShape.TE, new TrackCasingSpec(xo, ModelTransform.ZERO, 8).withAltSpec(flat_x));
        TRACK_CASINGS.put(TrackShape.TW, new TrackCasingSpec(xo, ModelTransform.ZERO, 8).withAltSpec(flat_x));
        TRACK_CASINGS.put(TrackShape.TN, new TrackCasingSpec(zo, ModelTransform.ZERO, 8).withAltSpec(flat_z));
        TRACK_CASINGS.put(TrackShape.TS, new TrackCasingSpec(zo, ModelTransform.ZERO, 8).withAltSpec(flat_z));

        TRACK_CASINGS.put(TrackShape.CR_O, new TrackCasingSpec(cr_o, ModelTransform.ZERO, 8).withAltSpec(spec_3x3));

        TRACK_CASINGS.put(TrackShape.CR_D, new TrackCasingSpec(TRACK_CASING_FLAT, ModelTransform.ZERO, 3)
                .addTransform(1, 2).addTransform(0, 2).addTransform(-1, 2)
                .addTransform(2, 1).addTransform(1, 1).addTransform(0, 1).addTransform(-1, 1).addTransform(-2, 1)
                .addTransform(2, 0).addTransform(1, 0).addTransform(-1, 0).addTransform(-2, 0)
                .addTransform(1, -1).addTransform(0, -1).addTransform(-1, -1)
                .addTransform(0, -2)
                .withAltSpec(spec_3x3)
        );

        TRACK_CASINGS.put(TrackShape.CR_PDX, spec_3x3);
        TRACK_CASINGS.put(TrackShape.CR_PDZ, spec_3x3);
        TRACK_CASINGS.put(TrackShape.CR_NDX, spec_3x3);
        TRACK_CASINGS.put(TrackShape.CR_NDZ, spec_3x3);

        TRACK_CASINGS.put(TrackShape.AN, new TrackCasingSpec(block("track_casing/an"), ModelTransform.ZERO, 5).overlayShift(0, 3 / 16f));
        TRACK_CASINGS.put(TrackShape.AS, new TrackCasingSpec(block("track_casing/as"), ModelTransform.ZERO, 5).overlayShift(0, -3 / 16f));
        TRACK_CASINGS.put(TrackShape.AE, new TrackCasingSpec(block("track_casing/ae"), ModelTransform.ZERO, 5).overlayShift(-3 / 16f, 0));
        TRACK_CASINGS.put(TrackShape.AW, new TrackCasingSpec(block("track_casing/aw"), ModelTransform.ZERO, 5).overlayShift(3 / 16f, 0));
    }

    /*public static final PartialModel
            COUPLER_COUPLE = block("track_overlay/coupler_couple"),
            COUPLER_DECOUPLE = block("track_overlay/coupler_decouple"),
            COUPLER_BOTH = block("track_overlay/coupler_both"),
            COUPLER_NONE = block("track_overlay/coupler_none");*/

    public static final PartialModel
            ANDESITE_SWITCH_FLAG = block("track_switch_andesite/flag"),
            ANDESITE_SWITCH_HANDLE = block("track_switch_andesite/handle"),
            BRASS_SWITCH_FLAG = block("track_switch_brass/flag"),
            SWITCH_NONE = block("track_overlay/switch_none"),
            SWITCH_RIGHT_STRAIGHT = block("track_overlay/switch_right_straight"),
            SWITCH_RIGHT_TURN = block("track_overlay/switch_right_turn"),
            SWITCH_LEFT_STRAIGHT = block("track_overlay/switch_left_straight"),
            SWITCH_LEFT_TURN = block("track_overlay/switch_left_turn"),
            SWITCH_3WAY_STRAIGHT = block("track_overlay/switch_3way_straight"),
            SWITCH_3WAY_LEFT = block("track_overlay/switch_3way_left"),
            SWITCH_3WAY_RIGHT = block("track_overlay/switch_3way_right"),
            SWITCH_2WAY_LEFT = block("track_overlay/switch_2way_left"),
            SWITCH_2WAY_RIGHT = block("track_overlay/switch_2way_right")
                    ;

    public static final PartialModel
            KY_TRACK_SEGMENT_LEFT = block("track/standard/segment_left"),
            KY_TRACK_SEGMENT_RIGHT = block("track/standard/segment_right"),
            KY_TRACK_TIE = block("track/standard/tie"),
            KYSTANDARDBOGEY_FRAME = block("bogey/ky_standard/bogey_cw2_temple"),
            KYSTANDARDBOGEY_WHEEL = block("bogey/ky_standard/cw2_wheel");

    public static final PartialModel
            SINGLEAXLE_FRAME = block("bogey/singleaxle/singleaxle_frame"),
            LEAFSPRING_FRAME = block("bogey/leafspring/leafspring_frame"),
            COILSPRING_FRAME = block("bogey/coilspring/coilspring_frame"),
            FREIGHT_FRAME = block("bogey/freight/freight_frame"),
            ARCHBAR_FRAME = block("bogey/archbar/archbar_frame"),
            PASSENGER_FRAME = block("bogey/passenger/passenger_frame"),
            MODERN_FRAME = block("bogey/modern/modern_frame"),
            BLOMBERG_FRAME = block("bogey/blomberg/blomberg_frame"),
            Y25_FRAME = block("bogey/y25/y25_frame"),
            HEAVYWEIGHT_FRAME = block("bogey/heavyweight/heavyweight_frame"),
            RADIAL_FRAME = block("bogey/radial/radial_frame"),
            KY_BOGEY_WHEELS = block("bogey/wheels/cr_bogey_wheels");

    public static final PartialModel DIESEL_STACK_FAN = block("smokestack/block_diesel_fan");
    public static final PartialModel CONDUCTOR_ANTENNA = block("conductor_antenna");

    private static PartialModel createBlock(String path) {
        return new PartialModel(Main.asResource("block/" + path));
    }

    private static PartialModel block(String path) {
        return new PartialModel(Main.asResource("block/" + path));
    }

    static {
        for (DyeColor color : DyeColor.values()) {
            TOOLBOX_BODIES.put(color, createBlock(Lang.asId(color.name()) + "_toolbox"));
            CONDUCTOR_WHISTLE_FLAGS.put(color, block("conductor_whistle/flag_"+Lang.asId(color.name())));
        }
    }

    @SuppressWarnings("EmptyMethod")
    public static void init() {
    }
}