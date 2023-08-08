package willow.train.kuayue.events;

import net.minecraft.client.Minecraft;
import willow.train.kuayue.Blocks.Bogeys.selection_menu.BogeyCategoryHandlerClient;

public class ClientEvents {

    public static void onClientTickStart(Minecraft mc) {

        if (isGameActive()) {
            BogeyCategoryHandlerClient.clientTick();
        }
    }

    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }

    public static void onKeyInput(int key, boolean pressed) {
        if (Minecraft.getInstance().screen != null)
            return;
        BogeyCategoryHandlerClient.onKeyInput(key, pressed);
    }
}
