package willow.train.kuayue.init;

import com.google.common.base.Supplier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import willow.train.kuayue.Items.EditablePanels.Brush;
import willow.train.kuayue.Items.EditablePanels.ColoredBrush;
import willow.train.kuayue.Items.EditablePanels.LaqueredBoardItem;
import willow.train.kuayue.Items.catenary.CatenaryScissors;
import willow.train.kuayue.Items.catenary.GeneralCatenary;
import willow.train.kuayue.Items.foods.Food1;
import willow.train.kuayue.Items.resources.mixture;
import willow.train.kuayue.Items.resources.weathering_resistant_steel;
import willow.train.kuayue.Items.resources.weathering_resistant_steel_board;
import willow.train.kuayue.Main;
import willow.train.kuayue.effect.EffectInit;

public class ItemInit {
    public static final Logger LOGGER = LoggerFactory.getLogger("KuaYue");
    //注册机
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            Main.MOD_ID);
    public static final RegistryObject<Item> CA_25T = register("ca_25t",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SERIES_25_LOGOS = register("series25_logos",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LOCO_LOGOS = register("loco_logos",
            () -> new Item(new Item.Properties()));

    //火车餐1
    public static final RegistryObject<Item> TRAIN_DIET_1 = register("train_diet_1",
            () -> new Food1(new Item.Properties().tab(Main.KUAYUE_DIET).food(
                    new FoodProperties.Builder().nutrition(4).saturationMod(2.5F).
                    effect(() -> new MobEffectInstance(MobEffects.HUNGER, 1000, 0,false,true), 1.0F).
                    effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1000, 0,false,true), 1.0F).
                    effect(() -> new MobEffectInstance(EffectInit.NOODLE_SMELL.get(), 2000, 0,false,true), 1.0F).
                    alwaysEat().alwaysEat().build())));

    //接触网
    public static final RegistryObject<Item> GeneralCatenry = register("general_catenary",
            () -> new GeneralCatenary(new Item.Properties().tab(Main.KUAYUE_CATENARY)));

    public static final RegistryObject<Item> GeneralStraight = register("general_straight",
            () -> new GeneralCatenary(new Item.Properties().tab(Main.KUAYUE_CATENARY)));

    public static final RegistryObject<Item> GeneralHyperbolic = register("general_hyperbolic",
            () -> new GeneralCatenary(new Item.Properties().tab(Main.KUAYUE_CATENARY)));

    public static final RegistryObject<Item> CatenaryScissors = register("catenary_scissors",
            () -> new CatenaryScissors(new Item.Properties().tab(Main.KUAYUE_CATENARY)));

    public static final RegistryObject<Item> Brush = register("brush",
            () -> new Brush(new Item.Properties().tab(Main.KUAYUE_MAIN)));

    public static final RegistryObject<Item> ColoredBrush = register("colored_brush",
            () -> new ColoredBrush(new Item.Properties().tab(Main.KUAYUE_MAIN)));

    public static final RegistryObject<Item> LaqueredBoard = register("laquered_board_item",
            () -> new LaqueredBoardItem(new Item.Properties().tab(Main.KUAYUE_MAIN)));

//   public static final RegistryObject<Item> TRAIN_PANEL_SIGN_ITEM = register("train_panel_sign_item",
//            () -> new SignItem(new Item.Properties().tab(Main.KUAYUE_MAIN),
//                    BlockInit.TRAIN_PANEL_SIGN_BLOCK.get(),
//                    BlockInit.TRAIN_PANEL_SIGN_BLOCK1.get()));
//
//    public static final RegistryObject<Item> TRAIN_PANEL_SIGN_ITEM = ITEMS.register("train_panel_sign_item",
//            () -> new SignItem(new Item.Properties().tab(Main.KUAYUE_MAIN).stacksTo(16),
//                    BlockInit.TRAIN_PANEL_SIGN_BLOCK1.get(), BlockInit.TRAIN_PANEL_SIGN_BLOCK.get()));

//    public static final RegistryObject<Item> DF11G_BOGEY_ITEM = ITEMS.register("df11g_bogey",
//            () -> new BlockItem(KYCreateBlock.DF11G_BOGEY.get(),new Item.Properties().tab(Main.KUAYUE_DIET)));
//public static final RegistryObject<Item> TRAIN_PANEL_SIGN_ITEM = ITEMS.register("train_panel_sign_item",
//        () -> new BlockItem(BlockInit.TRAIN_PANEL_SIGN_BLOCK.get(), new Item.Properties().tab(Main.KUAYUE_MAIN)));
//耐候钢和混合物
    private static final RegistryObject<Item> WeatheringResistantSteel = register("weathering_resistant_steel",
        () -> new weathering_resistant_steel(new Item.Properties()));//.tab(Main.KUAYUE_NormalSpeedPassageCarriageTab)));
    private static final RegistryObject<Item> WeatheringResistantSteelBoard = register("weathering_resistant_steel_board",
        () -> new weathering_resistant_steel_board(new Item.Properties()));//.tab(Main.KUAYUE_NormalSpeedPassageCarriageTab)));

    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        //LOGGER.info("rigister item:"+name);
        return ITEMS.register(name, item);
    }

}