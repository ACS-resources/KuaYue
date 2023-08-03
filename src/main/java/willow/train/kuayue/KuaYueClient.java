package willow.train.kuayue;

import net.minecraft.client.renderer.Sheets;
import willow.train.kuayue.Util.CustomTrackOverlayRendering;
import willow.train.kuayue.init.*;

public class KuaYueClient {

    public static void init(){

        KYBogeyStyles.registerClient();
        KYBlockPartials.init();
        KYPackets.PACKETS.registerS2CListener();

    }
}
