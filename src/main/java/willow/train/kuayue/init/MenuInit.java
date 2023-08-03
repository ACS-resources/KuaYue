package willow.train.kuayue.init;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import willow.train.kuayue.Client.CarriageNoSignEditMenu;
import willow.train.kuayue.Client.CarriageTypeSignEditMenu;
import willow.train.kuayue.Main;
import willow.train.kuayue.Screen.CarriageNoSignEditScreen;
import willow.train.kuayue.Screen.CarriageTypeSignEditScreen;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MOD_ID);

    public static final RegistryObject<MenuType<CarriageTypeSignEditMenu>> CARRIAGE_TYPE_SIGN_EDIT_MENU = registerMenuType(CarriageTypeSignEditMenu::new, "carriage_type_sign_edit_menu");
    public static final RegistryObject<MenuType<CarriageNoSignEditMenu>> CARRIAGE_NO_SIGN_EDIT_MENU = registerMenuType(CarriageNoSignEditMenu::new, "carriage_no_sign_edit_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
