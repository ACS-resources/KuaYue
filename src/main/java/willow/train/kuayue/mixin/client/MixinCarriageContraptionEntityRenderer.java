package willow.train.kuayue.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntityRenderer;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import willow.train.kuayue.Blocks.Bogeys.df11g_bogey.DF11GBogeyRenderer;
import willow.train.kuayue.mixin.AccessorCarriageBogey;

@Mixin(value = CarriageContraptionEntityRenderer.class, remap = false)
public class MixinCarriageContraptionEntityRenderer {

    @Inject(method = "translateBogey", at = @At("TAIL"), remap = false)
    private static void sendValueToBogeyRenderer(PoseStack ms, CarriageBogey bogey, int bogeySpacing, float viewYRot, float viewXRot, float partialTicks, CallbackInfo ci) {

        LerpedFloat tempYaw = ((AccessorCarriageBogey) bogey).getYaw();
        //DF11GBogeyRenderer.SmallDF11GBogeyRenderer.yaw = tempYaw;

        LerpedFloat tempPitch = ((AccessorCarriageBogey) bogey).getPitch();
        //DF11GBogeyRenderer.SmallDF11GBogeyRenderer.pitch = tempPitch;

        Couple<Vec3> tempCouplingAnchors = bogey.couplingAnchors;
        //DF11GBogeyRenderer.SmallDF11GBogeyRenderer.couplingAnchors = tempCouplingAnchors;
    }
}
