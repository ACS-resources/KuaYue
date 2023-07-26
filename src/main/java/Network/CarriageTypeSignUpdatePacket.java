package Network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;
import willow.train.kuayue.Entity.CarriageTypeSignEntity;

import java.util.logging.Level;

public class CarriageTypeSignUpdatePacket implements KuayuePacket {
    private final BlockPos pos;
    private final String[] lines;

    public CarriageTypeSignUpdatePacket(BlockPos pPos, String pLine0, String pLine1, String pLine2, String pLine3, String pLine4) {
        this.pos = pPos;
        this.lines = new String[]{pLine0, pLine1, pLine2, pLine3, pLine4};
    }

    public CarriageTypeSignUpdatePacket(FriendlyByteBuf pBuffer) {
        this.pos = pBuffer.readBlockPos();
        this.lines = new String[5];

        for(int i = 0; i < 5; ++i) {
            this.lines[i] = pBuffer.readUtf(384);
        }

    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeBlockPos(this.pos);

        for(int i = 0; i < 5; ++i) {
            pBuffer.writeUtf(this.lines[i]);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */



    public BlockPos getPos() {
        return this.pos;
    }

    public String[] getLines() {
        return this.lines;
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        System.out.println("handling");

        context.enqueueWork(() -> {
            ServerLevel level = context.getSender().getLevel();
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity == null){
                return;
            }
            if(entity instanceof CarriageTypeSignEntity){
                ((CarriageTypeSignEntity) entity).setMessages(this.lines);
            }
        });
        return true;
    }

    @Override
    public void encode(FriendlyByteBuf pBuffer) {
        pBuffer.writeBlockPos(this.pos);

        for(int i = 0; i < 5; ++i) {
            pBuffer.writeUtf(this.lines[i]);
        }
    }
}
