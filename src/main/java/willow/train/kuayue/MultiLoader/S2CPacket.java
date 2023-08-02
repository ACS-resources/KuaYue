package willow.train.kuayue.MultiLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public interface S2CPacket {

    void write(FriendlyByteBuf buffer);

    void handle(Minecraft mc);
}
