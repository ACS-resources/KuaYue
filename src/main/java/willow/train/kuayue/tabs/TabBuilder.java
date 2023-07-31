package willow.train.kuayue.tabs;

import org.jetbrains.annotations.NotNull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public abstract class TabBuilder {
    private ResourceLocation id;

    public TabBuilder(String modId, String path) {
        this.id = new ResourceLocation(modId, path);
    }

    public final CreativeModeTab into() {
        return FabricItemGroupBuilder.create(this.id).icon(() -> this.makeIcon()).build();
    }

    @Environment(EnvType.CLIENT)
    public abstract @NotNull ItemStack makeIcon();
}
