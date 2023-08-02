package willow.train.kuayue;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import willow.train.kuayue.Client.CatenaryConnectionHandler;
import willow.train.kuayue.Screen.CarriageTypeSignEditScreen;
import willow.train.kuayue.effect.EffectInit;
import willow.train.kuayue.init.*;
import willow.train.kuayue.renderer.CarriageTypeSignRenderer;
import willow.train.kuayue.renderer.TrainPanelSignRenderer;
import willow.train.kuayue.sounds.ModSounds;
import willow.train.kuayue.tabs.*;

import static willow.train.kuayue.init.BlockEntitiesInit.CARRIAGE_TYPE_SIGN;

@Mod("kuayue")
public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger("KuaYue");
    public static final String MOD_ID = "kuayue";

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);
    public static CatenaryConnectionHandler CATENARYCONNECTIONHANDLER = new CatenaryConnectionHandler();
    public static final MainTab KUAYUE_MAIN = new MainTab(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(BlockInit.CR_LOGO.get());
        }
    };
    public static final LocosTab KUAYUE_LOCOS = new LocosTab(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public @NotNull ItemStack makeIcon() { return new ItemStack(ItemInit.LOCO_LOGOS.get());}
    };
    public static final NormalSpeedPassageCarriageTab KUAYUE_NormalSpeedPassageCarriageTab = new NormalSpeedPassageCarriageTab(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public @NotNull ItemStack makeIcon() { return new ItemStack(ItemInit.SERIES_25_LOGOS.get());}
    };
    public static final DietTab KUAYUE_DIET = new DietTab(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemInit.CA_25T.get());
        }
    };
    public static final CatenaryTab KUAYUE_CATENARY = new CatenaryTab(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public @NotNull ItemStack makeIcon() { return new ItemStack(BlockInit.Catenary_Grid_C1.get());}
    };

//    public static final GroundTab KUAYUE_GROUND = new GroundTab(MOD_ID) {
//        @Override
//        @OnlyIn(Dist.CLIENT)
//        public @NotNull ItemStack makeIcon() { return new ItemStack(BlockInit.Station_Entrance_Signal.get());}
//    };


    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //添加物品，方块的初始化信息
        ItemInit.ITEMS.register(bus);

        BlockInit.BLOCKS.register(bus);

        BlockEntitiesInit.BLOCK_ENTITIES.register(bus);

        EntityInit.ENTITY_TYPES.register(bus);

        ModSounds.register(bus);

        EffectInit.rigister(bus);

        MenuInit.register(bus);

        KYCreateBlock.register();
        KYCreateEntities.register();
        KYTrackMaterials.register();
        KYBogeyStyles.register();
        KYItems.register();
        KYTags.register();
        KYEdgePointTypes.register();
        KYBogeySizes.register();

        AllModulePartials.init();
        REGISTRATE.registerEventListeners(bus);

        //bus.addListener(this::setup);

        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

    }
    protected void clientSetup(final FMLClientSetupEvent fmlClientSetupEvent) {

        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25B_ORIGINAL_WINDOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B_SMALL.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_SEALED_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL_2.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SEALED_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_BLUE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE_SEALED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE_SEALED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE_SEALED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_SEALED_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B_TOILET.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_TOILET.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_TOILET.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_TOILET_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SMALL_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SEALED_SMALL_BLUE.get(), RenderType.translucent());



        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25_SEALED.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25B_ORIGINAL_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CARRIAGE_TYPE_SIGN_BLOCK.get(), RenderType.translucent());


        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25G_ORIGINAL_WINDOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25G_ORIGINAL_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25K_ORIGINAL_WINDOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25K_ORIGINAL_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_ORIGINAL_WINDOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.WIDEPANEL_BSP25T_ORIGINAL_MID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_ORIGINAL_SKIRT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_ORIGINAL_SKIRT_SIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_ORIGINAL_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_XL25T_ORIGINAL_TOP.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25_MARSHALLED_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25B_MARSHALLED_WINDOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25_MARSHALLED_WINDOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_MARSHALLED_SKIRT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_MARSHALLED_SKIRT_SIDE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.DF11G_PANEL_MID_FRONT_2.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DF11G_PANEL_MID_WINDOW.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.WIDEPANEL_CR200J_MARSHALLED_MID.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.CARPORT_25BGZK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CARPORT_25T.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANTOGRAPH.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SS8_PANTOGRAPH.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Pole.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Concrete_Pole.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Pole_Side_Hang_Point_Connector.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Pole_Side_Hang_Point_Connector_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Steel_Pole.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Steel_Pole_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Concrete_Pole_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_A1.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_A2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_A1_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_A2_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_Connector_A_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Cable_Hang_Point.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Cable_Hang_Point_2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Cable_Hang_Point_Central.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Cable_Hang_Point_Central_2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_Connector_C_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C1.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C1_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C2_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_B.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_A_1125.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_1600.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_End_1600.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_End_1255.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Falling_Weight_Cable.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Falling_Weight.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Pillar.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Steel_Gantry_Pillar.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Platform_Line_Block.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Head.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Pole_Base.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Pole_Base2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Pole.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Pole2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Pillar_Grid_Connector_A.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Pillar_Grid_Connector_C.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Pillar_End_Connector.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CATENARY_1600_WITH_PLUMB.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_A_1125_With_Plumb.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Hang_Point_1125.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Hang_Point_1125_C.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Hang_Point_1600_To_1125.get(),RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.Station_Entrance_Signal.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Shunting_Signal.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.No_Double_Pantograph.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Switch_Off_Sign.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Switch_On_Sign.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Signal_Pole.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Signal_Pole_Light.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.TactilePavingStraight.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.TactilePavingPin.get(),RenderType.translucent());
        //ItemBlockRenderTypes.setRenderLayer(BlockInit.STANDARD_TRACK.get(),RenderType.translucent());

        //WoodType.register(WoodTypeInit.TrainPanel);
        BlockEntityRenderers.register(BlockEntitiesInit.TRAIN_BLOCK_ENTITES_BLOCK.get(), TrainPanelSignRenderer::new);
        BlockEntityRenderers.register(CARRIAGE_TYPE_SIGN.get(), CarriageTypeSignRenderer::new);

        /*fmlClientSetupEvent.enqueueWork(() -> {
            Sheets.addWoodType(WoodTypeInit.TrainPanel);

        });*/

        MenuScreens.register(MenuInit.CARRIAGE_TYPE_SIGN_EDIT_MENU.get(), CarriageTypeSignEditScreen::new);
        //test text
    }

    /*@OnlyIn(Dist.CLIENT)
    protected void setup(final FMLCommonSetupEvent event) {

    }*/
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    @ApiStatus.Internal
    public static boolean trackEdgeTemporarilyFlipped = false;

    @ApiStatus.Internal
    public static boolean trackEdgeCarriageTravelling = false;

    @ApiStatus.Internal
    public static boolean temporarilySkipSwitches = false;
}
