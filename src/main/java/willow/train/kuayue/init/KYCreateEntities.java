package willow.train.kuayue.init;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.trains.BogeyTileEntityRenderer;
import com.simibubi.create.content.logistics.trains.track.StandardBogeyTileEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import willow.train.kuayue.Blocks.Entities.DF11GFrontTileEntity;
import willow.train.kuayue.Main;
import willow.train.kuayue.renderer.DF11GTileEntityRenderer;

public class KYCreateEntities {
    public static final BlockEntityEntry<DF11GFrontTileEntity> DF11G_FRONT = Main.registrate()
            .tileEntity("df11g_front_block", DF11GFrontTileEntity::new)
            //.instance(() -> DF11GFrontInstance::new)
            .validBlocks(AllBlocks.MECHANICAL_CRAFTER)
            .renderer(() -> DF11GTileEntityRenderer::new)
            .register();
    public static final BlockEntityEntry<StandardBogeyTileEntity> DF11G_BOGEY = Create.registrate()
            .tileEntity("df11g_bogey", StandardBogeyTileEntity::new)
            .renderer(() -> BogeyTileEntityRenderer::new)
            .validBlocks(AllBlocks.SMALL_BOGEY, AllBlocks.LARGE_BOGEY)
            .register();
    public static void register() {
    }
//    public static final BlockEntityEntry<SteamEngineTileEntity> STEAM_ENGINE = Create.registrate()
//            .tileEntity("steam_engine", SteamEngineTileEntity::new)
//            .validBlocks(AllBlocks.STEAM_ENGINE)
//            .renderer(() -> SteamEngineRenderer::new)
//            .register();
}
