package willow.train.kuayue.Network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import willow.train.kuayue.BlockEntity.CarriageNoSignEntity;

public class CarriageNoSignUpdatePacket implements KuayuePacket{

    private final BlockPos pos;
    private final Component message;
    private final int color;

    private final boolean isLeftSide;

    public CarriageNoSignUpdatePacket(BlockPos pos, Component message, int color, boolean isLeftSide) {
        this.pos = pos;
        this.message = message;
        this.color = color;
        this.isLeftSide = isLeftSide;
    }

    public CarriageNoSignUpdatePacket(FriendlyByteBuf buffer){
        this.pos = buffer.readBlockPos();
        this.color = buffer.readInt();
        this.message = Component.translatable(buffer.readUtf());
        this.isLeftSide = buffer.getBoolean(0);
    }
    @Override
    public boolean handle(NetworkEvent.Context var1) {
        var1.enqueueWork(
                () -> {
                    ServerPlayer sender = var1.getSender();
                    ServerLevel level = sender.getLevel();

                    BlockEntity entity = level.getBlockEntity(pos);
                    if (entity == null){ return;}

                    if (entity instanceof CarriageNoSignEntity){
                        ((CarriageNoSignEntity)entity).setMessage(message);
                        ((CarriageNoSignEntity)entity).setColor(color);
                        ((CarriageNoSignEntity)entity).setLeftSide(isLeftSide);
                    }
                }
        );
        return true;
    }

    @Override
    public void encode(FriendlyByteBuf var1) {
        var1.writeBlockPos(pos);
        var1.writeInt(color);
        var1.writeUtf(message.getString());
        var1.writeBoolean(isLeftSide);
    }
}
