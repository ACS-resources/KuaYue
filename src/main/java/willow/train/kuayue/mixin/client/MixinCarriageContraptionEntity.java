package willow.train.kuayue.mixin.client;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.trains.entity.*;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import willow.train.kuayue.mixin.AccessorCarriageBogey;

@Mixin(CarriageContraptionEntity.class)
public class MixinCarriageContraptionEntity {

    private static int soundTick = 0;
    @Redirect(method = "tickContraption", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/entity/CarriageParticles;tick(Lcom/simibubi/create/content/trains/entity/Carriage$DimensionalCarriageEntity;)V"), remap = false)
    public void tick(CarriageParticles instance, Carriage.DimensionalCarriageEntity dce){
        Minecraft mc = Minecraft.getInstance();
        CarriageContraptionEntity entity = ((AccessorCarriageParticles) instance).getEntity();

        boolean arrived = ((AccessorCarriageParticles) instance).isArrived();
        int depressurise = ((AccessorCarriageParticles) instance).getDepressurise();
        double prevMotion = ((AccessorCarriageParticles) instance).getPrevMotion();
        LerpedFloat brakes = ((AccessorCarriageParticles) instance).getBrakes();

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

            LerpedFloat yaw = ((AccessorCarriageBogey) bogey).getYaw();
            LerpedFloat pitch = ((AccessorCarriageBogey) bogey).getPitch();
            boolean isLeading = ((AccessorCarriageBogey) bogey).getIsLeading();

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

                    m = VecHelper.rotate(m, pitch.getValue(0), Direction.Axis.X);
                    m = VecHelper.rotate(m, yaw.getValue(0), Direction.Axis.Y);

                    v = VecHelper.rotate(v, pitch.getValue(0), Direction.Axis.X);
                    v = VecHelper.rotate(v, yaw.getValue(0), Direction.Axis.Y);

                    v = VecHelper.rotate(v, -viewYRot - 90, Direction.Axis.Y);
                    v = VecHelper.rotate(v, viewXRot, Direction.Axis.X);
                    v = VecHelper.rotate(v, -180, Direction.Axis.Y);

                    v = v.add(0, 0, isLeading ? 0 : -bogeySpacing);
                    v = VecHelper.rotate(v, 180, Direction.Axis.Y);
                    v = VecHelper.rotate(v, -viewXRot, Direction.Axis.X);
                    v = VecHelper.rotate(v, viewYRot + 90, Direction.Axis.Y);
                    v = v.add(position);

                    m = m.add(contraptionMotion.scale(.75f));

                    if(spark && bogey.getStyle().contactParticle != null) {
                        level.addParticle(bogey.getStyle().contactParticle, v.x, v.y, v.z, m.x, m.y, m.z);
                    }else if(!spark && bogey.getStyle().smokeParticle != null) {
                        level.addParticle(bogey.getStyle().smokeParticle, v.x, v.y, v.z, m.x, m.y, m.z);
                    }
                }
            }
        }
    }

    /*
    @Redirect(method = "tickContraption", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/simibubi/create/content/trains/entity/CarriageSounds;tick(Lcom/simibubi/create/content/trains/entity/Carriage$DimensionalCarriageEntity;)V"), remap = false)
    public void tick(CarriageSounds instance, Carriage.DimensionalCarriageEntity dce) {
        Minecraft mc = Minecraft.getInstance();
        Entity camEntity = mc.cameraEntity;

        int tick = ((AccessorCarriageSounds) instance).getTick();
        CarriageContraptionEntity entity = ((AccessorCarriageSounds) instance).getEntity();
        boolean arrived = ((AccessorCarriageSounds) instance).isArrived();
        SoundEvent closestBogeySound = ((AccessorCarriageSounds) instance).getClosestBogeySound();
        LerpedFloat distanceFactor = ((AccessorCarriageSounds) instance).getDistanceFactor();
        LerpedFloat speedFactor = ((AccessorCarriageSounds) instance).getSpeedFactor();
        LerpedFloat approachFactor = ((AccessorCarriageSounds) instance).getApproachFactor();
        LerpedFloat seatCrossfade = ((AccessorCarriageSounds) instance).getSeatCrossfade();

        if (camEntity == null)
            return;

        Vec3 leadingAnchor = dce.leadingAnchor();
        Vec3 trailingAnchor = dce.trailingAnchor();
        if (leadingAnchor == null || trailingAnchor == null)
            return;

        tick++;
        soundTick++;
        if (soundTick > 100) {soundTick = 0;}

        Vec3 cam = camEntity.getEyePosition();
        Vec3 contraptionMotion = entity.position()
                .subtract(entity.getPrevPositionVec());
        Vec3 combinedMotion = contraptionMotion.subtract(camEntity.getDeltaMovement());

        Train train = entity.getCarriage().train;

        if (arrived && contraptionMotion.length() > 0.01f)
            arrived = false;
        if (arrived && entity.carriageIndex == 0)
            train.accumulatedSteamRelease /= 2;

        arrived |= entity.isStalled();

        if (entity.carriageIndex == 0)
            train.accumulatedSteamRelease = (float) Math
                    .min(train.accumulatedSteamRelease + Math.min(0.5f, Math.abs(contraptionMotion.length() / 10f)), 10);

        Vec3 toBogey1 = leadingAnchor.subtract(cam);
        Vec3 toBogey2 = trailingAnchor.subtract(cam);
        double distance1 = toBogey1.length();
        double distance2 = toBogey2.length();

        Couple<CarriageBogey> bogeys = entity.getCarriage().bogeys;
        CarriageBogey relevantBogey = bogeys.get(distance1 > distance2);
        if (relevantBogey == null) {
            relevantBogey = bogeys.getFirst();
        }
        if (relevantBogey != null) {
            closestBogeySound = relevantBogey.getStyle().getSoundType();
        }

        Vec3 toCarriage = distance1 > distance2 ? toBogey2 : toBogey1;
        double distance = Math.min(distance1, distance2);
        Vec3 soundLocation = cam.add(toCarriage);

        double dot = toCarriage.normalize()
                .dot(combinedMotion.normalize());

        speedFactor.chase(contraptionMotion.length(), .25f, LerpedFloat.Chaser.exp(.05f));
        distanceFactor.chase(Mth.clampedLerp(100, 0, (distance - 3) / 64d), .25f, LerpedFloat.Chaser.exp(50f));
        approachFactor.chase(Mth.clampedLerp(50, 200, .5f * (dot + 1)), .25f, LerpedFloat.Chaser.exp(10f));
        seatCrossfade.chase(camEntity.getVehicle() instanceof CarriageContraptionEntity ? 1 : 0, .1f, LerpedFloat.Chaser.EXP);

        speedFactor.tickChaser();
        distanceFactor.tickChaser();
        approachFactor.tickChaser();
        seatCrossfade.tickChaser();

        //minecartEsqueSound = playIfMissing(mc, minecartEsqueSound, AllSoundEvents.TRAIN.getMainEvent());
        //sharedWheelSound = playIfMissing(mc, sharedWheelSound, closestBogeySound);
        //sharedWheelSoundSeated = playIfMissing(mc, sharedWheelSoundSeated, AllSoundEvents.TRAIN3.getMainEvent());

        float volume = Math.min(Math.min(speedFactor.getValue(), distanceFactor.getValue() / 100),
                approachFactor.getValue() / 300 + .0125f);

        if (entity.carriageIndex == 0) {
            float v = volume * (1 - seatCrossfade.getValue() * .35f) * .75f;
            if ((5 + soundTick) % 25 == 0) {
                AllSoundEvents.TRAIN2.playAt(entity.level, soundLocation, volume, 1.17f, false);
                //AllSoundEvents.STEAM.playAt(entity.level, soundLocation, v * ((tick + 7) % 8 == 0 ? 0.75f : .45f),
                //1.17f, false);
            }
            //if (soundTick % 40 == 0) {
                //AllSoundEvents.TRAIN2.playAt(entity.level, soundLocation, volume, .8f, false);
                //AllSoundEvents.STEAM.playAt(entity.level, soundLocation, v * 1.5f, .8f, false);
            //}
        }

        if (!arrived && speedFactor.getValue() < .002f && train.accumulatedSteamRelease > 1) {
            arrived = true;
            float releaseVolume = train.accumulatedSteamRelease / 10f;
            //entity.level.playLocalSound(soundLocation.x, soundLocation.y, soundLocation.z, SoundEvents.LAVA_EXTINGUISH,
                    //SoundSource.NEUTRAL, .25f * releaseVolume, .78f, false);
            entity.level.playLocalSound(soundLocation.x, soundLocation.y, soundLocation.z,
                    SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundSource.NEUTRAL, .2f * releaseVolume, 1.5f, false);
            //AllSoundEvents.STEAM.playAt(entity.level, soundLocation, .75f * releaseVolume, .5f, false);
        }

        float pitchModifier = ((entity.getId() * 10) % 13) / 36f;

        volume = Math.min(volume, distanceFactor.getValue() / 800);

        float pitch = Mth.clamp(speedFactor.getValue() * 2 + .25f, .75f, 1.95f) - pitchModifier;
//		float pitch2 = Mth.clamp(speedFactor.getValue() * 2, 0.75f, 1.25f) - pitchModifier;

        //minecartEsqueSound.setPitch(pitch * 1.5f);

        volume = Math.min(volume, distanceFactor.getValue() / 1000);

        for (Carriage carriage : train.carriages) {
            Carriage.DimensionalCarriageEntity mainDCE = carriage.getDimensionalIfPresent(entity.level.dimension());
            if (mainDCE == null)
                continue;
            CarriageContraptionEntity mainEntity = mainDCE.entity.get();
            if (mainEntity == null)
                continue;
            if (mainEntity.sounds == null)
                mainEntity.sounds = new CarriageSounds(mainEntity);
            finalizeSharedVolume(volume, seatCrossfade, speedFactor);
            if (carriage != entity.getCarriage()) {
                volume = finalizeSharedVolume(0, seatCrossfade, speedFactor);
                return;
            }
            break;
        }

//		finalizeSharedVolume(volume);
//		minecartEsqueSound.setLocation(soundLocation);
//		sharedWheelSound.setPitch(pitch2);
//		sharedWheelSound.setLocation(soundLocation);
//		sharedWheelSoundSeated.setPitch(pitch2);
//		sharedWheelSoundSeated.setLocation(soundLocation);

        if (train.honkTicks == 0) {
            return;
        }

        train.honkTicks--;
        train.determineHonk(entity.level);

        if (train.lowHonk == null)
            return;

        boolean low = train.lowHonk;
        float honkPitch = (float) Math.pow(2, train.honkPitch / 12.0);

        AllSoundEvents.SoundEntry endSound =
                !low ? AllSoundEvents.WHISTLE_TRAIN_MANUAL_END : AllSoundEvents.WHISTLE_TRAIN_MANUAL_LOW_END;
        AllSoundEvents.SoundEntry continuousSound =
                !low ? AllSoundEvents.WHISTLE_TRAIN_MANUAL : AllSoundEvents.WHISTLE_TRAIN_MANUAL_LOW;

        if (train.honkTicks == 5)
            endSound.playAt(mc.level, soundLocation, 1, honkPitch, false);
        if (train.honkTicks == 19)
            endSound.playAt(mc.level, soundLocation, .5f, honkPitch, false);

        //sharedHonkSound = playIfMissing(mc, sharedHonkSound, continuousSound.getMainEvent());
        //sharedHonkSound.setLocation(soundLocation);
        float fadeout = Mth.clamp((3 - train.honkTicks) / 3f, 0, 1);
        float fadein = Mth.clamp((train.honkTicks - 17) / 3f, 0, 1);
        //sharedHonkSound.setVolume(1 - fadeout - fadein);
        //sharedHonkSound.setPitch(honkPitch);

    }

    public float finalizeSharedVolume(float volume, LerpedFloat seatCrossfade, LerpedFloat speedFactor) {

        float crossfade = seatCrossfade.getValue();
        //minecartEsqueSound.setVolume((1 - crossfade * .65f) * volume / 2);
        return Math.min(volume, Math.max((speedFactor.getValue() - .25f) / 4 + 0.01f, 0));
        //sharedWheelSoundSeated.setVolume(volume * crossfade);
        //sharedWheelSound.setVolume(volume * (1 - crossfade) * 1.5f);
    }
     */
}
