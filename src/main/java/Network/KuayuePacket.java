package Network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public interface KuayuePacket {

    boolean handle(NetworkEvent.Context var1);

    void encode(FriendlyByteBuf var1);
}
