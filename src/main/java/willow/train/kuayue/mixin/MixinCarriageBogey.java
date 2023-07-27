package willow.train.kuayue.mixin;

import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CarriageBogey.class, remap = false)
public class MixinCarriageBogey {

    @Shadow
    private AbstractBogeyBlock<?> type;

    @Shadow public Couple<Vec3> couplingAnchors;

    /*没什么用的方法，Kuayue的轨道用不到。*/

    /*@Redirect(method = "read", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;", remap = true))
    private static String snr$updateKYStandardBogey(CompoundTag instance, String key) {
        String value = instance.getString(key);
        if (value.equals("railways:mono_bogey_upside_down")) {
            value = "railways:mono_bogey";
            instance.putString(key, value);
            instance.putBoolean("UpsideDown", true);
        }
        return value;
    }*/

    /*@Inject(method = "updateCouplingAnchor", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/Couple;set(ZLjava/lang/Object;)V"), cancellable = true)
    private void snr$hideInvisibleCoupler(Vec3 entityPos, float entityXRot, float entityYRot, int bogeySpacing,
                                          float partialTicks, boolean leading, CallbackInfo ci) {
        if (type instanceof InvisibleBogeyBlock) {
            couplingAnchors.set(leading, null);
            ci.cancel();
        }
    }*/
}
