package willow.train.kuayue.Client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.Catenary.ConnectionEvent;
import willow.train.kuayue.Main;

import java.util.HashMap;

public class CatenaryConnectionHandler {
    HashMap<Player , ConnectionEvent> EVENT = new HashMap<>();

    public CatenaryConnectionHandler(){}

    public void registerPos(Player player , Level level, Vec3 pos , String catenaryType , BlockPos block){
        if(!EVENT.containsKey(player)){
            EVENT.put(player , new ConnectionEvent(player , level , catenaryType));
        }
        EVENT.get(player).setPos(pos , block);
    }

    public boolean canConnect(Player player){
        return EVENT.get(player).canConnect();
    }

    public void connect(Player player){
        EVENT.get(player).connect();
        EVENT.remove(player);
        return;
    }

    public void remove(Player player){
        if (EVENT.containsKey(player)){
            EVENT.remove(player);
            player.displayClientMessage(new TranslatableComponent("msg." + Main.MOD_ID + ".cancelled") , true);
        }
    }
}
