package willow.train.kuayue.bases.datafixerapi;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import org.jetbrains.annotations.*;
import willow.train.kuayue.Config;
import willow.train.kuayue.Main;
import willow.train.kuayue.base.datafixerapi.DataFixesInternalsImpl;
import willow.train.kuayue.base.datafixerapi.NoOpDataFixesInternals;

import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;

@ApiStatus.Internal
public abstract class DataFixesInternals {

    public static final BiFunction<Integer, Schema, Schema> BASE_SCHEMA = (version, parent) -> {
        checkArgument(version == 0, "version must be 0");
        checkArgument(parent == null, "parent must be null");
        return get().createBaseSchema();
    };

    public record DataFixerEntry(DataFixer dataFixer, int currentVersion) {}

    @Contract(pure = true)
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int getModDataVersion(@NotNull CompoundTag compound) {
        return compound.getInt("Kuayue_DataVersion");
    }

    private static willow.train.kuayue.base.datafixerapi.DataFixesInternals instance;

    public static @NotNull willow.train.kuayue.base.datafixerapi.DataFixesInternals get() {
        if (instance == null) {
            Schema latestVanillaSchema;
            try {
                latestVanillaSchema = DataFixers.getDataFixer()
                        .getSchema(DataFixUtils.makeKey(SharedConstants.getCurrentVersion().getDataVersion().getVersion()));
            } catch (Exception e) {
                latestVanillaSchema = null;
            }

            if (latestVanillaSchema == null) {
                Main.LOGGER.warn("[Kuayue DFU] Failed to initialize! Either someone stopped DFU from initializing,");
                Main.LOGGER.warn("[Kuayue DFU]  or this Minecraft build is hosed.");
                Main.LOGGER.warn("[Kuayue DFU] Using no-op implementation.");
                instance = new NoOpDataFixesInternals();
            } else {
                if (Config.DISABLE_DATAFIXER.get())
                    instance = new NoOpDataFixesInternals();
                else
                    instance = new DataFixesInternalsImpl(latestVanillaSchema);
            }
        }

        return instance;
    }

    public abstract void registerFixer(@Range(from = 0, to = Integer.MAX_VALUE) int currentVersion,
                                       @NotNull DataFixer dataFixer);

    public abstract @Nullable willow.train.kuayue.base.datafixerapi.DataFixesInternals.DataFixerEntry getFixerEntry();

    @Contract(value = "-> new", pure = true)
    public abstract @NotNull Schema createBaseSchema();

    public abstract @NotNull CompoundTag updateWithAllFixers(@NotNull DataFixTypes dataFixTypes, @NotNull CompoundTag compound);

    public abstract @NotNull CompoundTag addModDataVersions(@NotNull CompoundTag compound);
}
