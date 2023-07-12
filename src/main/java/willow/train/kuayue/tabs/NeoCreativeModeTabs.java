package willow.train.kuayue.tabs;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import willow.train.kuayue.Main;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;

public class NeoCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MOD_ID);
    public static final RegistryObject<CreativeModeTab> KUAYUE_MAIN = TAB_REGISTER.register("base",
            () -> {
                return CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.item_group_main"))
                        .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                        .icon(() -> new ItemStack(BlockInit.CR_LOGO.get().asItem()))
                        .displayItems((pParameters, pOutput) -> {
                            //pOutput.accept(BlockInit.CR_LOGO.get());
                            pOutput.accept(BlockInit.Platform_Line_Block.get());
                            pOutput.accept(BlockInit.SMOOTH_QUARTZ_PANEL_HALF.get());
                            pOutput.accept(BlockInit.SMOOTH_QUARTZ_PANEL_4.get());
                            pOutput.accept(BlockInit.SMOOTH_QUARTZ_PANEL_2.get());
                            pOutput.accept(BlockInit.SMOOTH_QUARTZ_ANGLE_22_UP.get());
                            pOutput.accept(BlockInit.CLAY_SLAB.get());
                            pOutput.accept(BlockInit.CLAY_STAIRS.get());
                            pOutput.accept(BlockInit.GREEN_FENCE.get());
                            pOutput.accept(BlockInit.GREEN_FENCE_45.get());

                            pOutput.accept(BlockInit.Station_Entrance_Signal.get());
                            pOutput.accept(BlockInit.Shunting_Signal.get());
                            pOutput.accept(BlockInit.No_Double_Pantograph.get());
                            pOutput.accept(BlockInit.Switch_Off_Sign.get());
                            pOutput.accept(BlockInit.Switch_On_Sign.get());
                            pOutput.accept(BlockInit.Signal_Pole.get());
                            pOutput.accept(BlockInit.Signal_Pole_Light.get());
                            pOutput.accept(BlockInit.TactilePavingStraight.get());
                            pOutput.accept(BlockInit.TactilePavingPin.get());
                            pOutput.accept(BlockInit.FlourescentLight.get());
                            pOutput.accept(BlockInit.MEGAPHONE.get());

                            //pOutput.accept(BlockInit.TRAIN_PANEL_SIGN_BLOCK.get());
                            //pOutput.accept(BlockInit.TRAIN_PANEL_SIGN_BLOCK1.get());





                            pOutput.accept(BlockInit.BOILING_WATER_PLACE.get());

                        })
                        .build();
            });

    public static final RegistryObject<CreativeModeTab> KUAYUE_LOCOS = TAB_REGISTER.register("locos",
            () -> {
                return CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.item_locos"))
                        .withTabsBefore(KUAYUE_MAIN.getKey())
                        .icon(() -> new ItemStack(ItemInit.LOCO_LOGOS.get().asItem()))
                        .displayItems((pParameters, pOutput) -> {

                            pOutput.accept(BlockInit.PANTOGRAPH.get());
                            //pOutput.accept(BlockInit.CARRIAGETYPESIGN.get());
                            //pOutput.accept(BlockInit.CARRIAGE_TYPE_SIGN_BLOCK.get());
                            pOutput.accept(BlockInit.SS8_PANTOGRAPH.get());
                            pOutput.accept(BlockInit.DF11G_COWCATCHER.get());
                            pOutput.accept(BlockInit.DF11G_CARPORT_1.get());
                            pOutput.accept(BlockInit.DF11G_CARPORT_2.get());
                            pOutput.accept(BlockInit.DF11G_CARPORT_GENERAL.get());
                            pOutput.accept(BlockInit.DF11G_CARPORT_LOUVER.get());
                            pOutput.accept(BlockInit.DF11G_CARPORT_LOUVER_2.get());
                            pOutput.accept(BlockInit.DF11G_CARPORT_LOUVER_3.get());
                            pOutput.accept(BlockInit.DF11G_CARPORT_KUAYUE.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_KUAYUE.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_GENERAL.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_MID_GENERAL.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_SHADES.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_MID_WINDOW.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_MID_SHADES.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_CR.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_MID_FRONT_2.get());
                            pOutput.accept(BlockInit.DF11G_PANEL_MID_FRONT.get());
                            pOutput.accept(BlockInit.DF11G_END_FACE.get());
                            pOutput.accept(BlockInit.DF11G_CAB_DOOR.get());
                            pOutput.accept(BlockInit.DF11G_EQUIP_ROOM_DOOR.get());

                            pOutput.accept(BlockInit.HXD3D_PANEL_RED.get());
                            pOutput.accept(BlockInit.HXD3D_PANEL_HEXIE.get());
                            pOutput.accept(BlockInit.HXD3D_CARPORT_GENERAL.get());
                            pOutput.accept(BlockInit.HXD3D_SLAB.get());
                            pOutput.accept(BlockInit.HXD3D_CAB_DOOR.get());

                        })
                        .build();
            });

//    public static final RegistryObject<CreativeModeTab> KUAYUE_GROUP_GROUND = TAB_REGISTER.register("group_ground",
//            () -> {
//                return CreativeModeTab.builder()
//                        .title(Component.translatable("itemGroup.item_group_ground"))
//                        .icon(() -> new ItemStack(BlockInit.MEGAPHONE.get().asItem()))
//                        .displayItems((pParameters, pOutput) -> {
//                            //pOutput.accept(BlockInit.Catenary_Pole.get());
//
//
//                        })
//                        .build();
//            });





    public static final RegistryObject<CreativeModeTab> KUAYUE_GROUP_NORMAL_SPEED_PASSAGE_CARRIAGE = TAB_REGISTER.register("group_normal_speed_passage_carriage",
            () -> {
                return CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.item_group_normal_speed_passage_carriage"))
                        .icon(() -> new ItemStack(ItemInit.SERIES_25_LOGOS.get().asItem()))
                        .withTabsBefore(KUAYUE_LOCOS.getKey())
                        .displayItems((pParameters, pOutput) -> {
                            //pOutput.accept(ItemInit.CA_25T.get());
                            //pOutput.accept(ItemInit.SERIES_25_LOGOS.get());
                            //pOutput.accept(ItemInit.LOCO_LOGOS.get());

                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25_SEALED.get());

                            pOutput.accept(BlockInit.PANEL_25B_ORIGINAL_DOOR.get());
                            pOutput.accept(BlockInit.END_FACE_25B.get());
                            pOutput.accept(BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get());
                            pOutput.accept(BlockInit.PANEL_25B_ORIGINAL_MID.get());
                            pOutput.accept(BlockInit.PANEL_25B_ORIGINAL_WINDOW.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25B_SMALL.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25B_TOILET.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE_SEALED.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25B.get());
                            pOutput.accept(BlockInit.PANEL_25B_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.PANEL_25B_ORIGINAL_TOP_B.get());
                            pOutput.accept(BlockInit.SLAB_25B_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.LADDER_25B_ORIGIINAL.get());
                            pOutput.accept(BlockInit.END_FACE_25B_ORIGINAL_UPPER.get());
                            pOutput.accept(BlockInit.END_FACE_25B_ORIGINAL_UPPER_2.get());
                            pOutput.accept(BlockInit.END_FACE_ORIGINAL_25G.get());
                            pOutput.accept(BlockInit.DIRECT_DRAINAGE_TOILET_ORIGINAL_25B.get());

                            pOutput.accept(BlockInit.PANEL_25G_ORIGINAL_DOOR.get());
                            pOutput.accept(BlockInit.PANEL_25G_ORIGINAL_BOTTOM.get());
                            pOutput.accept(BlockInit.PANEL_25G_ORIGINAL_MID.get());
                            pOutput.accept(BlockInit.PANEL_25G_ORIGINAL_WINDOW.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL_2.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25G_SEALED_SMALL.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25G_TOILET.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE_SEALED.get());
                            pOutput.accept(BlockInit.PANEL_25G_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.SLAB_25G_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.LADDER_25G_ORIGIINAL.get());

                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_WINDOW.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SMALL_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SEALED_SMALL_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_TOILET_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED_BLUE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_SEALED_BLUE.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.PANEL_XL25T_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_SKIRT.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_SKIRT_SIDE.get());
                            pOutput.accept(BlockInit.CARPORT_25T.get());
                            pOutput.accept(BlockInit.CARPORT_25T_TOILET.get());
                            pOutput.accept(BlockInit.AIR_CODITION_BSP25T.get());
                            pOutput.accept(BlockInit.SLAB_25T_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.WIDEPANEL_BSP25T_ORIGINAL_MID.get());
                            pOutput.accept(BlockInit.END_FACE_25T_ORIGINAL_UPPER.get());
                            pOutput.accept(BlockInit.END_FACE_25T_ORIGINAL_UPPER_2.get());
                            pOutput.accept(BlockInit.END_FACE_25T_ORIGINAL_RUBBER.get());
                            pOutput.accept(BlockInit.END_FACE_25T_ORIGINAL_RUBBER_UPPER.get());
                            pOutput.accept(BlockInit.END_FACE_25T_ORIGINAL_RUBBER_UPPER_2.get());
                            pOutput.accept(BlockInit.DIRECT_DRAINAGE_TOILET_ORIGINAL_25T.get());
                            pOutput.accept(BlockInit.PANEL_25_MARSHALLED_DOOR.get());
                            pOutput.accept(BlockInit.PANEL_25_MARSHALLED_BOTTOM.get());
                            pOutput.accept(BlockInit.PANEL_25_MARSHALLED_BOTTOM_LINE.get());
                            pOutput.accept(BlockInit.PANEL_25_MARSHALLED_MID.get());
                            pOutput.accept(BlockInit.PANEL_25_MARSHALLED_TOP.get());
                            pOutput.accept(BlockInit.PANEL_25_MARSHALLED_TOP_B.get());
                            pOutput.accept(BlockInit.PANEL_25B_MARSHALLED_SYMBOL.get());
                            pOutput.accept(BlockInit.PANEL_25G_MARSHALLED_SYMBOL.get());
                            pOutput.accept(BlockInit.PANEL_25K_MARSHALLED_SYMBOL.get());
                            pOutput.accept(BlockInit.PANEL_25B_MARSHALLED_WINDOW.get());
                            pOutput.accept(BlockInit.SLAB_25_MARSHALLED_TOP.get());
                            pOutput.accept(BlockInit.PANEL_25_MARSHALLED_WINDOW.get());
                            pOutput.accept(BlockInit.PANEL_25T_MARSHALLED_SKIRT.get());
                            pOutput.accept(BlockInit.PANEL_25T_MARSHALLED_SKIRT_SIDE.get());
                            pOutput.accept(BlockInit.END_FACE_25T_ORIGINAL.get());

                            pOutput.accept(BlockInit.ISOLATION_DOOR_25BG.get());

                            pOutput.accept(BlockInit.END_FACE_25G_ORIGINAL_UPPER.get());
                            pOutput.accept(BlockInit.END_FACE_25G_ORIGINAL_UPPER_2.get());
                            pOutput.accept(BlockInit.DIRECT_DRAINAGE_TOILET_ORIGINAL_25G.get());

                            pOutput.accept(BlockInit.SLAB_25Z_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.LADDER_25Z_ORIGIINAL.get());
                            pOutput.accept(BlockInit.END_FACE_ORIGINAL_25Z.get());
                            pOutput.accept(BlockInit.PANEL_25Z_ORIGINAL_BOTTOM.get());
                            pOutput.accept(BlockInit.PANEL_25Z_ORIGINAL_MID.get());
                            pOutput.accept(BlockInit.PANEL_25Z_ORIGINAL_TOP.get());

                            pOutput.accept(BlockInit.PANEL_25K_ORIGINAL_DOOR.get());
                            pOutput.accept(BlockInit.PANEL_25K_ORIGINAL_SLIDING_DOOR.get());
                            pOutput.accept(BlockInit.PANEL_25K_ORIGINAL_BOTTOM.get());
                            pOutput.accept(BlockInit.PANEL_25K_ORIGINAL_LINE.get());
                            pOutput.accept(BlockInit.PANEL_25K_ORIGINAL_MID.get());
                            pOutput.accept(BlockInit.PANEL_25K_ORIGINAL_WINDOW.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25K_SMALL.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25K_SEALED_SMALL.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25K_TOILET.get());
                            pOutput.accept(BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE_SEALED.get());
                            pOutput.accept(BlockInit.PANEL_25K_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.PANEL_25K_ORIGINAL_SYMBOL.get());
                            pOutput.accept(BlockInit.CARPORT_25BGZK.get());
                            pOutput.accept(BlockInit.VENTIDUCT_25B.get());
                            pOutput.accept(BlockInit.CARPORT_25BGZKT_CENTRE.get());
                            pOutput.accept(BlockInit.CARPORT_BSP25T_CENTRE.get());
                            pOutput.accept(BlockInit.CARPORT_25BGZKT_WATER_TANK.get());
                            pOutput.accept(BlockInit.CARPORT_25BGZK_AIR_CONDITION_ALL.get());
                            pOutput.accept(BlockInit.LADDER_25K_ORIGIINAL.get());
                            pOutput.accept(BlockInit.DIRECT_DRAINAGE_TOILET_ORIGINAL_25K.get());
                            pOutput.accept(BlockInit.CARPORT_25BGZK_AIR_CONDITION_SIDE.get());
                            pOutput.accept(BlockInit.SLAB_25K_ORIGINAL_TOP.get());
                            pOutput.accept(BlockInit.END_FACE_25K_ORIGINAL.get());
                            pOutput.accept(BlockInit.END_FACE_25K_ORIGINAL_UPPER.get());
                            pOutput.accept(BlockInit.END_FACE_25K_ORIGINAL_UPPER_2.get());
                            pOutput.accept(BlockInit.END_FACE_25K_ORIGINAL_RUBBER.get());
                            pOutput.accept(BlockInit.END_FACE_25K_ORIGINAL_RUBBER_UPPER.get());
                            pOutput.accept(BlockInit.END_FACE_25K_ORIGINAL_RUBBER_UPPER_2.get());

                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_DOOR.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_SLIDING_DOOR.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_BOTTOM.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_MID.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_MID_B.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_TOP_B.get());
                            pOutput.accept(BlockInit.PANEL_25T_ORIGINAL_BOTTOM_B.get());
                            pOutput.accept(BlockInit.LADDER_25T_ORIGIINAL.get());

                            pOutput.accept(BlockInit.WIDEPANEL_CR200J_MARSHALLED_MID.get());
                            pOutput.accept(BlockInit.PANEL_CR200J_MARSHALLED_TOP.get());
                            pOutput.accept(BlockInit.PANEL_CR200J_MARSHALLED_BOTTOM.get());
                            pOutput.accept(BlockInit.SLAB_CR200J_MARSHALLED_TOP.get());
                        })
                        .build();
            });
    public static final RegistryObject<CreativeModeTab> KUAYUE_GROUP_CATENARY = TAB_REGISTER.register("group_catenary",
            () -> {
                return CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.item_group_catenary"))
                        .icon(() -> new ItemStack(ItemInit.GeneralCatenry.get().asItem()))
                        .displayItems((pParameters, pOutput) -> {
                            pOutput.accept(ItemInit.GeneralCatenry.get());
                            pOutput.accept(ItemInit.GeneralStraight.get());
                            pOutput.accept(ItemInit.GeneralHyperbolic.get());
                            pOutput.accept(ItemInit.CatenaryScissors.get());
                            //pOutput.accept(ItemInit.WeatheringResistantSteel.get());
                            //pOutput.accept(ItemInit.MineralMixture.get());
                            //pOutput.accept(ItemInit.WeatheringResistantSteelBoard.get());

                            pOutput.accept(BlockInit.Concrete_Pole.get());
                            pOutput.accept(BlockInit.Catenary_Pole_Side_Hang_Point_Connector.get());
                            pOutput.accept(BlockInit.Catenary_Pole_Side_Hang_Point_Connector_45.get());
                            pOutput.accept(BlockInit.Steel_Pole.get());
                            pOutput.accept(BlockInit.Steel_Pole_45.get());
                            pOutput.accept(BlockInit.Concrete_Pole_45.get());
                            pOutput.accept(BlockInit.Catenary_Grid_A1.get());
                            pOutput.accept(BlockInit.Catenary_Grid_A1_45.get());
                            pOutput.accept(BlockInit.Catenary_Grid_C1_45.get());
                            pOutput.accept(BlockInit.Catenary_Grid_C2_45.get());
                            pOutput.accept(BlockInit.Catenary_Grid_A2_45.get());

                            //pOutput.accept(BlockInit.Catenary_Grid_Connector_A_45.get());
                            pOutput.accept(BlockInit.Cable_Hang_Point.get());
                            pOutput.accept(BlockInit.Cable_Hang_Point_2.get());
                            pOutput.accept(BlockInit.Cable_Hang_Point_Central.get());
                            pOutput.accept(BlockInit.Cable_Hang_Point_Central_2.get());
                            //pOutput.accept(BlockInit.Catenary_Grid_Connector_C_45.get());
                            pOutput.accept(BlockInit.Catenary_Grid_A2.get());
                            pOutput.accept(BlockInit.Catenary_Grid_B.get());
                            pOutput.accept(BlockInit.Catenary_Grid_C1.get());
                            pOutput.accept(BlockInit.Catenary_Grid_C2.get());
                            //pOutput.accept(BlockInit.Catenary_Grid_C_45.get());
                            //pOutput.accept(BlockInit.Catenary_A_1125.get());
                            //pOutput.accept(BlockInit.Catenary_1600.get());
                            pOutput.accept(BlockInit.Catenary_End_1600.get());
                            pOutput.accept(BlockInit.Falling_Weight_Cable.get());
                            pOutput.accept(BlockInit.Falling_Weight.get());
                            pOutput.accept(BlockInit.Gantry_Pillar.get());
                            pOutput.accept(BlockInit.Steel_Gantry_Pillar.get());
                            pOutput.accept(BlockInit.Gantry_Cast_Head.get());
                            pOutput.accept(BlockInit.Gantry_Cast.get());

                            pOutput.accept(BlockInit.Gantry_Cast_Pole_Base.get());
                            pOutput.accept(BlockInit.Gantry_Cast_Pole_Base2.get());
                            pOutput.accept(BlockInit.Gantry_Cast_Pole.get());
                            pOutput.accept(BlockInit.Gantry_Cast_Pole2.get());
                            pOutput.accept(BlockInit.Gantry_Pillar_Grid_Connector_A.get());
                            pOutput.accept(BlockInit.Gantry_Pillar_Grid_Connector_C.get());
                            //pOutput.accept(BlockInit.Gantry_Pillar_End_Connector.get());
                            //pOutput.accept(BlockInit.Catenary_End_1255.get());
                            //pOutput.accept(BlockInit.CATENARY_1600_WITH_PLUMB.get());
                            //pOutput.accept(BlockInit.Catenary_A_1125_With_Plumb.get());
                            //pOutput.accept(BlockInit.Catenary_Hang_Point_1125.get());
                            //pOutput.accept(BlockInit.Catenary_Hang_Point_1125_C.get());
                            //pOutput.accept(BlockInit.Catenary_Hang_Point_1600_To_1125.get());

                        })
                        .build();
            });
    public static final RegistryObject<CreativeModeTab> KUAYUE_GROUP_DIET = TAB_REGISTER.register("group_diet",
            () -> {
                return CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.item_group_diet"))
                        .withTabsBefore(KUAYUE_GROUP_NORMAL_SPEED_PASSAGE_CARRIAGE.getKey())
                        .icon(() -> new ItemStack(ItemInit.TRAIN_DIET_1.get().asItem()))
                        .displayItems((pParameters, pOutput) -> {
                            pOutput.accept(ItemInit.TRAIN_DIET_1.get());
                        })
                        .build();
            });
}
