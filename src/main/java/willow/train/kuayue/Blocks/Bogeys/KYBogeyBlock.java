package willow.train.kuayue.Blocks.Bogeys;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.schematics.requirement.ISpecialBlockItemRequirement;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.init.KYCreateEntities;

public class KYBogeyBlock extends AbstractBogeyBlock<KYBogeyBlockEntity>
        implements IBE<KYBogeyBlockEntity>, ProperWaterloggedBlock, ISpecialBlockItemRequirement {

    private final BogeyStyle defaultStyle;

    protected KYBogeyBlock(BlockBehaviour.Properties props, BogeyStyle defaultStyle, BogeySizes.BogeySize size) {
        super(props, size);
        this.defaultStyle = defaultStyle;
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public TrackMaterial.TrackType getTrackType(BogeyStyle style) {
        return TrackMaterial.TrackType.STANDARD;
    }

    @Override
    public double getWheelPointSpacing() {
        return 2.4d;
    }

    @Override
    public double getWheelRadius() {
        return 0.915d;//6.5 / 16d;
    }

    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 7 / 32f, 1);
    }

    @Override
    public BogeyStyle getDefaultStyle() {
        return defaultStyle;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return AllBlocks.RAILWAY_CASING.asStack();
    }

    @Override
    public Class<KYBogeyBlockEntity> getBlockEntityClass() {
        return KYBogeyBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends KYBogeyBlockEntity> getBlockEntityType() {
        return KYCreateEntities.BOGEY.get();
    }
}
