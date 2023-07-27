package willow.train.kuayue;

import willow.train.kuayue.Util.CustomTrackOverlayRendering;
import willow.train.kuayue.init.KYBlockPartials;
import willow.train.kuayue.init.KYBogeyStyles;
import willow.train.kuayue.init.KYEdgePointTypes;
import willow.train.kuayue.init.KYPackets;

public class KuaYueClient {

    public static void init(){

        //CustomTrackOverlayRendering.register(KYEdgePointTypes.COUPLER, KYBlockPartials.COUPLER_BOTH);
        CustomTrackOverlayRendering.register(KYEdgePointTypes.SWITCH, KYBlockPartials.SWITCH_RIGHT_TURN);

        KYBogeyStyles.registerClient();
        KYBlockPartials.init();
        KYPackets.PACKETS.registerS2CListener();
    }
}
