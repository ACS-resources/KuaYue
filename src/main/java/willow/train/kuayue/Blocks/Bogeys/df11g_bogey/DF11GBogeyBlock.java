package willow.train.kuayue.Blocks.Bogeys.df11g_bogey;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.schematics.requirement.ISpecialBlockItemRequirement;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.init.KYBogeyStyles;
import willow.train.kuayue.init.KYCreateEntities;
import willow.train.kuayue.init.KYTrackMaterials;

import java.util.List;

public class DF11GBogeyBlock extends AbstractBogeyBlock<DF11GBogeyBlockEntity> implements
        IBE<DF11GBogeyBlockEntity>, ProperWaterloggedBlock, ISpecialBlockItemRequirement {

    public DF11GBogeyBlock(BlockBehaviour.Properties props, BogeySizes.BogeySize size){

        super(props, size);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public TrackMaterial.TrackType getTrackType(BogeyStyle style) {

        return KYTrackMaterials.KYTrackType.STANDARD;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public double getWheelPointSpacing() {
        return 2.5d;
    }

    @Override
    public double getWheelRadius() {
        return 0.915;
    }

    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 7 / 32f, 1);
    }

    @Override
    public boolean allowsSingleBogeyCarriage() {
        return true;
    }

    @Override
    public BogeyStyle getDefaultStyle() {
        return KYBogeyStyles.DF11GBOGEY;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return AllBlocks.RAILWAY_CASING.asStack();
    }

    @Override
    public Class<DF11GBogeyBlockEntity> getBlockEntityClass() {
        return DF11GBogeyBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends DF11GBogeyBlockEntity> getBlockEntityType() {
        return KYCreateEntities.DF11G_BOGEY.get();
    }

    @Override
    public BlockState getRotatedBlockState(BlockState state, Direction targetedFace) {
        return state;
    }

    private final List<Property<?>> properties_to_copy = ImmutableList.<Property<?>>builder()
            .addAll(super.propertiesToCopy())
            .build();

    @Override
    public List<Property<?>> propertiesToCopy() {
        return properties_to_copy;
    }
}