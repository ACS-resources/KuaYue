package willow.train.kuayue.forge.events;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import willow.train.kuayue.Config;

public class ClientEvents {

    public static void onClientTickStart(Minecraft mc) {
        //PhantomSpriteManager.tick(mc);
        /*if (DummyRailwayMarkerHandler.getInstance() == null)
            return;*/

        Level level = mc.level;
        long ticks = level == null ? 1 : level.getGameTime();
        if (ticks % Config.JOURNEYMAP_REMOVE_OBSOLETE_TICKS.get() == 0) {
            //DummyRailwayMarkerHandler.getInstance().removeObsolete();
            //DummyRailwayMarkerHandler.getInstance().reloadMarkers();
        }
//            DummyRailwayMarkerHandler.getInstance().removeObsolete(CreateClient.RAILWAYS.trains.keySet());

        if (ticks % Config.JOURNEYMAP_UPDATE_TICKS.get() == 0) {
            //DummyRailwayMarkerHandler.getInstance().runUpdates();
/*            for (Train train : CreateClient.RAILWAYS.trains.values()) {
                DummyRailwayMarkerHandler.getInstance().addOrUpdateTrain(train);
            }*/
        }

        if (isGameActive()) {
            //BogeyCategoryHandlerClient.clientTick();
            //ConductorPossessionController.onClientTick(mc, true);
        }
    }

    public static void onClientTickEnd(Minecraft mc) {
        /*if (isGameActive()) {
            ConductorPossessionController.onClientTick(mc, false);
        }*/
    }

    public static void onClientWorldLoad(Level level) {
        //DummyRailwayMarkerHandler.getInstance().onJoinWorld();
        //PhantomSpriteManager.firstRun = true;
        //CRExtraRegistration.register();
    }

    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }

    public static void onKeyInput(int key, boolean pressed) {
        if (Minecraft.getInstance().screen != null)
            return;
        //BogeyCategoryHandlerClient.onKeyInput(key, pressed);
    }
}
