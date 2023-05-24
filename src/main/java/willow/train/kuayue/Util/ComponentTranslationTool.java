package willow.train.kuayue.Util;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import willow.train.kuayue.Main;

public class ComponentTranslationTool {

    public static void showMsg(Player pPlayer, String s, boolean b) {
        pPlayer.displayClientMessage(new TranslatableComponent("msg." + Main.MOD_ID + ".catenary_removed"), true);
    }
}
