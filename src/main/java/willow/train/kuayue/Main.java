package willow.train.kuayue;

import willow.train.kuayue.BlockEntity.BlockEntityRenderer.CarriageNoSignRenderer;
import willow.train.kuayue.Network.KuayueNetworkHandler;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import willow.train.kuayue.Client.CatenaryConnectionHandler;
import willow.train.kuayue.Screen.CarriageNoSignEditScreen;
import willow.train.kuayue.Screen.CarriageTypeSignEditScreen;
import willow.train.kuayue.effect.EffectInit;
import willow.train.kuayue.init.*;
import willow.train.kuayue.BlockEntity.BlockEntityRenderer.CarriageTypeSignRenderer;
import willow.train.kuayue.sounds.ModSounds;
import willow.train.kuayue.tabs.*;


//import static willow.train.kuayue.init.BlockEntitiesInit.CARRIAGE_TYPE_SIGN;

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
        AllModulePartials.init();
        REGISTRATE.registerEventListeners(bus);

        bus.addListener(this::setup);

        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

    }
    protected void clientSetup(final FMLClientSetupEvent fmlClientSetupEvent) {

        // DF11G
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DF11G_PANEL_MID_FRONT_2.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DF11G_PANEL_MID_WINDOW.get(), RenderType.translucent());

        // 柱子
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Concrete_Pole.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Steel_Pole.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Steel_Pole_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Concrete_Pole_45.get(),RenderType.translucent());

        // 构架
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Head.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Pole_Base.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Pole_Base2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Pole.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Cast_Pole2.get(),RenderType.translucent());

        // 张紧装置
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_End_1600.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Falling_Weight_Cable.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Falling_Weight.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Pillar.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Steel_Gantry_Pillar.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Platform_Line_Block.get(),RenderType.translucent());

        // 标识
        ItemBlockRenderTypes.setRenderLayer(BlockInit.No_Double_Pantograph.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Switch_Off_Sign.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Switch_On_Sign.get(),RenderType.translucent());

        // 盲道
        ItemBlockRenderTypes.setRenderLayer(BlockInit.TactilePavingStraight.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.TactilePavingPin.get(),RenderType.translucent());

        // 运作
        registerRenderers(fmlClientSetupEvent);
        registerMenus(fmlClientSetupEvent);
        registerWoodTypes(fmlClientSetupEvent);

        // 车厢板
        register25BPanels(fmlClientSetupEvent);
        register25GPanels(fmlClientSetupEvent);
        register25TPanels(fmlClientSetupEvent);
        registerBSP25TPanels(fmlClientSetupEvent);
        register25KPanels(fmlClientSetupEvent);
        registerUniversal25Panels(fmlClientSetupEvent);
        registerMarshalledPanels(fmlClientSetupEvent);

        // 接触网相关
        registerPantograph(fmlClientSetupEvent);
        registerHangPoints(fmlClientSetupEvent);
        registerGrids(fmlClientSetupEvent);

        // 信号
        registerSignals(fmlClientSetupEvent);

    }

    protected void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        Sheets.addWoodType(WoodTypeInit.TrainPanel);
            KuayueNetworkHandler.registerPackets();

        });
    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    /**
     * 注册 GUI Menu 的方法，请把 Menu 统统堆这里
     * @param fmlClientSetupEvent -
     */
    protected void registerMenus(final FMLClientSetupEvent fmlClientSetupEvent){
        MenuScreens.register(MenuInit.CARRIAGE_TYPE_SIGN_EDIT_MENU.get(), CarriageTypeSignEditScreen::new);
        MenuScreens.register(MenuInit.CARRIAGE_NO_SIGN_EDIT_MENU.get(), CarriageNoSignEditScreen::new);
    }

    /**
     * 注册 Renderer 的方法，把 Renderer 堆这里
     * BlockEntity 和 Renderer 的连接是在这里完成的
     * BlockEntitiesInit.Carriage_TYPE_SIGN.get() 为 BlockEntity
     * <p>
     * CarriageTypeSignRenderer::new 调用 Renderer 类的构造体
     * @param fmlClientSetupEvent -
     */

    protected void registerRenderers(final FMLClientSetupEvent fmlClientSetupEvent){
        BlockEntityRenderers.register(BlockEntitiesInit.CARRIAGE_TYPE_SIGN.get(), CarriageTypeSignRenderer::new);
        BlockEntityRenderers.register(BlockEntitiesInit.CARRIAGE_NO_SIGN.get(), CarriageNoSignRenderer::new);
    }

    /**
     * WoodTypes 等杂项的注册
     * @param fmlClientSetupEvent -
     */
    protected void registerWoodTypes(final FMLClientSetupEvent fmlClientSetupEvent){
        WoodType.register(WoodTypeInit.TrainPanel);
    }

    /**
     * 原色 25B 车厢部件注册
     * @param fmlClientSetupEvent -
     */
    protected void register25BPanels(final FMLClientSetupEvent fmlClientSetupEvent){
        // 车窗
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25B_ORIGINAL_WINDOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE_SEALED.get(), RenderType.translucent());

        // 门
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25B_ORIGINAL_DOOR.get(), RenderType.translucent());

        // 便池
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25B_TOILET.get(), RenderType.translucent());
    }

    /**
     * 原色 25G 车厢部件注册
     * @param fmlClientSetupEvent -
     */
    protected void register25GPanels(final FMLClientSetupEvent fmlClientSetupEvent){
        // 车窗
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL_2.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SEALED_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE_SEALED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25G_ORIGINAL_WINDOW.get(), RenderType.translucent());

        // 门
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25G_ORIGINAL_DOOR.get(), RenderType.translucent());

        // 便池
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25G_TOILET.get(), RenderType.translucent());

    }

    /**
     * 原色 25T 车厢部件注册
     * @param fmlClientSetupEvent -
     */
    protected void register25TPanels(final FMLClientSetupEvent fmlClientSetupEvent){

        // 车窗
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_ORIGINAL_WINDOW.get(), RenderType.translucent());

        // 裙板
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_ORIGINAL_SKIRT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_ORIGINAL_SKIRT_SIDE.get(), RenderType.translucent());

        //门
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_ORIGINAL_DOOR.get(), RenderType.translucent());

        //车顶
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_XL25T_ORIGINAL_TOP.get(), RenderType.translucent());

        //地板
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CARPORT_25T.get(), RenderType.translucent());

    }

    /**
     * BSP25T 车厢部件注册
     * @param fmlClientSetupEvent -
     */
    protected void registerBSP25TPanels(final FMLClientSetupEvent fmlClientSetupEvent){
        // 车窗
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_SEALED_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_TOILET_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SMALL_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SEALED_SMALL_BLUE.get(), RenderType.translucent());

        // 中厢板
        ItemBlockRenderTypes.setRenderLayer(BlockInit.WIDEPANEL_BSP25T_ORIGINAL_MID.get(), RenderType.translucent());
    }

    /**
     * 25K 车厢部件注册
     * @param fmlClientSetupEvent -
     */
    protected void register25KPanels(final FMLClientSetupEvent fmlClientSetupEvent){
        // 车窗
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_SEALED_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE_SEALED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_SMALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25K_TOILET.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25_SEALED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25K_ORIGINAL_WINDOW.get(), RenderType.translucent());

        // 车门
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25K_ORIGINAL_DOOR.get(), RenderType.translucent());
    }

    /**
     * 统型车厢部件注册
     * @param fmlClientSetupEvent -
     */
    protected void registerMarshalledPanels(final FMLClientSetupEvent fmlClientSetupEvent){
        // 车门
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25_MARSHALLED_DOOR.get(), RenderType.translucent());

        // 车窗
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25_MARSHALLED_WINDOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25B_MARSHALLED_WINDOW.get(), RenderType.translucent());

        // 裙板
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_MARSHALLED_SKIRT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANEL_25T_MARSHALLED_SKIRT_SIDE.get(), RenderType.translucent());

        // CR200J
        ItemBlockRenderTypes.setRenderLayer(BlockInit.WIDEPANEL_CR200J_MARSHALLED_MID.get(), RenderType.translucent());
    }

    /**
     * 通用25系列车厢板注册
     * @param fmlClientSetupEvent -
     */
    protected void registerUniversal25Panels(final FMLClientSetupEvent fmlClientSetupEvent){
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ORIGINAL_COLOR_WINDOW_25.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CARPORT_25BGZK.get(), RenderType.translucent());
    }

    /**
     * 受电弓注册
     * @param fmlClientSetupEvent -
     */
    protected void registerPantograph(final FMLClientSetupEvent fmlClientSetupEvent){
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PANTOGRAPH.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SS8_PANTOGRAPH.get(), RenderType.translucent());
    }

    /**
     * 信号机注册
     * @param fmlClientSetupEvent -
     */
    protected void registerSignals(final FMLClientSetupEvent fmlClientSetupEvent){
        // 信号机
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Station_Entrance_Signal.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Shunting_Signal.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Signal_Pole_Light.get(),RenderType.translucent());

        // 柱子
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Signal_Pole.get(),RenderType.translucent());
    }

    /**
     * 接触网挂点注册
     * @param fmlClientSetupEvent -
     */
    protected void registerHangPoints(final FMLClientSetupEvent fmlClientSetupEvent){
        // 挂点
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Cable_Hang_Point.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Cable_Hang_Point_2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Cable_Hang_Point_Central.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Cable_Hang_Point_Central_2.get(),RenderType.translucent());

        // 挂点转接器
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Pole_Side_Hang_Point_Connector.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Pole_Side_Hang_Point_Connector_45.get(),RenderType.translucent());
    }

    /**
     * 接触网架注册
     * @param fmlClientSetupEvent -
     */
    protected void registerGrids(final FMLClientSetupEvent fmlClientSetupEvent){
        // A
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_A1.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_A2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_A1_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_A2_45.get(),RenderType.translucent());

        // C
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C1.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C2.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C1_45.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_C2_45.get(),RenderType.translucent());

        // B
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Catenary_Grid_B.get(),RenderType.translucent());

        // 转接器
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Pillar_Grid_Connector_A.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.Gantry_Pillar_Grid_Connector_C.get(),RenderType.translucent());
    }
}