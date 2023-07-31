package willow.train.kuayue.init;

import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import willow.train.kuayue.BlockEntity.CarriageNoSignEntity;
import willow.train.kuayue.Blocks.Entities.MegaphoneBlockEntity;
import willow.train.kuayue.BlockEntity.CarriageTypeSignEntity;
import willow.train.kuayue.Main;

public class BlockEntitiesInit {
    public static final LazyRegistrar<BlockEntityType<?>> BLOCK_ENTITIES =
            LazyRegistrar.create(Registry.BLOCK_ENTITY_TYPE, Main.MOD_ID);

//    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,
//            Main.MOD_ID);
    public static final RegistryObject<BlockEntityType<MegaphoneBlockEntity>> MEGAPHONE_BLOCK_ENTITIES =
            BLOCK_ENTITIES.register("megaphone_block_entity" , () ->
                    BlockEntityType.Builder.of(MegaphoneBlockEntity::new, BlockInit.MEGAPHONE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CarriageTypeSignEntity>> CARRIAGE_TYPE_SIGN =
            BLOCK_ENTITIES.register("carriage_type_sign" , () ->
                    BlockEntityType.Builder.of(CarriageTypeSignEntity::new, BlockInit.TRAIN_BOTTOM_PANEL_BLOCK.get()).build(null));

    /**
     * 这里注册方块实体的时候就已经连接了 Entity 和 Block
     * CarriageNoSignEntity::new 是调用 Entity 的构造体，
     * Block.TRAIN_NO_BLOCK.get() 是调用对应的方块
     */
    public static final RegistryObject<BlockEntityType<CarriageNoSignEntity>> CARRIAGE_NO_SIGN =
            BLOCK_ENTITIES.register("carriage_no_sign" , () ->
                    BlockEntityType.Builder.of(CarriageNoSignEntity::new, BlockInit.TRAIN_NO_BLOCK.get()).build(null));

    //public static final RegistryObject<BlockEntityType<CarriageTypeSignEntity>> CARRIAGE_TYPE_SIGN2 =
            //BLOCK_ENTITIES.register("carriage_type_sign2" , () ->
                    //BlockEntityType.Builder.of(CarriageTypeSignEntity::new, BlockInit.PANEL_25B_ORIGINAL_BOTTOM.get()).build(null));


//    public static final RegistryObject<BlockEntityType<MegaphoneBlockEntity>> MegaphoneBlockEntity =
//            BLOCK_ENTITIES.register("megaphone_block_entity", () ->
//            BlockEntityType.Builder.of(MegaphoneBlockEntity::new, BlockInit.MEGAPHONE.get()).build());
    public static void register(){
        BLOCK_ENTITIES.register();
    }
}
