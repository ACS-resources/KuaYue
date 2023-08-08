package willow.train.kuayue.mixin;

import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackShape;
import com.simibubi.create.foundation.utility.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TrackBlock.class, remap = false)
public abstract class MixinTrackBlock extends Block {

    @Shadow
    public abstract void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource p_60465_);

    public MixinTrackBlock(Properties pProperties) {
        super(pProperties);
    }

/*    @Inject(method = "getBogeyAnchor", at = @At("HEAD"), cancellable = true)
    private void placeCustomStyle(BlockGetter world, BlockPos pos, BlockState state, CallbackInfoReturnable<BlockState> cir) {

        if (BogeyCategoryHandlerServer.currentPlayer == null)
            return;
        Pair<BogeyStyle, BogeySizes.BogeySize> styleData = BogeyCategoryHandlerServer.getStyle(BogeyCategoryHandlerServer.currentPlayer);
        BogeyStyle style = styleData.getFirst();
        BogeySizes.BogeySize selectedSize = styleData.getSecond();
        if (style == AllBogeyStyles.STANDARD)
            return;

        BogeySizes.BogeySize size = selectedSize != null ? selectedSize : BogeySizes.getAllSizesSmallToLarge().get(0);
        int escape = BogeySizes.getAllSizesSmallToLarge().size();
        while (!style.validSizes().contains(size)) {
            if (escape < 0)
                return;
            size = size.increment();
            escape--;
        }
        Block block = style.getBlockOfSize(size);
        cir.setReturnValue(
                block.defaultBlockState()
                        .setValue(BlockStateProperties.HORIZONTAL_AXIS,
                                state.getValue(TrackBlock.SHAPE) == TrackShape.XO ? Direction.Axis.X : Direction.Axis.Z)
        );
    }*/
}
