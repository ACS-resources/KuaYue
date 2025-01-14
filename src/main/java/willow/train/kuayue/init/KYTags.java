package willow.train.kuayue.init;

import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import willow.train.kuayue.Main;

import static willow.train.kuayue.init.KYTags.NameSpace.MOD;

public class KYTags {

    public enum NameSpace {

        MOD(Main.MOD_ID, false, true), FORGE("forge")

        ;

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;

        NameSpace(String id) {
            this(id, true, false);
        }

        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }

    }



    public enum AllBlockTags {
        SEMAPHORE_POLES(MOD,MOD.optionalDefault,false),
        TRACK_CASING_BLACKLIST(MOD,MOD.optionalDefault,false),
        CONDUCTOR_SPY_USABLE(MOD, MOD.optionalDefault,false) // so other mods / datapacks can make more blocks usable for conductor spies
        ;

        public final TagKey<Block> tag;


        AllBlockTags() {
            this(MOD);
        }

        AllBlockTags(willow.train.kuayue.init.KYTags.NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllBlockTags(willow.train.kuayue.init.KYTags.NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllBlockTags(willow.train.kuayue.init.KYTags.NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        AllBlockTags(willow.train.kuayue.init.KYTags.NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            tag = optionalTag(Registry.BLOCK, id);
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Block block) {
            return block.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof BlockItem blockItem && matches(blockItem.getBlock());
        }

        public boolean matches(BlockState state) {
            return state.is(tag);
        }

        public static void register() {
        }
    }

    public enum AllItemTags {
        CONDUCTOR_CAPS
        ;

        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        AllItemTags() {
            this(MOD);
        }

        AllItemTags(willow.train.kuayue.init.KYTags.NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllItemTags(willow.train.kuayue.init.KYTags.NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        AllItemTags(willow.train.kuayue.init.KYTags.NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        AllItemTags(willow.train.kuayue.init.KYTags.NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            tag = optionalTag(Registry.ITEM, id);
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Item item) {
            return item.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        }

        public static void register() {
        }
    }

    public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
        return TagKey.create(registry.key(), id);
    }

    // load all classes
    public static void register() {
        willow.train.kuayue.init.KYTags.AllBlockTags.register();
        willow.train.kuayue.init.KYTags.AllItemTags.register();
    }
}
