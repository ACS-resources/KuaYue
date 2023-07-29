package willow.train.kuayue.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import willow.train.kuayue.Blocks.Entities.MegaphoneBlockEntity;
import willow.train.kuayue.Entity.CarriageTypeSignEntity;
import willow.train.kuayue.Main;

public class BlockEntitiesInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MOD_ID);

//    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,
//            Main.MOD_ID);
    public static final RegistryObject<BlockEntityType<MegaphoneBlockEntity>> MEGAPHONE_BLOCK_ENTITIES =
            BLOCK_ENTITIES.register("megaphone_block_entity" , () ->
                    BlockEntityType.Builder.of(MegaphoneBlockEntity::new, BlockInit.MEGAPHONE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CarriageTypeSignEntity>> CARRIAGE_TYPE_SIGN =
            BLOCK_ENTITIES.register("carriage_type_sign" , () ->
                    BlockEntityType.Builder.of(CarriageTypeSignEntity::new, BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get()).build(null));

    //public static final RegistryObject<BlockEntityType<CarriageTypeSignEntity>> CARRIAGE_TYPE_SIGN2 =
            //BLOCK_ENTITIES.register("carriage_type_sign2" , () ->
                    //BlockEntityType.Builder.of(CarriageTypeSignEntity::new, BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get()).build(null));


//    public static final RegistryObject<BlockEntityType<MegaphoneBlockEntity>> MegaphoneBlockEntity =
//            BLOCK_ENTITIES.register("megaphone_block_entity", () ->
//            BlockEntityType.Builder.of(MegaphoneBlockEntity::new, BlockInit.MEGAPHONE.get()).build());
    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
