package willow.train.kuayue.mixin.client;

import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageParticles;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CarriageParticles.class)
public interface AccessorCarriageParticles {
    @Accessor(remap = false)
    CarriageContraptionEntity getEntity();

    @Accessor(remap = false)
    boolean isArrived();

    @Accessor(remap = false)
    int getDepressurise();

    @Accessor(remap = false)
    double getPrevMotion();

    @Accessor(remap = false)
    LerpedFloat getBrakes();
}
