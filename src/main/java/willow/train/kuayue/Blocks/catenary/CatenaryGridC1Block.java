package willow.train.kuayue.Blocks.catenary;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import willow.train.kuayue.Catenary.CatenaryBlockInterface;
import willow.train.kuayue.Entity.CatenaryBaseEntity;
import willow.train.kuayue.Entity.SmallCatenaryBaseEntity;
import willow.train.kuayue.Main;
import willow.train.kuayue.Util.HorizontalBlockBase;
import willow.train.kuayue.init.ItemInit;

import static willow.train.kuayue.Catenary.Connections.DISCARDAREAWIDTH;
import static willow.train.kuayue.Main.CATENARYCONNECTIONHANDLER;

public class CatenaryGridC1Block extends HorizontalBlockBase implements CatenaryBlockInterface {

    public CatenaryGridC1Block(Properties p_49795_) {
        super(p_49795_);
    }

    protected static final VoxelShape SOUTH_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    protected static final VoxelShape WEST_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    protected static final VoxelShape NORTH_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    protected static final VoxelShape EAST_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));


    public VoxelShape getShape(BlockState p_54372_, BlockGetter p_54373_, BlockPos p_54374_, CollisionContext p_54375_) {
        switch ((Direction) p_54372_.getValue(FACING)) {
            case NORTH:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case WEST:
                return WEST_AABB;
            case EAST:
            default:
                return EAST_AABB;
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.isClientSide) {

            return InteractionResult.SUCCESS;

        } else {
            if(pPlayer.getMainHandItem().is(ItemInit.GeneralCatenry.get())) {
                Vec3 pos = getCatenaryPort(pPos, pState, pPlayer.getDirection());
                CATENARYCONNECTIONHANDLER.registerPos(pPlayer, pLevel, pos, "catenary", pPos);
                if (CATENARYCONNECTIONHANDLER.canConnect(pPlayer)) CATENARYCONNECTIONHANDLER.connect(pPlayer);
            }else if(pPlayer.getMainHandItem().is(ItemInit.CatenaryScissors.get())) {
                discardCatenary(pLevel , pPos , DISCARDAREAWIDTH);
                pPlayer.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".catenary_removed") , true);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public Vec3[] getCatenaryPorts() {
        return new Vec3[0];
    }

    @Override
    public void discardCatenary(Level level, BlockPos pos , double areaWidth) {
        Vec3 PositionStart = new Vec3(pos.getX() - areaWidth , pos.getY() - areaWidth , pos.getZ() - areaWidth);
        Vec3 PositionEnd = new Vec3(pos.getX() + areaWidth , pos.getY() + areaWidth , pos.getZ() + areaWidth);
        CatenaryBaseEntity[] entities =  level.getEntitiesOfClass(CatenaryBaseEntity.class , new AABB(PositionStart , PositionEnd)).toArray(new CatenaryBaseEntity[0]);
        SmallCatenaryBaseEntity[] hangerEntities =  level.getEntitiesOfClass(SmallCatenaryBaseEntity.class , new AABB(PositionStart , PositionEnd)).toArray(new SmallCatenaryBaseEntity[0]);
        for(CatenaryBaseEntity cbe : entities){
            CompoundTag tag = new CompoundTag();
            cbe.addAdditionalSaveData(tag);
            if((tag.get("blockA").getAsString()).equals(pos.getX() + "," + pos.getY() + "," + pos.getZ()) ||
                    (tag.get("blockB").getAsString()).equals(pos.getX() + "," + pos.getY() + "," + pos.getZ()))
            {
                cbe.discard();
            }
        }
        for(SmallCatenaryBaseEntity cbe : hangerEntities){
            CompoundTag tag = new CompoundTag();
            cbe.addAdditionalSaveData(tag);
            if((tag.get("blockA").getAsString()).equals(pos.getX() + "," + pos.getY() + "," + pos.getZ()) ||
                    (tag.get("blockB").getAsString()).equals(pos.getX() + "," + pos.getY() + "," + pos.getZ()))
            {
                cbe.discard();
            }
        }
    }

    @Override
    public Vec3 getCatenaryPort(BlockPos pPos, BlockState pState, Direction direction) {
        String d = direction.getAxisDirection().getName();
        double offset = 0.35;
        switch ((Direction) pState.getValue(FACING)) {
            case NORTH:
                switch (d){
                    case "Towards positive":
                        return new Vec3(((double)pPos.getX())+0.5 - offset, pPos.getY() , (double)(pPos.getZ())+1.5 - 0.15);
                    case "Towards negative":
                    default:
                        return new Vec3(((double)pPos.getX())+0.5 + offset , pPos.getY() , (double)(pPos.getZ())+1.5 + 0.15);
                }
            case SOUTH:
                switch (d){
                    case "Towards positive":
                        return new Vec3(((double)pPos.getX())+0.5 - offset , pPos.getY() , (double)(pPos.getZ())-0.5 - 0.15);
                    case "Towards negative":
                    default:
                        return new Vec3(((double)pPos.getX())+0.5 + offset , pPos.getY() , (double)(pPos.getZ())-0.5 + 0.15);
                }
            case WEST:
                switch (d){
                    case "Towards positive":
                        return new Vec3(((double)pPos.getX()) + 1.5 + 0.15 , pPos.getY() , (double) pPos.getZ() + 0.5 - offset);
                    case "Towards negative":
                    default:
                        return new Vec3(((double)pPos.getX()) + 1.5 - 0.15 , pPos.getY() , (double) pPos.getZ() + 0.5 + offset);
                }
            case EAST:
            default:
                switch (d){
                    case "Towards positive":
                        return new Vec3(((double)pPos.getX()) - 0.5 + 0.15 , pPos.getY() , (double) pPos.getZ() + 0.5 - offset);
                    case "Towards negative":
                    default:
                        return new Vec3(((double)pPos.getX()) - 0.5 - 0.15 , pPos.getY() , (double) pPos.getZ() + 0.5 + offset);
                }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(!level.isClientSide()){
            discardCatenary(level , pos , DISCARDAREAWIDTH);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        if(!level.isClientSide()){
            discardCatenary(level , pos , DISCARDAREAWIDTH);
        }
        super.onBlockExploded(state, level, pos, explosion);
    }
}
