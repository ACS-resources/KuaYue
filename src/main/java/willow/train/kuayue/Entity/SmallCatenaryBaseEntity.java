package willow.train.kuayue.Entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SmallCatenaryBaseEntity extends AbstractArrow {

    String blockPosA = null , blockPosB = null;

    public SmallCatenaryBaseEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SmallCatenaryBaseEntity(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public SmallCatenaryBaseEntity(EntityType<? extends AbstractArrow> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitEntity(EntityHitResult result){
        //super.onHitEntity(result);
        //this.level.explode(this , this.getX() , this.getY() , this.getZ() , 4.0f , true , Explosion.BlockInteraction.BREAK);
    }

    @Override
    protected void onHitBlock(BlockHitResult ray){
        //super.onHitBlock(ray);
        BlockState hit = this.level.getBlockState(ray.getBlockPos());
    }

    @Override
    protected void tickDespawn() {
        if (this.inGroundTime > 30){
            this.discard();
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isNoPhysics(){
        return true;
    }

    @Override
    public void tick(){}

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("blockA",blockPosA);
        pCompound.putString("blockB",blockPosB);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.blockPosA = pCompound.getString("blockA");
        this.blockPosB = pCompound.getString("blockB");
        super.readAdditionalSaveData(pCompound);
    }
}
