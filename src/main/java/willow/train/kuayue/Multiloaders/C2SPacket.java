package willow.train.kuayue.Multiloaders;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public interface C2SPacket {

    void write(FriendlyByteBuf buffer);

    void handle(ServerPlayer sender);
}