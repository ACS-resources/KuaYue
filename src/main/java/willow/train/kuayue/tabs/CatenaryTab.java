package willow.train.kuayue.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.init.BlockInit;

public class CatenaryTab extends TabBuilder {
    public CatenaryTab(String modId) {
        super(modId, "item_group_catenary");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(BlockInit.Catenary_Grid_C1.get());
    }
}

