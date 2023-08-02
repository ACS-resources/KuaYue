package willow.train.kuayue.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlockEntity;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.content.trains.station.GlobalStation;
import com.simibubi.create.content.trains.station.StationBlockEntity;
import com.simibubi.create.content.trains.track.ITrackBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.Pair;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import willow.train.kuayue.Blocks.Bogeys.selection_menu.BogeyCategoryHandlerServer;
import willow.train.kuayue.base.Constants;
import willow.train.kuayue.base.data.BogeyPaintColour;
import willow.train.kuayue.init.KYTrackMaterials;

import javax.annotation.Nullable;

import static willow.train.kuayue.base.Constants.BOGEY_PAINT_KEY;

@Mixin(value = StationBlockEntity.class, remap = false)
public abstract class MixinStationBlockEntity extends SmartBlockEntity {

    @Shadow
    Direction assemblyDirection;

    @Shadow
    @Nullable
    public abstract GlobalStation getStation();

    private MixinStationBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "trackClicked", at = @At("HEAD"))
    private void storePlayer(Player player, InteractionHand hand, ITrackBlock track, BlockState state, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BogeyCategoryHandlerServer.currentPlayer = player.getUUID();
    }

    @Inject(method = "trackClicked", at = @At("RETURN"))
    private void clearPlayer(Player player, InteractionHand hand, ITrackBlock track, BlockState state, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BogeyCategoryHandlerServer.currentPlayer = null;
    }

    @Inject(
            method = "trackClicked",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
                    shift = At.Shift.AFTER
            )
    )
    public void addData(Player player, InteractionHand hand, ITrackBlock track, BlockState state, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level == null)
            return;

        BlockPos up = new BlockPos(track.getUpNormal(level, pos, state));
        BlockPos down = new BlockPos(track.getUpNormal(level, pos, state).scale(-1));

        boolean upsideDown = (player.getViewXRot(1.0F) < 0 && (track.getBogeyAnchor(level, pos, state)).getBlock() instanceof AbstractBogeyBlock<?> bogey && bogey.canBeUpsideDown());
        BlockPos targetPos = upsideDown ? pos.offset(down) : pos.offset(up);

        BlockEntity blockEntity = level.getBlockEntity(targetPos);

        if (!(blockEntity instanceof AbstractBogeyBlockEntity bogeyBlockEntity))
            return;

        ItemStack heldItem = player.getItemInHand(hand);
        BogeyPaintColour paintColour = BogeyPaintColour.UNPAINTED;

        CompoundTag bogeyData = bogeyBlockEntity.getBogeyData();

        NBTHelper.writeEnum(bogeyData, BOGEY_PAINT_KEY, paintColour);
        NBTHelper.writeEnum(bogeyData, Constants.BOGEY_ASSEMBLY_DIRECTION_KEY, assemblyDirection);

        bogeyBlockEntity.setBogeyData(bogeyData);
    }

    @Redirect(
            method = "trackClicked",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tterrag/registrate/util/entry/BlockEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"
            )
    )
    public boolean modifyAssemblyConditions(BlockEntry<?> instance, ItemStack stack) {
        return stack.is(AllBlocks.RAILWAY_CASING.get().asItem());
    }
}
