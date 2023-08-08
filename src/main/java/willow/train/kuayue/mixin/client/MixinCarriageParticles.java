package willow.train.kuayue.mixin.client;

import com.simibubi.create.content.trains.entity.CarriageParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CarriageParticles.class)
public class MixinCarriageParticles {
    
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"), remap = false)
    public void doAddArticle(Level instance, ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){
        if(pParticleData != null){
            instance.addParticle(pParticleData, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        }
    }
}
