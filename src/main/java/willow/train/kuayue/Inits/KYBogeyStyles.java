package willow.train.kuayue.Inits;

import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.resources.ResourceLocation;
import willow.train.kuayue.Blocks.Bogeys.CategoryIcon;
import willow.train.kuayue.Blocks.Bogeys.standard_bogey.KYStandardBogeyRenderer;
import willow.train.kuayue.Main;
import willow.train.kuayue.init.KYCreateBlock;

import static com.simibubi.create.AllBogeyStyles.STANDARD_CYCLE_GROUP;
import static willow.train.kuayue.Blocks.Bogeys.selection_menu.BogeyCategoryHandlerClient.registerStyleCategory;

public class KYBogeyStyles {

    public static final String KY_STANDARD_BOGEY_CYCLE_GROUP = "kystandardbogey";

    public static final BogeyStyle KYSTANDARDBOGEY = create("kystandardbogey", Create.asResource(STANDARD_CYCLE_GROUP))
            .displayName(Components.translatable("kuayue.bogeys.styles.kystandardbogey"))
            .size(BogeySizes.SMALL, () -> KYStandardBogeyRenderer.SmallKYStandardBogeyRenderer::new, KYCreateBlock.KY_STANDARD_BOGEY)
            .build();

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
    }
}
