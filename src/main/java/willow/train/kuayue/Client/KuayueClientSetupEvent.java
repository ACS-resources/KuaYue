package willow.train.kuayue.Client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import willow.train.kuayue.Main;
import willow.train.kuayue.init.EntityInit;
import willow.train.kuayue.renderer.CatenaryBaseRenderer;
import willow.train.kuayue.renderer.SmallCatenaryBaseRenderer;

@Mod.EventBusSubscriber(modid = Main.MOD_ID , bus = Mod.EventBusSubscriber.Bus.MOD , value = Dist.CLIENT)
public class KuayueClientSetupEvent {
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event){
        EntityRenderers.register(EntityInit.CATENARY_BASE.get() , CatenaryBaseRenderer::new);
        EntityRenderers.register(EntityInit.SMALL_CATENARY_BASE.get() , SmallCatenaryBaseRenderer::new);
    }
}
