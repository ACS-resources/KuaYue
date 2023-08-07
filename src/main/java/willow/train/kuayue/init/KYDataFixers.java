package willow.train.kuayue.init;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.Util;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import willow.train.kuayue.Main;
import willow.train.kuayue.base.datafixerapi.DataFixesInternals;

import java.util.function.BiFunction;

import static willow.train.kuayue.base.datafixerapi.DataFixesInternals.BASE_SCHEMA;

public class KYDataFixers {

    private static final BiFunction<Integer, Schema, Schema> SAME = Schema::new;
    private static final BiFunction<Integer, Schema, Schema> SAME_NAMESPACED = NamespacedSchema::new;
    public static void register() {
        Main.LOGGER.info("Registering data fixers");
        DataFixesInternals api = DataFixesInternals.get();

        DataFixerBuilder builder = new DataFixerBuilder(1);
        addFixers(builder);
        api.registerFixer(1, builder.buildOptimized(Util.bootstrapExecutor()));
    }

    private static void addFixers(DataFixerBuilder builder) {
        builder.addSchema(0, BASE_SCHEMA);

    }
}
