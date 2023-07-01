package willow.train.kuayue.Blocks.Signs;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.Client.CarriageTypeSignEditMenu;
import willow.train.kuayue.Entity.CarriageTypeSignEntity;
import willow.train.kuayue.Screen.CarriageTypeSignEditScreen;
import willow.train.kuayue.init.ItemInit;
import willow.train.kuayue.init.MenuInit;

import java.util.Random;
import java.util.function.Consumer;

public class CarriageTypeSignBlock extends KuayueSignBlock{

    public String[] data = new String[]{"", "", "", "", ""};
    CarriageTypeSignEntity ctse;

    public CarriageTypeSignBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        ctse = new CarriageTypeSignEntity(pPos, pState);
        boolean flag = false;
        for(String s : data){
            if (!s.equals("")){
                flag = true;
                break;
            }
        }
        if(flag){
            ctse.setMessages(data);
        }
        return ctse;
    }

    public boolean isMirrored(BlockState pState) {
        return pState.getValue(MIRROR);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getMainHandItem().is(ItemInit.CA_25T.get())){
            if(!pLevel.isClientSide){
                NetworkHooks.openGui((ServerPlayer) pPlayer, (CarriageTypeSignEntity) pLevel.getBlockEntity(pPos), pPos);
            }
            return InteractionResult.SUCCESS;
        }
        pState = pState.cycle(MIRROR);
        pLevel.setBlock(pPos, pState, 10);
        pLevel.gameEvent(pPlayer, this.isMirrored(pState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    public boolean isPossibleToRespawnInThis() {
        return true;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        this.data = ctse.getData();
    }
}
