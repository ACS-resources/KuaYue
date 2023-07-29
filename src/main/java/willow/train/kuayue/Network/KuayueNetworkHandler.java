package willow.train.kuayue.Network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import willow.train.kuayue.Main;

import java.util.function.Function;

public class KuayueNetworkHandler {

    private static SimpleChannel INSTANCE;

    private static int ID = 0;

    public static void registerPackets() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Main.MOD_ID, "main"), () -> "1.0", s -> true, s -> true);

        register(CarriageTypeSignUpdatePacket.class, CarriageTypeSignUpdatePacket::new);
    }

    private static <T extends KuayuePacket> void register(Class<T> type, Function<FriendlyByteBuf, T> decoder){
        INSTANCE.messageBuilder(type, ID++)
                .encoder(KuayuePacket::encode)
                .decoder(decoder)
                .consumer(((first, second) -> first.handle(second.get()) || false))
                .add();
    }

    public static void sendToClient(KuayuePacket packet, ServerPlayer player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void boardcastToClientsWatching(KuayuePacket packet, ServerLevel level, ChunkPos pos) {
        level.getChunkSource().chunkMap.getPlayers(pos, false).forEach(player -> {sendToClient(packet, player);});
    }

    public static void sendToServer(KuayuePacket packet) {INSTANCE.sendToServer(packet);}
}
