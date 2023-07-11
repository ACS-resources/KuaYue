package willow.train.kuayue.Items.catenary;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static willow.train.kuayue.Main.CATENARYCONNECTIONHANDLER;

public class GeneralStraight extends Item{

    BlockPos posA = null, posB = null;

        public GeneralStraight(Properties p_41383_) {
            super(p_41383_);
        }

        /*
        @Override
        public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, List<Component> tooltips, TooltipFlag p_41424_) {
            tooltips.add(new TranslatableComponent("item.kuayue.train_diet_1.tooltip"));
            super.appendHoverText(p_41421_, p_41422_, tooltips, p_41424_);
        }
         */

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pPlayer.level().isClientSide()) {
            /*
            CatenaryBaseEntity test = new CatenaryBaseEntity(EntityInit.CATENARY_BASE.get(), pPlayer , pPlayer.level);
            test.setDeltaMovement(0,0,0);
            test.setXRot(45.0F);
            test.setYRot(30.0F);
            pPlayer.level.addFreshEntity(test);
             */
            if(pPlayer.isShiftKeyDown()){
                CATENARYCONNECTIONHANDLER.remove(pPlayer);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
