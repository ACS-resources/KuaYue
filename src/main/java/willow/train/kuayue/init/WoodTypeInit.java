package willow.train.kuayue.init;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WoodTypeInit {
    public static WoodType TrainPanel = WoodType.register(
            new WoodType("train_panel", new BlockSetType("train_panel")));//create("train_panel");
}
