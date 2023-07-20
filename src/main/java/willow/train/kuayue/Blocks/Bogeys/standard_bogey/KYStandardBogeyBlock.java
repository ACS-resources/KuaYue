package willow.train.kuayue.Blocks.Bogeys.standard_bogey;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.schematics.requirement.ISpecialBlockItemRequirement;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.content.trains.bogey.StandardBogeyBlockEntity;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.init.KYCreateEntities;
import willow.train.kuayue.init.KYTrackMaterials;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KYStandardBogeyBlock extends AbstractBogeyBlock<KYStandardBogeyBlockEntity> implements
        IBE<KYStandardBogeyBlockEntity>, ProperWaterloggedBlock, ISpecialBlockItemRequirement {

    public KYStandardBogeyBlock(Properties props){

        super(props, BogeySizes.SMALL);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public TrackMaterial.TrackType getTrackType(BogeyStyle style) {

        return KYTrackMaterials.KYTrackType.STANDARD;
    }

    @Override
    public double getWheelPointSpacing() {
        return 2;
    }

    @Override
    public double getWheelRadius() {
        return (size == BogeySizes.LARGE ? 12.5 : 6.5) / 16d;
    }

    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 7 / 32f, 1);
    }

    @Override
    public BogeyStyle getDefaultStyle() {
        return AllBogeyStyles.STANDARD;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return AllBlocks.RAILWAY_CASING.asStack();
    }

    @Override
    public Class<KYStandardBogeyBlockEntity> getBlockEntityClass() {
        return KYStandardBogeyBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends KYStandardBogeyBlockEntity> getBlockEntityType() {
        return KYCreateEntities.KY_STANDARD_BOGEY.get();
    }

}
