package willow.train.kuayue.init;


import com.simibubi.create.content.trains.bogey.BogeyBlockEntityRenderer;
import com.simibubi.create.content.trains.track.TrackBlockEntity;
import com.simibubi.create.content.trains.track.TrackInstance;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import willow.train.kuayue.Blocks.Bogeys.KYBogeyBlockEntity;
import willow.train.kuayue.Blocks.Bogeys.standard_bogey.KYStandardBogeyBlockEntity;
import willow.train.kuayue.Main;

import static com.simibubi.create.Create.REGISTRATE;

public class KYCreateEntities {

    private static final CreateRegistrate REGISTRATE = Main.registrate();

    public static final BlockEntityEntry<KYStandardBogeyBlockEntity> KY_STANDARD_BOGEY = REGISTRATE
            .blockEntity("mono_bogey", KYStandardBogeyBlockEntity::new)
            .renderer(() -> BogeyBlockEntityRenderer::new)
            .validBlocks(KYCreateBlock.KY_STANDARD_BOGEY)
            .register();

    public static final BlockEntityEntry<KYBogeyBlockEntity> BOGEY = REGISTRATE
            .blockEntity("bogey", KYBogeyBlockEntity::new)
            .renderer(() -> BogeyBlockEntityRenderer::new)
            .validBlocks(KYCreateBlock.SINGLEAXLE_BOGEY, KYCreateBlock.DOUBLEAXLE_BOGEY,
                    KYCreateBlock.LARGE_PLATFORM_DOUBLEAXLE_BOGEY, KYCreateBlock.TRIPLEAXLE_BOGEY)
            .register();

//    public static final BlockEntityEntry<DF11GFrontTileEntity> DF11G_FRONT = Main.registrate()
//            .tileEntity("df11g_front_block", DF11GFrontTileEntity::new)
//            //.instance(() -> DF11GFrontInstance::new)
//            .validBlocks(AllBlocks.MECHANICAL_CRAFTER)
//            .renderer(() -> DF11GTileEntityRenderer::new)
//            .register();
//    public static final BlockEntityEntry<StandardBogeyTileEntity> DF11G_BOGEY = Main.registrate()
//            .tileEntity("df11g_bogey", StandardBogeyTileEntity::new)
//            .renderer(() -> BogeyTileEntityRenderer::new)
//            .validBlocks(KYCreateBlock.DF11G_BOGEY)
//            .register();



//    public static final BlockEntityEntry<DF11GBogeyTileEntity> fake_DF11G_BOGEY = Main.registrate()
//            .tileEntity("fake_bogey", DF11GBogeyTileEntity::new)
//            .instance(() -> FakeDF11GBogeyInstance::new, false)
//            .validBlocks(KYCreateBlock.fake_DF11G_BOGEY)
//            .renderer(() -> FakeDF11GBogeyRenderer::new)
//            .register();

    /*public static final BlockEntityEntry<TrackBlockEntity> STANDARD_TRACK = REGISTRATE
            .blockEntity("standard_track", TrackBlockEntity::new)
            .instance(() -> TrackInstance::new)
            .validBlocksDeferred(TrackMaterial::allBlocks)
            .renderer(() -> TrackRenderer::new)
            .register();*/

    public static void register() {
    }
//    public static final BlockEntityEntry<SteamEngineTileEntity> STEAM_ENGINE = Create.registrate()
//            .tileEntity("steam_engine", SteamEngineTileEntity::new)
//            .validBlocks(AllBlocks.STEAM_ENGINE)
//            .renderer(() -> SteamEngineRenderer::new)
//            .register();
}
