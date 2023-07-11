package willow.train.kuayue.Util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import willow.train.kuayue.Main;

public class ComponentTranslationTool {

    public static void showMsg(Player pPlayer, String s, boolean b) {
        pPlayer.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + s), b);
    }
}
