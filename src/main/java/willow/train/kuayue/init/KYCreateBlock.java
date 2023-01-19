package willow.train.kuayue.init;

import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import willow.train.kuayue.Main;
import willow.train.kuayue.Blocks.Locos.df11g.DF11GBogeyBlock;
import willow.train.kuayue.Blocks.Locos.df11g.DF11GFrontBlock;
import willow.train.kuayue.Blocks.Locos.df11g.FakeDF11GBogeyBlock;
import willow.train.kuayue.Blocks.Supplier.FakeDF11GBogeyBehavior;
import willow.train.kuayue.Items.ToolTipsItemHelper;

public class KYCreateBlock {
    private static final CreateRegistrate REGISTRATE = Main.registrate();

    public static final BlockEntry<DF11GFrontBlock> DF11G_FRONT_BLOCK = REGISTRATE
            .creativeModeTab(() -> Main.KUAYUE_LOCOS)
            .block("df11g_front2", DF11GFrontBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.COLOR_BLUE))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(axeOrPickaxe())
            // .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(BlockStressDefaults.setImpact(2.0))
            // .onRegister(CreateRegistrate.connectedTextures(CrafterCTBehaviour::new))
            // .addLayer(() -> RenderType::cutoutMipped)
            .item(ToolTipsItemHelper::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<DF11GFrontBlock> HXD3D_FRONT_BLOCK = REGISTRATE
            .creativeModeTab(() -> Main.KUAYUE_LOCOS)
            .block("hxd3d_front", DF11GFrontBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.COLOR_BLUE))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(axeOrPickaxe())
            // .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(BlockStressDefaults.setImpact(2.0))
            // .onRegister(CreateRegistrate.connectedTextures(CrafterCTBehaviour::new))
            // .addLayer(() -> RenderType::cutoutMipped)
            .item(ToolTipsItemHelper::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<DF11GBogeyBlock> DF11G_BOGEY = REGISTRATE.creativeModeTab(() -> Main.KUAYUE_MAIN)
            .block("df11g_bogey", p -> new DF11GBogeyBlock(p, true))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BuilderTransformers.bogey())
            .register();
    public static final BlockEntry<FakeDF11GBogeyBlock> fake_DF11G_BOGEY = REGISTRATE
            .creativeModeTab(() -> Main.KUAYUE_MAIN)
            .block("fake_df11g_bogey", p -> FakeDF11GBogeyBlock.brass("none", p))
            .initialProperties(SharedProperties::softMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .transform(BlockStressDefaults.setNoImpact())
            .onRegister(movementBehaviour(new FakeDF11GBogeyBehavior()))
            // .onRegisterAfter(Registry.ITEM_REGISTRY, v -> TooltipHelper.)
            // .item()
            // .transform(customItemModel())
            .register();

    public static void register() {
    }
}
