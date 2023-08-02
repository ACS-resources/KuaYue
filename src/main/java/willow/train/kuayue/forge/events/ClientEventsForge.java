package willow.train.kuayue.forge.events;

import com.simibubi.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.redstone.link.controller.LinkedControllerClientHandler;
import com.simibubi.create.content.trains.track.TrackPlacement;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static willow.train.kuayue.forge.events.ClientEvents.isGameActive;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEventsForge {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            ClientEvents.onClientTickStart(Minecraft.getInstance());
        else if (event.phase == TickEvent.Phase.END)
            ClientEvents.onClientTickEnd(Minecraft.getInstance());
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        ClientEvents.onClientWorldLoad((Level) event.getWorld());
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        int key = event.getKey();
        boolean pressed = event.getAction() != 0;
        ClientEvents.onKeyInput(key, pressed);
    }

    /*@SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event){

        if (!isGameActive())
            return;

        Level world = Minecraft.getInstance().level;
        if (event.phase == TickEvent.Phase.START) {
            LinkedControllerClientHandler.tick();
            ControlsHandler.tick();
            AirCurrent.tickClientPlayerSounds();
            return;
        }

        TrackPlacement.clientTick();
    }*/
}
