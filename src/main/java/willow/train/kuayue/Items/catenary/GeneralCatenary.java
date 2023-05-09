package willow.train.kuayue.Items.catenary;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.Catenary.CatenaryConnectionPack;
import willow.train.kuayue.Catenary.Connections;
import willow.train.kuayue.Entity.CatenaryBaseEntity;
import willow.train.kuayue.Main;
import willow.train.kuayue.init.EntityInit;

import static willow.train.kuayue.Main.CATENARYCONNECTIONHANDLER;

public class GeneralCatenary extends Item{

    BlockPos posA = null, posB = null;

        public GeneralCatenary(Properties p_41383_) {
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
        if(!pPlayer.level.isClientSide()) {
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
