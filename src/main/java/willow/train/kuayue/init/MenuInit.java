package willow.train.kuayue.init;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import willow.train.kuayue.Client.CarriageNoSignEditMenu;
import willow.train.kuayue.Client.CarriageTypeSignEditMenu;
import willow.train.kuayue.Main;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MOD_ID);

    public static final RegistryObject<MenuType<CarriageTypeSignEditMenu>> CARRIAGE_TYPE_SIGN_EDIT_MENU = registerMenuType(CarriageTypeSignEditMenu::new, "carriage_type_sign_edit_menu");
    public static final RegistryObject<MenuType<CarriageNoSignEditMenu>> CARRIAGE_NO_SIGN_EDIT_MENU = registerMenuType(CarriageNoSignEditMenu::new, "carriage_no_sign_edit_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
