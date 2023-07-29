package willow.train.kuayue.Client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.BlockEntity.CarriageTypeSignEntity;
import willow.train.kuayue.init.MenuInit;

public class CarriageTypeSignEditMenu extends AbstractContainerMenu {

    CarriageTypeSignEntity ctse;

    public CarriageTypeSignEditMenu(@Nullable MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {return 0;}

            @Override
            public void set(int pValue) {}
        });
    }

    public CarriageTypeSignEditMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData){
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public CarriageTypeSignEditMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data){
        super(MenuInit.CARRIAGE_TYPE_SIGN_EDIT_MENU.get(), pContainerId);
        this.ctse = ((CarriageTypeSignEntity) entity);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void setCtse(CarriageTypeSignEntity ctse){
        this.ctse = ctse;
    }

    /**
     * 这个方法将负责将方块实体向Screen传递
     * @return 对应的方块实体
     */
    public CarriageTypeSignEntity getCtse(){
        return ctse;
    }
}
