package willow.train.kuayue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simibubi.create.foundation.data.CreateRegistrate;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.state.properties.WoodType;
import willow.train.kuayue.effect.EffectInit;
import willow.train.kuayue.init.AllModulePartials;
import willow.train.kuayue.init.BlockEntitiesInit;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;
import willow.train.kuayue.init.KYCreateBlock;
import willow.train.kuayue.init.KYCreateEntities;
import willow.train.kuayue.init.WoodTypeInit;
import willow.train.kuayue.renderer.TrainPanelSignRenderer;
import willow.train.kuayue.sounds.ModSounds;

public class Main implements ModInitializer, ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("KuaYue");
    public static final String MOD_ID = "kuayue";

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);
    public static final CreativeModeTab KUAYUE_MAIN = FabricItemGroupBuilder.build(asResource("item_group_main"),
            BlockInit.CR_LOGO.get().asItem()::getDefaultInstance);
    public static final CreativeModeTab KUAYUE_LOCOS = FabricItemGroupBuilder.build(asResource("item_locos"),
            ItemInit.LOCO_LOGOS.get()::getDefaultInstance);
    public static final CreativeModeTab KUAYUE_NormalSpeedPassageCarriageTab = FabricItemGroupBuilder.build(
            asResource("item_group_normal_speed_passage_carriage"), ItemInit.SERIES_25_LOGOS.get()::getDefaultInstance);
    public static final CreativeModeTab KUAYUE_DIET = FabricItemGroupBuilder.build(asResource("item_group_diet"),
            ItemInit.CA_25T.get()::getDefaultInstance);
    public static final CreativeModeTab KUAYUE_CATENARY = FabricItemGroupBuilder
            .build(asResource("item_group_catenary"), BlockInit.Catenary_Pole.get().asItem()::getDefaultInstance);

    // public static final GroundTab KUAYUE_GROUND = new GroundTab(MOD_ID) {
    // @Override
    // @OnlyIn(Dist.CLIENT)
    // public @NotNull ItemStack makeIcon() { return new
    // ItemStack(BlockInit.Station_Entrance_Signal.get());}
    // };

    @Override
    public void onInitializeClient() {
        AllModulePartials.init();
    }

    @Override
    public void onInitialize() {
        ItemInit.ITEMS.register();
        BlockInit.BLOCKS.register();
        BlockEntitiesInit.BLOCK_ENTITIES.register();
        ModSounds.register();
        EffectInit.rigister();

        KYCreateBlock.register();
        KYCreateEntities.register();

        WoodType.register(WoodTypeInit.TrainPanel);
    }

    protected void clientSetup() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25B_ORIGINAL_WINDOW.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25B.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_BLUE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25B_SMALL.get(),
                RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25K_SEALED_SMALL.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL_2.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SEALED_SMALL.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_BLUE.get(),
                RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE_SEALED.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE_SEALED.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE_SEALED.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_SEALED_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25K_SMALL.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25B_TOILET.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25G_TOILET.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25K_TOILET.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_TOILET_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SMALL_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL_BLUE.get(),
                RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SEALED_SMALL_BLUE.get(),
                RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ORIGINAL_COLOR_WINDOW_25_SEALED.get(),
                RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25B_ORIGINAL_DOOR.get(), RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25G_ORIGINAL_WINDOW.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25G_ORIGINAL_DOOR.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25K_ORIGINAL_WINDOW.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25K_ORIGINAL_DOOR.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25T_ORIGINAL_WINDOW.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.WIDEPANEL_BSP25T_ORIGINAL_MID.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25T_ORIGINAL_SKIRT.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25T_ORIGINAL_SKIRT_SIDE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25T_ORIGINAL_DOOR.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_XL25T_ORIGINAL_TOP.get(), RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25_MARSHALLED_DOOR.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25B_MARSHALLED_WINDOW.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25_MARSHALLED_WINDOW.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25T_MARSHALLED_SKIRT.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANEL_25T_MARSHALLED_SKIRT_SIDE.get(),
                RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.DF11G_PANEL_MID_FRONT_2.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.DF11G_PANEL_MID_WINDOW.get(), RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.WIDEPANEL_CR200J_MARSHALLED_MID.get(),
                RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.CARPORT_25BGZK.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.CARPORT_25T.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PANTOGRAPH.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Catenary_Pole.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Catenary_Grid.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Hang_Catenary_Grid.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Station_Entrance_Signal.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Shunting_Signal.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.No_Double_Pantograph.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Switch_Off_Sign.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Switch_On_Sign.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Signal_Pole.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Signal_Pole_Light.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.Concrete_Pole.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.TactilePavingStraight.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.TactilePavingPin.get(), RenderType.translucent());

        BlockEntityRenderers.register(BlockEntitiesInit.TRAIN_BLOCK_ENTITES_BLOCK.get(), TrainPanelSignRenderer::new);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
