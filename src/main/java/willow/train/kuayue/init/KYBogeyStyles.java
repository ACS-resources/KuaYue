package willow.train.kuayue.init;

import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import willow.train.kuayue.Blocks.Bogeys.df11g_bogey.DF11GBogeyRenderer;
import willow.train.kuayue.Blocks.Bogeys.standard_bogey.KYStandardBogeyRenderer;
import willow.train.kuayue.Main;

public class KYBogeyStyles {

    public static final String KY_STANDARD_BOGEY_CYCLE_GROUP = "kystandardbogey";

    public static final BogeyStyle KYSTANDARDBOGEY = create("kystandardbogey", KY_STANDARD_BOGEY_CYCLE_GROUP)
            .displayName(Components.translatable("kuayue.bogeys.styles.kystandardbogey"))
            .size(BogeySizes.SMALL, () -> KYStandardBogeyRenderer.SmallKYStandardBogeyRenderer::new, KYCreateBlock.KY_STANDARD_BOGEY)
            .size(KYBogeySizes.PK209P, () -> KYStandardBogeyRenderer.PK209PBogeyRender::new, KYCreateBlock.PK209P_BOGEY)
            .contactParticle(ParticleTypes.CRIT).smokeParticle(null).soundType(AllSoundEvents.TRAIN.getId())
            .build();

    public static final BogeyStyle DF11GBOGEY = create("df11gbogey", KY_STANDARD_BOGEY_CYCLE_GROUP)
            .displayName(Components.translatable("kuayue.bogeys.styles.df11gbogey"))
            .size(BogeySizes.SMALL, () -> DF11GBogeyRenderer.SmallDF11GBogeyRenderer::new, KYCreateBlock.DF11G_BOGEY)
            .size(KYBogeySizes.BACKWARD, () -> DF11GBogeyRenderer.BackwardDF11GBogeyRenderer::new, KYCreateBlock.DF11G_BACKWARD_BOGEY)
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
        //registerStyleCategory(Create.asResource(STANDARD_CYCLE_GROUP), CategoryIcon.standardSupplier("default_icon"));
    }
}
