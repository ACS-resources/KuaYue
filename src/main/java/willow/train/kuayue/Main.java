package willow.train.kuayue;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import willow.train.kuayue.BlockEntity.BlockEntityRenderer.CarriageNoSignRenderer;
import willow.train.kuayue.BlockEntity.BlockEntityRenderer.CarriageTypeSignRenderer;
import willow.train.kuayue.BlockEntity.BlockEntityRenderer.LaqueredBoardEntityRenderer;
import willow.train.kuayue.Client.CatenaryConnectionHandler;
import willow.train.kuayue.MultiLoader.Env;
import willow.train.kuayue.Network.KuayueNetworkHandler;
import willow.train.kuayue.Screen.CarriageNoSignEditScreen;
import willow.train.kuayue.Screen.CarriageTypeSignEditScreen;
import willow.train.kuayue.effect.EffectInit;
import willow.train.kuayue.init.*;
import willow.train.kuayue.sounds.ModSounds;
import willow.train.kuayue.tabs.*;

@Mod("kuayue")
public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger("KuaYue");
    public static final String MOD_ID = "kuayue";

    static IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

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

        //添加物品，方块的初始化信息
        ItemInit.ITEMS.register(bus);

        BlockInit.BLOCKS.register(bus);

        BlockEntitiesInit.BLOCK_ENTITIES.register(bus);

        EntityInit.ENTITY_TYPES.register(bus);

        ModSetup.register();

        ModSounds.register(bus);

        EffectInit.rigister(bus);

        MenuInit.register(bus);

        AllModulePartials.init();
        REGISTRATE.registerEventListeners(bus);

        bus.addListener(this::setup);

        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        KYPackets.PACKETS.registerC2SListener();

        Env.CLIENT.runIfCurrent(() -> KuaYueClient::init);

    }
    protected void clientSetup(final FMLClientSetupEvent fmlClientSetupEvent) {

        registerRenderers(fmlClientSetupEvent);
        registerMenus(fmlClientSetupEvent);

        /**
         * 1.19 开始Render_Layer写在模型里面
         *
         * 在 "credit": "xxx" 后面补上一句 "render_type": "translucent" 即可
         *
         * obj 模型里的 "flip-v" 改为 "flip_v", 在 1.20 上必须这样
         */

        /*
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

         */

        //WoodType.register(WoodTypeInit.TrainPanel);

        //test text
    }

    protected void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        //Sheets.addWoodType(WoodTypeInit.TrainPanel);
            KuayueNetworkHandler.registerPackets();

    });
    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    protected void registerRenderers(final FMLClientSetupEvent fmlClientSetupEvent){
        BlockEntityRenderers.register(BlockEntitiesInit.CARRIAGE_TYPE_SIGN.get(), CarriageTypeSignRenderer::new);
        BlockEntityRenderers.register(BlockEntitiesInit.CARRIAGE_NO_SIGN.get(), CarriageNoSignRenderer::new);
        BlockEntityRenderers.register(BlockEntitiesInit.LAQUERED_BOARD.get(), LaqueredBoardEntityRenderer::new);
    }

    protected void registerMenus(final FMLClientSetupEvent fmlClientSetupEvent){
        MenuScreens.register(MenuInit.CARRIAGE_TYPE_SIGN_EDIT_MENU.get(), CarriageTypeSignEditScreen::new);
        MenuScreens.register(MenuInit.CARRIAGE_NO_SIGN_EDIT_MENU.get(), CarriageNoSignEditScreen::new);
    }

    public static ResourceLocation asResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}