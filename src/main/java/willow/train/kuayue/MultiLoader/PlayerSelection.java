package willow.train.kuayue.MultiLoader;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class PlayerSelection {

    public abstract void accept(ResourceLocation id, FriendlyByteBuf buffer);

    public static PlayerSelection all() {
        throw new AssertionError();
    }

    public static PlayerSelection of(ServerPlayer player) {
        throw new AssertionError();
    }

    public static PlayerSelection tracking(Entity entity) {
        throw new AssertionError();
    }

    public static PlayerSelection tracking(BlockEntity be) {
        throw new AssertionError();
    }

    public static PlayerSelection tracking(ServerLevel level, BlockPos pos) {
        throw new AssertionError();
    }

    public static PlayerSelection trackingAndSelf(ServerPlayer player) {
        throw new AssertionError();
    }
}
