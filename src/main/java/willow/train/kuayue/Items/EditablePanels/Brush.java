package willow.train.kuayue.Items.EditablePanels;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Brush extends Item {
    public Brush(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, List<Component> tooltips, TooltipFlag p_41424_) {
        tooltips.add(new TranslatableComponent("item.kuayue.brush.tooltip"));
        super.appendHoverText(p_41421_, p_41422_, tooltips, p_41424_);
    }
}
