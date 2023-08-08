package willow.train.kuayue.MultiLoader;

import com.simibubi.create.AllPackets;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import com.simibubi.create.foundation.utility.Components;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.ApiStatus;
import willow.train.kuayue.Main;
import willow.train.kuayue.init.KYPackets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Manages sending and receiving registered packets.
 * <pre>
 *     C2S -> Client-to-Server
 *     S2C -> Server-to-Client
 * </pre>
 */
public class PacketSet {

    public final String id;
    public final int version;

    public final ResourceLocation c2sPacket;
    public final ResourceLocation s2cPacket;

    private final List<Function<FriendlyByteBuf, S2CPacket>> s2cPackets;
    private final Object2IntMap<Class<? extends S2CPacket>> s2cTypes;
    private final List<Function<FriendlyByteBuf, C2SPacket>> c2sPackets;
    private final Object2IntMap<Class<? extends C2SPacket>> c2sTypes;

    public static final Map<ResourceLocation, willow.train.kuayue.MultiLoader.PacketSet> HANDLERS = new HashMap<>();

    protected PacketSet(String id, int version,
                        List<Function<FriendlyByteBuf, S2CPacket>> s2cPackets,
                        Object2IntMap<Class<? extends S2CPacket>> s2cTypes,
                        List<Function<FriendlyByteBuf, C2SPacket>> c2sPackets,
                        Object2IntMap<Class<? extends C2SPacket>> c2sTypes) {
        this.id = id;
        this.version = version;

        this.s2cPackets = s2cPackets;
        this.s2cTypes = s2cTypes;

        this.c2sPackets = c2sPackets;
        this.c2sTypes = c2sTypes;

        c2sPacket = new ResourceLocation(id, "c2s");
        s2cPacket = new ResourceLocation(id, "s2c");
    }

    /**
     * Send the given C2S packet to the server.
     */
    @OnlyIn(Dist.CLIENT)
    public void send(C2SPacket packet) {
        int i = idOfC2S(packet);
        if (i != -1) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeVarInt(i);
            packet.write(buf);
            doSendC2S(buf);
        } else {
            throw new IllegalArgumentException("Cannot send unregistered C2SPacket: " + packet);
        }
    }

    /**
     * Send one of Create's packets to the server.
     */
    @OnlyIn(Dist.CLIENT)
    public void send(SimplePacketBase packet) {
        AllPackets.getChannel().sendToServer(packet);
    }

    /**
     * Send the given S2C packet to the given player.
     */
    public void sendTo(ServerPlayer player, S2CPacket packet) {
        sendTo(PlayerSelection.of(player), packet);
    }

    /**
     * Send the given Create packet to the given player.
     */
    public void sendTo(ServerPlayer player, SimplePacketBase packet) {
        AllPackets.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    /**
     * Send the given S2C packet to the given players.
     */
    public void sendTo(PlayerSelection selection, S2CPacket packet) {
        int i = idOfS2C(packet);
        if (i != -1) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeVarInt(i);
            packet.write(buf);
            selection.accept(s2cPacket, buf);
        } else {
            throw new IllegalArgumentException("Cannot send unregistered S2CPacket: " + packet);
        }
    }

    /**
     * Send the given Create packet to the given players.
     */
    public void sendTo(PlayerSelection selection, SimplePacketBase packet) {
        AllPackets.getChannel().send(((PlayerSelection) selection).target, packet);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerS2CListener() {
        HANDLERS.put(s2cPacket, this);
    }

    public void registerC2SListener() {
        HANDLERS.put(c2sPacket, this);
    }

    @OnlyIn(Dist.CLIENT)
    protected void doSendC2S(FriendlyByteBuf buf) {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection != null) {
            connection.send(new ServerboundCustomPayloadPacket(c2sPacket, buf));
        } else {
            Main.LOGGER.error("Cannot send a C2S packet before the client connection exists, skipping!");
        }
    }

    protected int idOfC2S(C2SPacket packet) {
        return c2sTypes.getOrDefault(packet.getClass(), -1);
    }

    protected int idOfS2C(S2CPacket packet) {
        return s2cTypes.getOrDefault(packet.getClass(), -1);
    }

    @OnlyIn(Dist.CLIENT)
    @ApiStatus.Internal
    public void handleS2CPacket(Minecraft mc, FriendlyByteBuf buf) {
        int i = buf.readVarInt();
        if (i < 0 || i >= s2cPackets.size()) {
            Main.LOGGER.error("Invalid S2C Packet {}, ignoring", i);
            return;
        }
        Function<FriendlyByteBuf, S2CPacket> factory = s2cPackets.get(i);
        S2CPacket packet = factory.apply(buf);
        mc.execute(() -> packet.handle(mc));
    }

    @ApiStatus.Internal
    public void handleC2SPacket(ServerPlayer sender, FriendlyByteBuf buf) {
        int i = buf.readVarInt();
        if (i < 0 || i >= s2cPackets.size()) {
            Main.LOGGER.error("Invalid C2S Packet {}, ignoring", i);
            return;
        }
        Function<FriendlyByteBuf, C2SPacket> factory = c2sPackets.get(i);
        C2SPacket packet = factory.apply(buf);
        sender.server.execute(() -> packet.handle(sender));
    }

    @ApiStatus.Internal
    public static willow.train.kuayue.MultiLoader.PacketSet create(String id, int version,
                                                                   List<Function<FriendlyByteBuf, S2CPacket>> s2cPackets,
                                                                   Object2IntMap<Class<? extends S2CPacket>> s2cTypes,
                                                                   List<Function<FriendlyByteBuf, C2SPacket>> c2sPackets,
                                                                   Object2IntMap<Class<? extends C2SPacket>> c2sTypes) {
        return new willow.train.kuayue.MultiLoader.PacketSet(id, version, s2cPackets, s2cTypes, c2sPackets, c2sTypes);
    }

    /**
     * Start building a new PacketSet. Starts with a version sync packet.
     */
    public static willow.train.kuayue.MultiLoader.PacketSet.Builder builder(String id, int version) {
        return new willow.train.kuayue.MultiLoader.PacketSet.Builder(id, version).s2c(willow.train.kuayue.MultiLoader.PacketSet.CheckVersionPacket.class, willow.train.kuayue.MultiLoader.PacketSet.CheckVersionPacket::new);
    }

    /**
     * Send the player a packet with the current network version. If they do not match, the player will disconnect.
     */
    public void onPlayerJoin(ServerPlayer player) {
        sendTo(player, new willow.train.kuayue.MultiLoader.PacketSet.CheckVersionPacket(version));
    }

    public record CheckVersionPacket(int serverVersion) implements S2CPacket {
        public CheckVersionPacket(FriendlyByteBuf buf) {
            this(buf.readVarInt());
        }

        @Override
        public void write(FriendlyByteBuf buffer) {
            buffer.writeVarInt(serverVersion);
        }

        @Override
        public void handle(Minecraft mc) {
            if (KYPackets.PACKETS.version == serverVersion)
                return;
            Component error = Components.literal("KuaYue on the client uses a different network format than the server.")
                    .append(" You should use the same version of the mod on both sides.");
            mc.getConnection().onDisconnect(error);
        }
    }

    public static class Builder {
        public final String id;
        public final int version;

        private final List<Function<FriendlyByteBuf, S2CPacket>> s2cPackets = new ArrayList<>();
        private final Object2IntMap<Class<? extends S2CPacket>> s2cTypes = new Object2IntOpenHashMap<>();
        private final List<Function<FriendlyByteBuf, C2SPacket>> c2sPackets = new ArrayList<>();
        private final Object2IntMap<Class<? extends C2SPacket>> c2sTypes = new Object2IntOpenHashMap<>();

        protected Builder(String id, int version) {
            this.id = id;
            this.version = version;
        }

        public willow.train.kuayue.MultiLoader.PacketSet.Builder s2c(Class<? extends S2CPacket> clazz, Function<FriendlyByteBuf, S2CPacket> factory) {
            s2cPackets.add(factory);
            s2cTypes.put(clazz, s2cPackets.indexOf(factory));
            return this;
        }

        public willow.train.kuayue.MultiLoader.PacketSet.Builder c2s(Class<? extends C2SPacket> clazz, Function<FriendlyByteBuf, C2SPacket> factory) {
            c2sPackets.add(factory);
            c2sTypes.put(clazz, c2sPackets.indexOf(factory));
            return this;
        }

        public willow.train.kuayue.MultiLoader.PacketSet build() {
            return willow.train.kuayue.MultiLoader.PacketSet.create(id, version, s2cPackets, s2cTypes, c2sPackets, c2sTypes);
        }
    }
}
