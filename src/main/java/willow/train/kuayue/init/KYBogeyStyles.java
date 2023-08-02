package willow.train.kuayue.init;

import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.resources.ResourceLocation;
import willow.train.kuayue.Blocks.Bogeys.CategoryIcon;
import willow.train.kuayue.Blocks.Bogeys.KYBogeyRenderer;
import willow.train.kuayue.Blocks.Bogeys.standard_bogey.KYStandardBogeyRenderer;
import willow.train.kuayue.Main;

import static com.simibubi.create.AllBogeyStyles.STANDARD_CYCLE_GROUP;
import static willow.train.kuayue.Blocks.Bogeys.selection_menu.BogeyCategoryHandlerClient.registerStyleCategory;

public class KYBogeyStyles {

    public static final String KY_STANDARD_BOGEY_CYCLE_GROUP = "kystandardbogey";

    public static final BogeyStyle KYSTANDARDBOGEY = create("kystandardbogey", KY_STANDARD_BOGEY_CYCLE_GROUP)
            .displayName(Components.translatable("kuayue.bogeys.styles.kystandardbogey"))
            .size(BogeySizes.SMALL, () -> KYStandardBogeyRenderer.SmallKYStandardBogeyRenderer::new, KYCreateBlock.KY_STANDARD_BOGEY)
            .build();

    public static final String SINGLEAXLE_CYCLE_GROUP = "singleaxles";
    public static final String DOUBLEAXLE_CYCLE_GROUP = "doubleaxles";
    public static final String TRIPLEAXLE_CYCLE_GROUP = "tripleaxles";

    // Single Axles
    public static final BogeyStyle
            SINGLEAXLE = create("singleaxle", SINGLEAXLE_CYCLE_GROUP)
            .size(BogeySizes.SMALL, () -> KYBogeyRenderer.SingleaxleBogeyRenderer::new, KYCreateBlock.SINGLEAXLE_BOGEY)
            .build(),
            LEAFSPRING = create("leafspring", SINGLEAXLE_CYCLE_GROUP)
                    .size(BogeySizes.SMALL, () -> KYBogeyRenderer.LeafspringBogeyRenderer::new, KYCreateBlock.SINGLEAXLE_BOGEY)
                    .build(),
            COILSPRING = create("coilspring", SINGLEAXLE_CYCLE_GROUP)
                    .size(BogeySizes.SMALL, () -> KYBogeyRenderer.CoilspringBogeyRenderer::new, KYCreateBlock.SINGLEAXLE_BOGEY)
                    .build()
                    ;

    // Double Axles
    public static final BogeyStyle
            FREIGHT = create("freight", DOUBLEAXLE_CYCLE_GROUP)
            .size(BogeySizes.SMALL, () -> KYBogeyRenderer.FreightBogeyRenderer::new, KYCreateBlock.LARGE_PLATFORM_DOUBLEAXLE_BOGEY)
            .build(),
            ARCHBAR = create("archbar", DOUBLEAXLE_CYCLE_GROUP)
                    .size(BogeySizes.SMALL, () -> KYBogeyRenderer.ArchbarBogeyRenderer::new, KYCreateBlock.LARGE_PLATFORM_DOUBLEAXLE_BOGEY)
                    .build(),
            PASSENGER = create("passenger", DOUBLEAXLE_CYCLE_GROUP)
                    .size(BogeySizes.SMALL, () -> KYBogeyRenderer.PassengerBogeyRenderer::new, KYCreateBlock.DOUBLEAXLE_BOGEY)
                    .build(),
            MODERN = create("modern", DOUBLEAXLE_CYCLE_GROUP)
                    .size(BogeySizes.SMALL, () -> KYBogeyRenderer.ModernBogeyRenderer::new, KYCreateBlock.DOUBLEAXLE_BOGEY)
                    .build(),
            BLOMBERG = create("blomberg", DOUBLEAXLE_CYCLE_GROUP)
                    .size(BogeySizes.SMALL, () -> KYBogeyRenderer.BlombergBogeyRenderer::new, KYCreateBlock.DOUBLEAXLE_BOGEY)
                    .build(),
            Y25 = create("y25", DOUBLEAXLE_CYCLE_GROUP)
                    .size(BogeySizes.SMALL, () -> KYBogeyRenderer.Y25BogeyRenderer::new, KYCreateBlock.LARGE_PLATFORM_DOUBLEAXLE_BOGEY)
                    .build()
                    ;

    // Triple Axles
    public static final BogeyStyle
            HEAVYWEIGHT = create("heavyweight", TRIPLEAXLE_CYCLE_GROUP)
            .size(BogeySizes.SMALL, () -> KYBogeyRenderer.HeavyweightBogeyRenderer::new, KYCreateBlock.TRIPLEAXLE_BOGEY)
            .build(),
            RADIAL = create("radial", TRIPLEAXLE_CYCLE_GROUP)
                    .size(BogeySizes.SMALL, () -> KYBogeyRenderer.RadialBogeyRenderer::new, KYCreateBlock.TRIPLEAXLE_BOGEY)
                    .build()
                    ;


    public static AllBogeyStyles.BogeyStyleBuilder create(String name, String cycleGroup) {
        return create(Main.asResource(name), Main.asResource(cycleGroup))
                .displayName(Components.translatable("kuayue.bogeys.styles."+name));
    }

    public static AllBogeyStyles.BogeyStyleBuilder create(String name, ResourceLocation cycleGroup) {
        return create(Main.asResource(name), cycleGroup);
    }

    public static AllBogeyStyles.BogeyStyleBuilder create(ResourceLocation name, ResourceLocation cycleGroup) {
        return new AllBogeyStyles.BogeyStyleBuilder(name, cycleGroup);
    }

    public static void register() {
        Main.LOGGER.info("Registered bogey styles from " + Main.MOD_ID);
    }

    public static void registerClient() {
        registerStyleCategory(Create.asResource(STANDARD_CYCLE_GROUP), CategoryIcon.standardSupplier("default_icon"));
        registerStyleCategory(SINGLEAXLE_CYCLE_GROUP, CategoryIcon.standardSupplier("singleaxle_icon"));
        registerStyleCategory(DOUBLEAXLE_CYCLE_GROUP, CategoryIcon.standardSupplier("freight_icon"));
        registerStyleCategory(TRIPLEAXLE_CYCLE_GROUP, CategoryIcon.standardSupplier("radial_icon"));
        //registerStyleCategory(KY_STANDARD_BOGEY_CYCLE_GROUP,CategoryIcon.standardSupplier("ky_standard_bogey_icon"));
    }
}
