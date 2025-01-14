package willow.train.kuayue.init;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackBlockItem;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import willow.train.kuayue.Blocks.Bogeys.df11g_bogey.DF11GBogeyBlock;
import willow.train.kuayue.Blocks.Bogeys.standard_bogey.KYStandardBogeyBlock;
import willow.train.kuayue.Blocks.Locos.df11g.DF11GFrontBlock;
import willow.train.kuayue.Blocks.Tracks.standard_track.StandardTrackBlockStateGenerator;
import willow.train.kuayue.Items.ToolTipsItemHelper;
import willow.train.kuayue.Main;
import willow.train.kuayue.base.data.BuilderTransformers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class KYCreateBlock {
    private static final CreateRegistrate REGISTRATE = Main.registrate()
            .creativeModeTab(() -> Main.KUAYUE_MAIN);

    private static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen, NonNullConsumer<? super TrackBlock> onRegister, Function<BlockBehaviour.Properties, BlockBehaviour.Properties> collectProperties) {
        List<TagKey<Block>> trackTags = new ArrayList<>();
        trackTags.add(AllTags.AllBlockTags.TRACKS.tag);
        trackTags.add(AllTags.AllBlockTags.GIRDABLE_TRACKS.tag);

        System.out.println("material name: " + material.resourceName() + "_track");
        return REGISTRATE.block(material.resourceName() + "_track", material::createBlock)
                .initialProperties(Material.STONE)
                .properties(p -> collectProperties.apply(p)
                        .color(MaterialColor.METAL)
                        .strength(0.8F)
                        .sound(SoundType.METAL)
                        .noOcclusion())
                .transform(pickaxeOnly())
                .blockstate(blockstateGen)

                .tag(AllTags.AllBlockTags.TRACKS.tag)
                .tag(AllTags.AllBlockTags.GIRDABLE_TRACKS.tag)
                .tag(AllTags.AllBlockTags.RELOCATION_NOT_SUPPORTED.tag)

                .lang(material.langName + " Train Track")
                .onRegister(onRegister)
                .onRegister(CreateRegistrate.blockModel(() -> TrackModel::new))
                .item(TrackBlockItem::new)
                .model((c, p) -> p.generated(c, Main.asResource("item/track/" + c.getName())))
                .build()
                .register();
    }

    private static BlockEntry<TrackBlock> makeTrack(TrackMaterial material) {
        return makeTrack(material, new StandardTrackBlockStateGenerator()::generate);
    }

    private static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen) {
        return makeTrack(material, blockstateGen, (t) -> {
        });
    }

    private static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen, NonNullConsumer<? super TrackBlock> onRegister) {
        return makeTrack(material, blockstateGen, onRegister, (p) -> p);
    }

    private static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen, Function<BlockBehaviour.Properties, BlockBehaviour.Properties> collectProperties) {
        return makeTrack(material, blockstateGen, (t) -> {
        }, collectProperties);
    }

    public static final BlockEntry<TrackBlock> STANDARD_TRACK = makeTrack(KYTrackMaterials.STANDARD,
            new StandardTrackBlockStateGenerator()::generate);

    public static final BlockEntry<KYStandardBogeyBlock> KY_STANDARD_BOGEY =
            REGISTRATE.block("ky_standard_bogey", p -> new KYStandardBogeyBlock(p, BogeySizes.SMALL))
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .transform(BuilderTransformers.kystandardbogey())
                    .lang("KY Standard Bogey")
                    //.item(KYStandardBogeyItem::new)
                    //.model((c, p) -> p.generated(c, Main.asResource("item/track/" + c.getName())))
                    //.build()
                    .register();

    public static final BlockEntry<DF11GBogeyBlock> DF11G_BOGEY =
            REGISTRATE.block("df11g_bogey", p -> new DF11GBogeyBlock(p, BogeySizes.SMALL))
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .transform(BuilderTransformers.df11gbogey())
                    .lang("DF11G Bogey")
                    .register();

    public static final BlockEntry<DF11GBogeyBlock> DF11G_BACKWARD_BOGEY =
            REGISTRATE.block("df11g_backward_bogey", p -> new DF11GBogeyBlock(p, KYBogeySizes.BACKWARD))
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .transform(BuilderTransformers.df11gbogey())
                    .lang("DF11G Backward Bogey")
                    .register();

    public static final BlockEntry<KYStandardBogeyBlock> PK209P_BOGEY =
            REGISTRATE.block("pk209p_bogey", p -> new KYStandardBogeyBlock(p, KYBogeySizes.PK209P))
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .transform(BuilderTransformers.kystandardbogey())
                    .lang("PK209P Bogey")
                    .register();

    private static final CreateRegistrate bogeyREGISTRATE = Main.registrate();

    private static final CreateRegistrate locoREGISTRATE = Main.registrate()
            .creativeModeTab(() -> Main.KUAYUE_LOCOS);

    public static final BlockEntry<DF11GFrontBlock> DF11G_FRONT_BLOCK =
            locoREGISTRATE.block("df11g_front2", DF11GFrontBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_BLUE))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(
                            axeOrPickaxe())
                    //.blockstate(BlockStateGen.horizontalBlockProvider(true))
                    .transform(BlockStressDefaults.setImpact(2.0))
                    //.onRegister(CreateRegistrate.connectedTextures(CrafterCTBehaviour::new))
                    .item(ToolTipsItemHelper::new)
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<DF11GFrontBlock> HXD3D_FRONT_BLOCK =
            locoREGISTRATE.block("hxd3d_front", DF11GFrontBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_BLUE))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(axeOrPickaxe())
                    //.blockstate(BlockStateGen.horizontalBlockProvider(true))
                    .transform(BlockStressDefaults.setImpact(2.0))
                    //.onRegister(CreateRegistrate.connectedTextures(CrafterCTBehaviour::new))
                    .item(ToolTipsItemHelper::new)
                    .transform(customItemModel())
                    .register();



//    public static final BlockEntry<DF11GBogeyBlock> DF11G_BOGEY =
//            REGISTRATE.block("df11g_bogey", p -> new DF11GBogeyBlock(p, true))
//                    .properties(p -> p.color(MaterialColor.PODZOL))
//                    .transform(BuilderTransformers.bogey())
//                    .register();
//    public static final BlockEntry<FakeDF11GBogeyBlock> fake_DF11G_BOGEY =
//            REGISTRATE.block("fake_df11g_bogey", p -> FakeDF11GBogeyBlock.brass("none", p))
//                    .initialProperties(SharedProperties::softMetal)
//                    .properties(BlockBehaviour.Properties::noOcclusion)
//                    .blockstate(BlockStateGen.axisBlockProvider(true))
//                    .transform(BlockStressDefaults.setNoImpact())
//                    .onRegister(movementBehaviour(new FakeDF11GBogeyBehavior()))
//                    //.onRegisterAfter(Registry.ITEM_REGISTRY, v -> TooltipHelper.)
//                    //.item()
//                    //.transform(customItemModel())
//                    .register();

    public static void register() {}
}
