package willow.train.kuayue.mixin;

import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CarriageBogey.class, remap = false)
public interface AccessorCarriageBogey {

    @Accessor
    AbstractBogeyBlock<?> getType();

    @Accessor(remap = false)
    LerpedFloat getWheelAngle();
    @Accessor(remap = false)
    LerpedFloat getYaw();
    @Accessor(remap = false)
    LerpedFloat getPitch();
    @Accessor(remap = false)
    boolean getIsLeading();
}
