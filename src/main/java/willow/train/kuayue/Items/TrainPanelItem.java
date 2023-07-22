package willow.train.kuayue.Items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import willow.train.kuayue.Util.PanelTypes;

public class TrainPanelItem extends ToolTipsItemHelper{

    public PanelTypes panelTypes = PanelTypes.P25B;
    public TrainPanelItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public InteractionResult useOn(UseOnContext pContext) {

        if(pContext.getPlayer().isShiftKeyDown()){return InteractionResult.FAIL;}

        InteractionResult interactionresult = this.place(new BlockPlaceContext(pContext));

        if (!interactionresult.consumesAction() && this.isEdible()) {
            InteractionResult interactionresult1 = this.use(pContext.getLevel(), pContext.getPlayer(), pContext.getHand()).getResult();
            return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionresult1;
        } else {
            return interactionresult;
        }
    }

    @Override
    public boolean useOnRelease(ItemStack pStack) {
        return super.useOnRelease(pStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide()) {
            if (pPlayer.isShiftKeyDown()) {
                PanelTypes[] types = PanelTypes.values();
                int x = 0;

                for (; x < types.length; x++) {
                    if (types[x] == panelTypes) break;
                }

                if (x < types.length - 1) {
                    panelTypes = types[x + 1];
                } else {
                    panelTypes = types[0];
                }

                System.out.println("panel type " + panelTypes.toString());
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            }
        }else {
            if (pPlayer.isShiftKeyDown()){
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            }
            return super.use(pLevel, pPlayer, pUsedHand);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
