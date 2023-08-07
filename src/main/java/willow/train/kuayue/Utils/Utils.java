package willow.train.kuayue.Utils;

import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.trains.HonkPacket;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.Main;

import java.nio.file.Path;
import java.util.Locale;

public class Utils {

    public static boolean isModLoaded(String id, @Nullable String fabricId) {
        return ModList.get().isLoaded(id);
    }

    public static Path configDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Contract
    public static boolean isDevEnv() {
        return !FMLLoader.isProduction();
    }

    public static boolean isEnvVarTrue(String name) {
        try {
            String result = System.getenv(name);
            return result != null && result.toLowerCase(Locale.ROOT).equals("true");
        } catch (SecurityException e) {
            Main.LOGGER.warn("Caught a security exception while trying to access environment variable `"+name+"`.");
            return false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isActiveAndMatches(KeyMapping mapping, InputConstants.Key keyCode) {
        return mapping.isActiveAndMatches(keyCode);
    }

    public static void sendCreatePacketToServer(SimplePacketBase packet) {
        AllPackets.getChannel().sendToServer(packet);
    }

    public static void sendHonkPacket(Train train, boolean isHonk) {
        AllPackets.getChannel().send(PacketDistributor.ALL.noArg(), new HonkPacket(train, isHonk));
    }

    public static void postChunkEventClient(LevelChunk chunk, boolean load) {
        if (load) {
            MinecraftForge.EVENT_BUS.post(new ChunkEvent.Load(chunk));
        } else {
            MinecraftForge.EVENT_BUS.post(new ChunkEvent.Unload(chunk));
        }
    }
}