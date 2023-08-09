package willow.train.kuayue.Client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.BlockEntity.LaqueredBoardEntity;
import willow.train.kuayue.init.MenuInit;

public class LaqueredBoardEditMenu  extends AbstractContainerMenu {
    LaqueredBoardEntity lbe;

    public LaqueredBoardEditMenu(@Nullable MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {return 0;}

            @Override
            public void set(int pValue) {}
        });
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    public LaqueredBoardEditMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData){
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public LaqueredBoardEditMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data){
        super(MenuInit.LAQUERED_BOARD_EDIT_MENU.get(), pContainerId);
        this.lbe = ((LaqueredBoardEntity) entity);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void setLbe(LaqueredBoardEntity lbe){
        this.lbe = lbe;
    }

    /**
     * 这个方法将负责将方块实体向Screen传递
     * @return 对应的方块实体
     */
    public LaqueredBoardEntity getLbe(){
        return lbe;
    }
}
