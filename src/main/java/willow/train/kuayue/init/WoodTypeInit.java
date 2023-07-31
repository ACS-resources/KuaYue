package willow.train.kuayue.init;

import net.minecraft.world.level.block.state.properties.WoodType;

public class WoodTypeInit {
    public static WoodType TrainPanel = new SelfRegisterWoodType("kuayue:train_panel");

    public static class SelfRegisterWoodType extends WoodType {
        public SelfRegisterWoodType(String name) {
            super(name);
            WoodType.register(this);
        }
    }
}
