package willow.train.kuayue.init;

import com.simibubi.create.content.trains.bogey.BogeySizes;
import willow.train.kuayue.Main;

public class KYBogeySizes {

    public static final BogeySizes.BogeySize EXTRA = create("coilspring", 6.5f / 16f);

    public static BogeySizes.BogeySize create(String name, float size) {
        return BogeySizes.addSize(Main.asResource(name), size);
    }

    public static void register() {

    }
}
