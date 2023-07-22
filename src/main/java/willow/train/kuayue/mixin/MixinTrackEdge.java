package willow.train.kuayue.mixin;

import com.simibubi.create.content.trains.graph.TrackEdge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import willow.train.kuayue.Main;
import willow.train.kuayue.mixin_interfaces.ISwitchDisabledEdge;

@Mixin(value = TrackEdge.class, remap = false)
public class MixinTrackEdge {

    @Inject(method = "canTravelTo", at = @At("HEAD"), cancellable = true)
    private void travelThroughSwitches(TrackEdge other, CallbackInfoReturnable<Boolean> cir) {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        if (className.equals("com.simibubi.create.content.trains.signal.SignalPropagator"))
            return;
        if (Main.temporarilySkipSwitches)
            return;
        TrackEdge relevantEdge = Main.trackEdgeTemporarilyFlipped ? ((TrackEdge) (Object) this) : other;
        Main.trackEdgeTemporarilyFlipped = false;
        // trains should be able to navigate through automatic switches
        if (className.equals("com.simibubi.create.content.trains.entity.Navigation")
                && ISwitchDisabledEdge.isAutomatic(relevantEdge))
            return;
        if (Main.trackEdgeCarriageTravelling) { // don't switch, just allow through. Actual switching is handled by MixinTravellingPoint
            if (ISwitchDisabledEdge.isAutomatic(relevantEdge) && ISwitchDisabledEdge.isDisabled(relevantEdge)) {
//                ISwitchDisabledEdge.automaticallySelect(relevantEdge);
                return;
            }
        }
        if (ISwitchDisabledEdge.isDisabled(relevantEdge))// || ISwitchDisabledEdge.isDisabled((TrackEdge) (Object) this))
            cir.setReturnValue(false);
    }
}
