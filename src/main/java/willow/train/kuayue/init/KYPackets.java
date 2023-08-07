package willow.train.kuayue.init;

import willow.train.kuayue.Main;
import willow.train.kuayue.MultiLoader.PacketSet;
import willow.train.kuayue.Util.packet.BogeyStyleSelectionPacket;

public class KYPackets {

    public static final PacketSet PACKETS = PacketSet.builder(Main.MOD_ID, 8) // increment version on changes


            .c2s(BogeyStyleSelectionPacket.class, BogeyStyleSelectionPacket::new)


            .build();
}
