package willow.train.kuayue.Util;

import net.minecraft.world.level.block.state.properties.WoodType;
import willow.train.kuayue.Main;

public class KYWoodType extends WoodType {

    public KYWoodType(String string) {
        super(Main.MOD_ID + "_" + string);
    }

}
