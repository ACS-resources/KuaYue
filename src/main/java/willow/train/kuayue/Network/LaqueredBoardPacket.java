package willow.train.kuayue.Network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import willow.train.kuayue.BlockEntity.CarriageTypeSignEntity;
import willow.train.kuayue.BlockEntity.LaqueredBoardEntity;

public class LaqueredBoardPacket implements KuayuePacket{

    int[] colors;
    String[] contents;

    double x_offset;

    BlockPos pos;
    public LaqueredBoardPacket(BlockPos pPos, String[] contents, double x_offset, int... colors){
        this.pos = pPos;
        this.contents = contents;
        this.colors = colors;
        this.x_offset = x_offset;
    }

    public LaqueredBoardPacket(BlockPos pPos, Component[] contents, double x_offset, int... colors){
        this.pos = pPos;
        this.contents = new String[contents.length];
        this.x_offset = x_offset;
        for(int i = 0; i < contents.length; i++){
            this.contents[i] = contents[i].getString();
        }
        this.colors = colors;
    }

    public LaqueredBoardPacket(FriendlyByteBuf buf){
        this.pos = buf.readBlockPos();
        int sLength = buf.readInt();
        int cLength = buf.readInt();
        this.x_offset = buf.readDouble();
        this.contents = new String[sLength];
        this.colors = new int[cLength];

        for(int i = 0; i < sLength; i++){
            this.contents[i] = buf.readUtf();
        }
        for(int i = 0; i < cLength; i++){
            this.colors[i] = buf.readInt();
        }
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ServerLevel level = context.getSender().getLevel();
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity == null){
                return;
            }
            if(entity instanceof LaqueredBoardEntity){
                ((LaqueredBoardEntity) entity).setMessages(this.contents);
                ((LaqueredBoardEntity) entity).setColors(this.colors);
                ((LaqueredBoardEntity) entity).setXOffset(this.x_offset);
            }
        });
        return true;
    }

    @Override
    public void encode(FriendlyByteBuf var1) {
        var1.writeBlockPos(this.pos);
        var1.writeInt(this.contents.length); //String[] 长度
        var1.writeInt(this.colors.length); //int[] 长度
        var1.writeDouble(this.x_offset);
        for(String s : contents) {
            var1.writeUtf(s);
        }
        for(int c : colors){
            var1.writeInt(c);
        }
    }
}
