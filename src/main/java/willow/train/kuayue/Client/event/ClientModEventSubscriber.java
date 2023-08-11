package willow.train.kuayue.Client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import willow.train.kuayue.EntityModels.CarriageNoSignModel;
import willow.train.kuayue.EntityModels.LaqueredBoardLogo;
import willow.train.kuayue.Main;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventSubscriber {

    /**
     * 记得在这里注册自己的模型，像以下这个例子写就好，对应的模型在 EntityModel 包里
     * @param event -
     */
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(CarriageNoSignModel.CARRIAGE_NO_SIGN_MODEL_LAYER, CarriageNoSignModel::createBodyLayer);
        event.registerLayerDefinition(LaqueredBoardLogo.LAYER_A, LaqueredBoardLogo::createBodyLayer);
    }
}
