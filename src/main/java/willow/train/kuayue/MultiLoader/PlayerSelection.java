package willow.train.kuayue.MultiLoader;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.PacketDistributor;

public class PlayerSelection {

    final PacketDistributor.PacketTarget target;

    private PlayerSelection(PacketDistributor.PacketTarget target) {
        this.target = target;
    }

    public void accept(ResourceLocation id, FriendlyByteBuf buffer) {
        ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(id, buffer);
        target.send(packet);
    }

    public static PlayerSelection all() {
        return new PlayerSelection(PacketDistributor.ALL.noArg());
    }

    public static PlayerSelection of(ServerPlayer player) {
        return new PlayerSelection(PacketDistributor.PLAYER.with(() -> player));
    }

    public static PlayerSelection tracking(Entity entity) {
        return new PlayerSelection(PacketDistributor.TRACKING_ENTITY.with(() -> entity));
    }

    public static PlayerSelection tracking(BlockEntity be) {
        LevelChunk chunk = be.getLevel().getChunkAt(be.getBlockPos());
        return new PlayerSelection(PacketDistributor.TRACKING_CHUNK.with(() -> chunk));
    }

    public static PlayerSelection tracking(ServerLevel level, BlockPos pos) {
        LevelChunk chunk = level.getChunkAt(pos);
        return new PlayerSelection(PacketDistributor.TRACKING_CHUNK.with(() -> chunk));
    }

    public static PlayerSelection trackingAndSelf(ServerPlayer player) {
        return new PlayerSelection(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
    }
}
