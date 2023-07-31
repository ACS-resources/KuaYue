package willow.train.kuayue.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.init.BlockInit;
import willow.train.kuayue.init.ItemInit;

public class DietTab extends TabBuilder {
    public DietTab(String modId) {
        super(modId, "item_group_diet");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ItemInit.CA_25T.get());
    }
}

