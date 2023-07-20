package willow.train.kuayue.Blocks.Bogeys.selection_menu;

import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BogeyCategoryHandlerServer {
    public static Map<UUID, BogeyStyle> selectedStyles = new HashMap<>();

    public static BogeyStyle getStyle(UUID uuid) {
        if (selectedStyles.containsKey(uuid))
            return selectedStyles.get(uuid);
        return AllBogeyStyles.STANDARD;
    }

    @Nullable
    public static UUID currentPlayer = null;
}
