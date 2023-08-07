package willow.train.kuayue.Util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrationListening {

    private static final Set<Listener<?>> listeners = new HashSet<>();

    public static <T> void whenRegistered(Registry<T> registry, ResourceLocation id, Consumer<T> consumer) {
        addListener(new Listener<>(registry, id, consumer));
    }

    public static <T> void whenBothRegistered(Registry<T> registry, ResourceLocation id1, ResourceLocation id2, BiConsumer<T, T> consumer) {
        whenBothRegistered(registry, id1, registry, id2, consumer);
    }

    public static <T, U> void whenBothRegistered(Registry<T> registry1, ResourceLocation id1,
                                                 Registry<U> registry2, ResourceLocation id2,
                                                 BiConsumer<T, U> consumer) {
        DualListener<T, U> dual = new DualListener<>(registry1, id1, registry2, id2, consumer);
        addListener(dual.listener1);
        addListener(dual.listener2);
    }

    public static <T> void addListener(Listener<T> listener) {
        listeners.add(listener);
    }

    public record Listener<T>(Registry<T> registry, ResourceLocation id, Consumer<T> consumer) {
        public void onRegister(T obj) {
            consumer.accept(obj);
        }
    }

    public static class DualListener<T, U> {
        public final Listener<T> listener1;
        public final Listener<U> listener2;
        private final BiConsumer<T, U> consumer;
        private T first;
        private U second;

        public DualListener(Registry<T> registry1, ResourceLocation id1,
                            Registry<U> registry2, ResourceLocation id2,
                            BiConsumer<T, U> consumer) {
            this.consumer = consumer;
            listener1 = new Listener<>(registry1, id1, this::firstRegistered);
            listener2 = new Listener<>(registry2, id2, this::secondRegistered);
        }

        private void firstRegistered(T first) {
            this.first = first;
            if (second != null)
                bothRegistered();
        }

        private void secondRegistered(U second) {
            this.second = second;
            if (first != null)
                bothRegistered();
        }

        private void bothRegistered() {
            consumer.accept(first, second);
        }
    }

    @SubscribeEvent
    public static void afterRegistration(InterModEnqueueEvent event) {
        event.enqueueWork(() -> listeners.forEach(RegistrationListening::handle));
    }

    private static <T> void handle(Listener<T> listener) {
        ResourceLocation id = listener.id();
        T obj = listener.registry().get(id);
        if (obj != null)
            listener.onRegister(obj);
    }
}
