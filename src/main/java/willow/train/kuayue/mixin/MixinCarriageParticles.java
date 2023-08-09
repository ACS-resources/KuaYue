package willow.train.kuayue.mixin;

import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.trains.bogey.BogeyInstance;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageParticles;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CarriageParticles.class)
public class MixinCarriageParticles {

    @Shadow(remap = false)
    CarriageContraptionEntity entity;

    @Shadow(remap = false)
    boolean arrived;
    @Shadow(remap = false)
    int depressurise;
    @Shadow(remap = false)
    double prevMotion;
    @Shadow(remap = false)
    LerpedFloat brakes;
    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void tick(Carriage.DimensionalCarriageEntity dce){
        Minecraft mc = Minecraft.getInstance();
        Entity camEntity = mc.cameraEntity;
        if (camEntity == null)
            return;
        Vec3 leadingAnchor = dce.leadingAnchor();
        if (leadingAnchor == null || !leadingAnchor.closerThan(camEntity.position(), 64))
            return;

        RandomSource r = entity.level.random;
        Vec3 contraptionMotion = entity.position()
                .subtract(entity.getPrevPositionVec());
        double length = contraptionMotion.length();
        if (arrived && length > 0.01f)
            arrived = false;
        arrived |= entity.isStalled();

        boolean stopped = length < .002f;
        if (stopped) {
            if (!arrived) {
                arrived = true;
                depressurise = (int) (20 * entity.getCarriage().train.accumulatedSteamRelease / 10f);
            }
        } else
            depressurise = 0;

        if (depressurise > 0)
            depressurise--;

        brakes.chase(prevMotion > length + length / 512f ? 1 : 0, .25f, LerpedFloat.Chaser.exp(.625f));
        brakes.tickChaser();
        prevMotion = length;

        Level level = entity.level;
        Vec3 position = entity.getPosition(0);
        float viewYRot = entity.getViewYRot(0);
        float viewXRot = entity.getViewXRot(0);
        int bogeySpacing = entity.getCarriage().bogeySpacing;

        for (CarriageBogey bogey : entity.getCarriage().bogeys) {
            if (bogey == null)
                continue;

            boolean spark = depressurise == 0 || depressurise > 10;

            float cutoff = length < 1 / 8f ? 0 : 1 / 8f;

            if (length > 1 / 6f)
                cutoff = Math.max(cutoff, brakes.getValue() * 1.15f);

            for (int j : Iterate.positiveAndNegative) {
                if (r.nextFloat() > cutoff && (spark || r.nextInt(4) == 0))
                    continue;
                for (int i : Iterate.positiveAndNegative) {
                    if (r.nextFloat() > cutoff && (spark || r.nextInt(4) == 0))
                        continue;

                    Vec3 v = Vec3.ZERO.add(j * 1.15, spark ? -.6f : .32, i);
                    Vec3 m = Vec3.ZERO.add(j * (spark ? .5 : .25), spark ? .49 : -.29, 0);

                    m = VecHelper.rotate(m, ((AccessorCarriageBogey) bogey).getPitch().getValue(0), Direction.Axis.X);
                    m = VecHelper.rotate(m, ((AccessorCarriageBogey) bogey).getYaw().getValue(0), Direction.Axis.Y);

                    v = VecHelper.rotate(v, ((AccessorCarriageBogey) bogey).getPitch().getValue(0), Direction.Axis.X);
                    v = VecHelper.rotate(v, ((AccessorCarriageBogey) bogey).getYaw().getValue(0), Direction.Axis.Y);

                    v = VecHelper.rotate(v, -viewYRot - 90, Direction.Axis.Y);
                    v = VecHelper.rotate(v, viewXRot, Direction.Axis.X);
                    v = VecHelper.rotate(v, -180, Direction.Axis.Y);

                    v = v.add(0, 0, ((AccessorCarriageBogey) bogey).getIsLeading() ? 0 : -bogeySpacing);
                    v = VecHelper.rotate(v, 180, Direction.Axis.Y);
                    v = VecHelper.rotate(v, -viewXRot, Direction.Axis.X);
                    v = VecHelper.rotate(v, viewYRot + 90, Direction.Axis.Y);
                    v = v.add(position);

                    m = m.add(contraptionMotion.scale(.75f));

                    if(bogey.getStyle() == AllBogeyStyles.STANDARD) {
                        level.addParticle(spark ? bogey.getStyle().contactParticle : bogey.getStyle().smokeParticle, v.x, v.y, v.z, m.x, m.y, m.z);
                    } else {
                        if(spark) {
                            level.addParticle(bogey.getStyle().contactParticle, v.x, v.y, v.z, m.x, m.y, m.z);
                        }
                    }
                }
            }
        }
    }
}
