package willow.train.kuayue.init;

import willow.train.kuayue.Main;
import willow.train.kuayue.MultiLoader.PacketSet;
import willow.train.kuayue.Util.packet.BogeyStyleSelectionPacket;

public class KYPackets {

    public static final PacketSet PACKETS = PacketSet.builder(Main.MOD_ID, 8) // increment version on changes

            //.c2s(MountedToolboxDisposeAllPacket.class, MountedToolboxDisposeAllPacket::new)
            //.c2s(MountedToolboxEquipPacket.class, MountedToolboxEquipPacket::new)
            //.c2s(SlabUseOnCurvePacket.class, SlabUseOnCurvePacket::new)
            .c2s(BogeyStyleSelectionPacket.class, BogeyStyleSelectionPacket::new)
            //.c2s(DismountCameraPacket.class, DismountCameraPacket::new)
            //.c2s(CameraMovePacket.class, CameraMovePacket::new)
            //.c2s(SpyConductorInteractPacket.class, SpyConductorInteractPacket::new)

            //.s2c(JukeboxCartPacket.class, JukeboxCartPacket::new)
            //.s2c(MountedToolboxSyncPacket.class, MountedToolboxSyncPacket::new)
            //.s2c(ModVersionPacket.class, ModVersionPacket::new)
            //.s2c(CarriageContraptionEntityUpdatePacket.class, CarriageContraptionEntityUpdatePacket::new)
            //.s2c(ChopTrainEndPacket.class, ChopTrainEndPacket::new)
            //.s2c(AddTrainEndPacket.class, AddTrainEndPacket::new)
            //.s2c(TrackCouplerClientInfoPacket.class, TrackCouplerClientInfoPacket::new)
            //.s2c(TrainMarkerDataUpdatePacket.class, TrainMarkerDataUpdatePacket::new)
            //.s2c(OverridableSignalPacket.class, OverridableSignalPacket::new)
            //.s2c(SwitchDataUpdatePacket.class, SwitchDataUpdatePacket::new)
            //.s2c(SetCameraViewPacket.class, SetCameraViewPacket::new)
            //.s2c(CameraMovePacket.class, CameraMovePacket::new)

            .build();
}
