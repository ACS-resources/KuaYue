package willow.train.kuayue.Multiloader;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

public enum Env {

    CLIENT, SERVER;

    public static final willow.train.kuayue.MultiLoader.Env CURRENT = getCurrent();

    public boolean isCurrent() {
        return this == CURRENT;
    }

    public void runIfCurrent(Supplier<Runnable> run) {
        if (isCurrent())
            run.get().run();
    }

    @ApiStatus.Internal
    public static willow.train.kuayue.MultiLoader.Env getCurrent() {
        return FMLEnvironment.dist == Dist.CLIENT ? willow.train.kuayue.MultiLoader.Env.CLIENT : willow.train.kuayue.MultiLoader.Env.SERVER;
    }
}
