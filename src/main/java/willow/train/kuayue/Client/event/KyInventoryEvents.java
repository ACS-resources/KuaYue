package willow.train.kuayue.Client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import willow.train.kuayue.Items.ToolTipsItemHelper;
import willow.train.kuayue.Main;
import willow.train.kuayue.Screen.wiget.ImageButton;
import willow.train.kuayue.Screen.wiget.ItemIconButton;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;

import java.util.ArrayList;
import java.util.List;

public class KyInventoryEvents {

    private int carriageType = 0;
    ItemIconButton[] imgBtn = new ItemIconButton[7];
    ArrayList<List<ItemStack>> itemList = new ArrayList<>();
    int guiLeft = 0, guiTop = 0;

    @SubscribeEvent
    public void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        carriageType = 0;
    }

    @SubscribeEvent
    @SuppressWarnings({"resource"})
    public void onScreenInit(ScreenEvent.Init.Post event) {
        if(!(event.getScreen() instanceof CreativeModeInventoryScreen)) return;
        this.guiLeft = ((CreativeModeInventoryScreen) event.getScreen()).getGuiLeft();
        this.guiTop = ((CreativeModeInventoryScreen) event.getScreen()).getGuiTop();
        CreativeModeInventoryScreen.ItemPickerMenu menu = ((CreativeModeInventoryScreen) event.getScreen()).getMenu();
        int tab = ((CreativeModeInventoryScreen) event.getScreen()).getSelectedTab();

        ItemStack[] icons = new ItemStack[8];
        icons[0] = new ItemStack(BlockInit.CR_LOGO.get());
        icons[1] = new ItemStack(BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get());
        icons[2] = new ItemStack(BlockInit.PANEL_25G_ORIGINAL_BOTTOM.get());
        icons[3] = new ItemStack(BlockInit.PANEL_25K_ORIGINAL_BOTTOM.get());
        icons[4] = new ItemStack(BlockInit.PANEL_25Z_ORIGINAL_BOTTOM.get());
        icons[5] = new ItemStack(BlockInit.PANEL_25T_ORIGINAL_BOTTOM.get());
        icons[6] = new ItemStack(BlockInit.PANEL_25B_MARSHALLED_SYMBOL.get());
        //icons[7] = new ItemStack(BlockInit.PANEL_CR200J_MARSHALLED_BOTTOM.get());

        initMapping();
        
        Component[] components = new Component[8];
        components[0] = Component.translatable("container." + Main.MOD_ID + ".inventory.button.all");
        components[1] = Component.translatable("container." + Main.MOD_ID + ".inventory.button.25b");
        components[2] = Component.translatable("container." + Main.MOD_ID + ".inventory.button.25g");
        components[3] = Component.translatable("container." + Main.MOD_ID + ".inventory.button.25k");
        components[4] = Component.translatable("container." + Main.MOD_ID + ".inventory.button.25z");
        components[5] = Component.translatable("container." + Main.MOD_ID + ".inventory.button.25t");
        components[6] = Component.translatable("container." + Main.MOD_ID + ".inventory.button.marshalled_25_series");
        components[7] = Component.translatable("container." + Main.MOD_ID + ".inventory.button.cr200j");
        
        
        for(int i = 0; i < imgBtn.length; i++){
            imgBtn[i] = new ItemIconButton(this.guiLeft - 22, this.guiTop - 8 + i * 22, components[i], icons[i], b -> {
                for(int bx = 0; bx < imgBtn.length; bx++) {
                    if(b.equals(imgBtn[bx])) {
                        carriageType = bx;
                        ((ItemIconButton)b).toggle();
                        updateMenuItem(menu);
                        menu.scrollTo(0.0f);
                    } else {
                        (imgBtn[bx]).reset();
                    }
                }
            }, i == 0 ? 2 : 5, i == 0 ? 2 : 4);
        }
        for(ItemIconButton b : imgBtn){
            event.addListener(b);
            b.visible = tab == Main.KUAYUE_NormalSpeedPassageCarriageTab.getId();
        }

        imgBtn[0].toggle();
    }

    @SubscribeEvent
    public void onRender(ScreenEvent.Render.Pre event){
        if(event.getScreen() instanceof CreativeModeInventoryScreen) {
            updateButtons();
        }
    }

    public void updateButtons(){
        Screen screen = Minecraft.getInstance().screen;
        int tab = ((CreativeModeInventoryScreen) screen).getSelectedTab();

        if(screen instanceof CreativeModeInventoryScreen) {
            for(ItemIconButton b : imgBtn) {
                b.visible = tab == Main.KUAYUE_NormalSpeedPassageCarriageTab.getId();
            }
        }
    }

    public void updateMenuItem(CreativeModeInventoryScreen.ItemPickerMenu menu){
        switch (carriageType){
            case 1:  // B
                menu.items.clear();
                menu.items.addAll(itemList.get(0));
                menu.items.addAll(itemList.get(7));
                menu.items.addAll(itemList.get(8));
                break;
            case 2:  // G
                menu.items.clear();
                menu.items.addAll(itemList.get(1));
                menu.items.addAll(itemList.get(7));
                menu.items.addAll(itemList.get(8));
                break;
            case 3:  // K
                menu.items.clear();
                menu.items.addAll(itemList.get(2));
                menu.items.addAll(itemList.get(7));
                menu.items.addAll(itemList.get(8));
                break;
            case 4:  // Z
                menu.items.clear();
                menu.items.addAll(itemList.get(3));
                menu.items.addAll(itemList.get(7));
                menu.items.addAll(itemList.get(8));
                break;
            case 5:  // T
                menu.items.clear();
                menu.items.addAll(itemList.get(4));
                menu.items.addAll(itemList.get(7));
                break;
            case 6:  // M
                menu.items.clear();
                menu.items.addAll(itemList.get(5));
                menu.items.addAll(itemList.get(7));
                break;
            case 7:  // M
                menu.items.clear();
                menu.items.addAll(itemList.get(6));
                menu.items.addAll(itemList.get(7));
                break;
            default:
                menu.items.clear();
                menu.items.addAll(itemList.get(0));  // B
                menu.items.addAll(itemList.get(1));  // G
                menu.items.addAll(itemList.get(2));  // K
                menu.items.addAll(itemList.get(3));  // Z
                menu.items.addAll(itemList.get(4));  // T
                menu.items.addAll(itemList.get(5));  // M
                //menu.items.addAll(itemList.get(6));  // cr200j
                menu.items.addAll(itemList.get(7));  // general
                menu.items.addAll(itemList.get(8));  // bgzk
        }
    }

    public void initMapping(){
        itemList = new ArrayList<>(){{
            add(List.of(
                    BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25B_ORIGINAL_MID.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25B_ORIGINAL_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25B_ORIGINAL_TOP_B.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25B_ORIGINAL_DOOR.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25B_ORIGINAL_WINDOW.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25B.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25B_SMALL.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25B_TOILET.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25B_WIDE_SEALED.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25B.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25B_ORIGINAL_UPPER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25B_ORIGINAL_UPPER_2.get().asItem().getDefaultInstance()
            ));  // 25B 0

            add(List.of(
                    BlockInit.PANEL_25G_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25G_ORIGINAL_MID.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25G_ORIGINAL_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25G_ORIGINAL_DOOR.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25G_ORIGINAL_WINDOW.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25G_SEALED_SMALL.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25G_SMALL_2.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25G_TOILET.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25G_WIDE_SEALED.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25T_ORIGINAL.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25T_ORIGINAL_RUBBER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25G_ORIGINAL_UPPER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25G_ORIGINAL_UPPER_2.get().asItem().getDefaultInstance()
            ));  // 25G 1

            add(List.of(
                    BlockInit.PANEL_25K_ORIGINAL_SYMBOL.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25K_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25K_ORIGINAL_MID.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25K_ORIGINAL_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25K_ORIGINAL_DOOR.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25K_ORIGINAL_SLIDING_DOOR.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25K_ORIGINAL_LINE.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25K_ORIGINAL_WINDOW.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25K_SEALED_SMALL.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25K_SMALL.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25K_TOILET.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25K_WIDE_SEALED.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25K_ORIGINAL.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25K_ORIGINAL_RUBBER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25K_ORIGINAL_RUBBER_UPPER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25K_ORIGINAL_RUBBER_UPPER_2.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25K_ORIGINAL_UPPER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25K_ORIGINAL_UPPER_2.get().asItem().getDefaultInstance()
            ));  // 25K 2

            add(List.of(
                    BlockInit.PANEL_25Z_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25Z_ORIGINAL_MID.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25Z_ORIGINAL_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_ORIGINAL_25Z.get().asItem().getDefaultInstance()
            ));  // 25Z 3

            add(List.of(
                    BlockInit.PANEL_25T_ORIGINAL_BOTTOM.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_BOTTOM_B.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_SKIRT.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_SKIRT_SIDE.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_MID.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_MID_B.get().asItem().getDefaultInstance(),
                    BlockInit.WIDEPANEL_BSP25T_ORIGINAL_MID.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_TOP_B.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_XL25T_ORIGINAL_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_DOOR.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_SLIDING_DOOR.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_ORIGINAL_WINDOW.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_SMALL.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_SEALED_SMALL.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_WIDE_SEALED_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_25T_TOILET_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SEALED_SMALL_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_SMALL_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_SEALED_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_TOILET_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.ORIGINAL_COLOR_WINDOW_BSP25T_WIDE_BLUE.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25T_ORIGINAL.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25T_ORIGINAL_UPPER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25T_ORIGINAL_UPPER_2.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25T_ORIGINAL_RUBBER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25T_ORIGINAL_RUBBER_UPPER.get().asItem().getDefaultInstance(),
                    BlockInit.END_FACE_25T_ORIGINAL_RUBBER_UPPER_2.get().asItem().getDefaultInstance()
            ));  // 25T 4

            add(List.of(
                    BlockInit.PANEL_25B_MARSHALLED_SYMBOL.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25_MARSHALLED_BOTTOM.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25_MARSHALLED_MID.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25_MARSHALLED_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25_MARSHALLED_TOP_B.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25_MARSHALLED_DOOR.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25_MARSHALLED_WINDOW.get().asItem().getDefaultInstance(),
                    BlockInit.SLAB_25_MARSHALLED_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25K_MARSHALLED_SYMBOL.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25G_MARSHALLED_SYMBOL.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_MARSHALLED_SKIRT.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_25T_MARSHALLED_SKIRT_SIDE.get().asItem().getDefaultInstance()
            ));  // 25 Marshalled Series 5

            add(List.of(
                    BlockInit.PANEL_CR200J_MARSHALLED_BOTTOM.get().asItem().getDefaultInstance(),
                    BlockInit.WIDEPANEL_CR200J_MARSHALLED_MID.get().asItem().getDefaultInstance(),
                    BlockInit.PANEL_CR200J_MARSHALLED_TOP.get().asItem().getDefaultInstance(),
                    BlockInit.SLAB_CR200J_MARSHALLED_TOP.get().asItem().getDefaultInstance()

            ));  // cr200j 6

            add(List.of(
                    //BlockInit.ORIGINAL_COLOR_WINDOW_25_SEALED.get().asItem().getDefaultInstance(),
                    //BlockInit.DIRECT_DRAINAGE_TOILET_ORIGINAL_25G.get().asItem().getDefaultInstance(),
                    //BlockInit.DIRECT_DRAINAGE_TOILET_ORIGINAL_25T.get().asItem().getDefaultInstance(),
                    //BlockInit.DIRECT_DRAINAGE_TOILET_ORIGINAL_25K.get().asItem().getDefaultInstance()
            ));  // 通用 7

            add(List.of(
                    BlockInit.CARPORT_25BGZK.get().asItem().getDefaultInstance(),
                    BlockInit.CARPORT_25BGZK_AIR_CONDITION_ALL.get().asItem().getDefaultInstance(),
                    BlockInit.CARPORT_25BGZK_AIR_CONDITION_SIDE.get().asItem().getDefaultInstance(),
                    BlockInit.CARPORT_25BGZKT_CENTRE.get().asItem().getDefaultInstance(),
                    BlockInit.CARPORT_25BGZKT_WATER_TANK.get().asItem().getDefaultInstance()
            ));  // BGZK 8
        }};
    }
}
