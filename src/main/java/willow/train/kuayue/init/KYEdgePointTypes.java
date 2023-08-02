package willow.train.kuayue.init;

import com.simibubi.create.content.trains.graph.EdgePointType;
import willow.train.kuayue.Blocks.Coupling.coupler.TrackCoupler;
import willow.train.kuayue.Blocks.Switches.TrackSwitch;
import willow.train.kuayue.Main;

public class KYEdgePointTypes {

    public static final EdgePointType<TrackCoupler> COUPLER = EdgePointType.register(Main.asResource("coupler"), TrackCoupler::new);
    public static final EdgePointType<TrackSwitch> SWITCH = EdgePointType.register(Main.asResource("switch"), TrackSwitch::new);

    @SuppressWarnings("EmptyMethod")
    public static void register() {}
}
