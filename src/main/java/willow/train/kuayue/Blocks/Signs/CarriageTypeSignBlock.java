package willow.train.kuayue.Blocks.Signs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.Entity.CarriageTypeSignEntity;

public class CarriageTypeSignBlock extends KuayueSignBlock{

    public CarriageTypeSignBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CarriageTypeSignEntity(pPos, pState);
    }
}
