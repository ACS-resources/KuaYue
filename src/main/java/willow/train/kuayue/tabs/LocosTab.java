package willow.train.kuayue.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.init.ItemInit;

public class LocosTab extends TabBuilder {
    public LocosTab(String modId) {
        super(modId, "item_locos");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ItemInit.SERIES_25_LOGOS.get());
    }
}

