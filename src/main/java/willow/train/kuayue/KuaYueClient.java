package willow.train.kuayue;

import willow.train.kuayue.Util.CustomTrackOverlayRendering;
import willow.train.kuayue.init.KYBlockPartials;
import willow.train.kuayue.init.KYEdgePointTypes;

public class KuaYueClient {

    public static void init(){

        CustomTrackOverlayRendering.register(KYEdgePointTypes.COUPLER, KYBlockPartials.COUPLER_BOTH);
        CustomTrackOverlayRendering.register(KYEdgePointTypes.SWITCH, KYBlockPartials.SWITCH_RIGHT_TURN);
    }
}
