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
import net.minecraft.world.level.block.state.StateDefinition;
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
import willow.train.kuayue.Util.DirectionalBlockBase;
import willow.train.kuayue.init.ItemInit;

import static willow.train.kuayue.Catenary.Connections.DISCARDAREAWIDTH;
import static willow.train.kuayue.Main.CATENARYCONNECTIONHANDLER;

public class CableHangPointBlock extends DirectionalBlockBase implements CatenaryBlockInterface {

    Vec3[] CATENARYPORTS = new Vec3[]{};
    public CableHangPointBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54370_) {
        super.createBlockStateDefinition(p_54370_);
    }

    protected static final VoxelShape SOUTH_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    protected static final VoxelShape WEST_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    protected static final VoxelShape NORTH_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    protected static final VoxelShape EAST_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    protected static final VoxelShape UP_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    protected static final VoxelShape DOWN_AABB = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));


    public VoxelShape getShape(BlockState p_54372_, BlockGetter p_54373_, BlockPos p_54374_, CollisionContext p_54375_) {
        switch ( p_54372_.getValue(FACING)) {
            case UP:
                return UP_AABB;
            case DOWN:
                return DOWN_AABB;
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
            if(pPlayer.getMainHandItem().is(ItemInit.GeneralStraight.get())) {
                Vec3 pos = getCatenaryPort(pPos, pState, pPlayer.getDirection());
                CATENARYCONNECTIONHANDLER.registerPos(pPlayer, pLevel, pos, "straight", pPos);
                if (CATENARYCONNECTIONHANDLER.canConnect(pPlayer)) CATENARYCONNECTIONHANDLER.connect(pPlayer);
            }else if(pPlayer.getMainHandItem().is(ItemInit.CatenaryScissors.get())) {
                discardCatenary(pLevel , pPos , DISCARDAREAWIDTH);
                pPlayer.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".catenary_removed") , true);
            } else if(pPlayer.getMainHandItem().is(ItemInit.GeneralHyperbolic.get())) {
                Vec3 pos = getCatenaryPort(pPos, pState, pPlayer.getDirection());
                CATENARYCONNECTIONHANDLER.registerPos(pPlayer, pLevel, pos, "hyperbolic", pPos);
                if (CATENARYCONNECTIONHANDLER.canConnect(pPlayer)) CATENARYCONNECTIONHANDLER.connect(pPlayer);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public Vec3[] getCatenaryPorts() {
        return CATENARYPORTS;
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
    public Vec3 getCatenaryPort(BlockPos pPos,BlockState pState, Direction direction) {
        switch ((Direction) pState.getValue(FACING)) {
            case UP:
                return new Vec3(((double)pPos.getX()) + 0.5 , pPos.getY() + 0.85 , (double)(pPos.getZ()) + 0.5);
            case DOWN:
                return new Vec3(((double)pPos.getX()) + 0.5 , pPos.getY() + 0.15 , (double)(pPos.getZ()) + 0.5);
            case NORTH:
                return new Vec3(((double)pPos.getX()) + 0.5 , pPos.getY() + 0.5, (double)(pPos.getZ()) + 0.15);
            case SOUTH:
                return new Vec3((double)pPos.getX() + 0.5 , pPos.getY() + 0.5 ,  ((double)pPos.getZ()) + 0.85);
            case WEST:
                return new Vec3(((double)pPos.getX()) + 0.15 , pPos.getY() + 0.5 , (double) pPos.getZ() + 0.5);
            case EAST:
            default:
                return new Vec3(((double)pPos.getX()) + 0.85  , pPos.getY() + 0.5 , (double) pPos.getZ() + 0.5);
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
