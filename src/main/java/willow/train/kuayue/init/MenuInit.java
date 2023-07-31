package willow.train.kuayue.init;

import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import willow.train.kuayue.Client.CarriageNoSignEditMenu;
import willow.train.kuayue.Client.CarriageTypeSignEditMenu;
import willow.train.kuayue.Main;

public class MenuInit {
    public static final LazyRegistrar<MenuType<?>> MENUS = LazyRegistrar.create(Registry.MENU, Main.MOD_ID);

    public static final RegistryObject<MenuType<CarriageTypeSignEditMenu>> CARRIAGE_TYPE_SIGN_EDIT_MENU = MENUS.register("carriage_type_sign_edit_menu", () -> new ExtendedScreenHandlerType<CarriageTypeSignEditMenu>(CarriageTypeSignEditMenu::new));
    public static final RegistryObject<MenuType<CarriageNoSignEditMenu>> CARRIAGE_NO_SIGN_EDIT_MENU = MENUS.register("carriage_no_sign_edit_menu", () -> new ExtendedScreenHandlerType<CarriageNoSignEditMenu>(CarriageNoSignEditMenu::new));

    public static void register() {
        MENUS.register();
    }
}
