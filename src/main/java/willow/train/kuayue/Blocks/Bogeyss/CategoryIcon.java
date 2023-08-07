package willow.train.kuayue.Blocks.Bogeyss;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.Main;

public class CategoryIcon implements ItemLike {

    public final ResourceLocation location;

    public CategoryIcon(ResourceLocation location) {
        this.location = location;
    }

    public static willow.train.kuayue.Blocks.Bogeys.CategoryIcon standard(String name) {
        return new willow.train.kuayue.Blocks.Bogeys.CategoryIcon(Main.asResource("textures/gui/bogey_icons/"+name+".png"));
    }

    public static NonNullSupplier<willow.train.kuayue.Blocks.Bogeys.CategoryIcon> standardSupplier(String name) {
        return () -> standard(name);
    }

    @Override
    public @NotNull Item asItem() {
        return Items.AIR;
    }
}
