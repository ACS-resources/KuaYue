package willow.train.kuayue.Multiloader;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface S2CPacket {

    void write(FriendlyByteBuf buffer);

    @OnlyIn(Dist.CLIENT)
    void handle(Minecraft mc);
}
