package willow.train.kuayue.BlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import willow.train.kuayue.init.BlockEntitiesInit;


/**
 * Entity 和 Block 的连接在 init/BlockEntitiesInit 里 (注册的时候就写明)
 * Entity 和 Renderer 的连接在 Main 下
 * 如 BlockEntityRenderers.register(BlockEntitiesInit.CARRIAGE_NO_SIGN.get(), CarriageNoSignRenderer::new);
 * 这个 Entity 是根据 Block.newBlockEntity() 来调用的，记得在对应的 Block 里写明这个方法
 * 这个 Entity 对应的 Block 是 TrainNoBlock ，在 Blocks/Signs 包内
 */
public class CarriageNoSignEntity extends BlockEntity {
    public CarriageNoSignEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesInit.CARRIAGE_NO_SIGN.get(), pPos, pBlockState);
    }
}
