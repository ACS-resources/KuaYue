package willow.train.kuayue.Util.packet;

import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.Blocks.Bogeys.selection_menu.BogeyCategoryHandlerServer;
import willow.train.kuayue.MultiLoader.C2SPacket;

public class BogeyStyleSelectionPacket implements C2SPacket {

    final BogeyStyle style;

    public BogeyStyleSelectionPacket(@NotNull BogeyStyle style) {
        this.style = style;
    }

    public BogeyStyleSelectionPacket(FriendlyByteBuf buf) {
        ResourceLocation loc = buf.readResourceLocation();
        style = AllBogeyStyles.BOGEY_STYLES.getOrDefault(loc, AllBogeyStyles.STANDARD);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(style.name);
    }

    @Override
    public void handle(ServerPlayer sender) {
        BogeyCategoryHandlerServer.selectedStyles.put(sender.getUUID(), style);
    }
}
