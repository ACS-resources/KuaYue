package willow.train.kuayue.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import willow.train.kuayue.Entity.CarriageTypeSignEntity;
import willow.train.kuayue.Main;
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
        return false;
    }

    public void setCtse(CarriageTypeSignEntity ctse){
        this.ctse = ctse;
    }

    public CarriageTypeSignEntity getCtse(){
        return ctse;
    }
}
