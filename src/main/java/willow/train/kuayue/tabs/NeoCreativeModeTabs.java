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

public class NeoCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MOD_ID);
    public static final RegistryObject<CreativeModeTab> KUAYUE_MAIN = TAB_REGISTER.register("base",
            () -> {
                return CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.item_group_main"))
                        .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                        .icon(() -> new ItemStack(BlockInit.CR_LOGO.get().asItem()))
                        .displayItems((p_270425_, p_260158_) -> {
                            p_260158_.accept(BlockInit.CR_LOGO.get());
                        })
                        .build();
            });



}
