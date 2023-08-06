package willow.train.kuayue.Util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Contract;

import java.util.function.Function;

public class EntityUtils {
    public static CompoundTag getPersistentData(Entity entity) {
        return entity.getPersistentData();
    }

    /**
     * Gives a player an item. Plays the pickup sound, and drops whatever can't be picked up.
     */
    public static void givePlayerItem(Player player, ItemStack stack) {
        ItemHandlerHelper.giveItemToPlayer(player, stack);
    }

    public static double getReachDistance(Player player) {
        return player.getReachDistance();
    }

    /**
     * Fire a use event.
     * @return true if the use is allowed, false otherwise
     */
    @Contract
    public static boolean handleUseEvent(Player player, InteractionHand hand, BlockHitResult hit) {
        PlayerInteractEvent.RightClickBlock event = ForgeHooks.onRightClickBlock(player, InteractionHand.MAIN_HAND, hit.getBlockPos(), hit);
        return event.getResult() != Event.Result.DENY;
    }

    public static boolean isHolding(Player player, Function<ItemStack, Boolean> test) {
        return test.apply(player.getItemInHand(InteractionHand.MAIN_HAND))
                || test.apply(player.getItemInHand(InteractionHand.OFF_HAND));
    }

    public static boolean isHoldingItem(Player player, Function<Item, Boolean> test) {
        return isHolding(player, (stack) -> test.apply(stack.getItem()));
    }
}

