package willow.train.kuayue.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import willow.train.kuayue.Entity.CatenaryBaseEntity;
import willow.train.kuayue.Entity.SmallCatenaryBaseEntity;
import willow.train.kuayue.Main;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES , Main.MOD_ID);

    public static final RegistryObject<EntityType<CatenaryBaseEntity>> CATENARY_BASE = ENTITY_TYPES.register("catenary_base",
            () -> EntityType.Builder.of((EntityType.EntityFactory<CatenaryBaseEntity>) CatenaryBaseEntity::new, MobCategory.MISC).sized(0.2F, 0.2F).build("catenary_base"));

    public static final RegistryObject<EntityType<SmallCatenaryBaseEntity>> SMALL_CATENARY_BASE = ENTITY_TYPES.register("small_catenary_base",
            () -> EntityType.Builder.of((EntityType.EntityFactory<SmallCatenaryBaseEntity>) SmallCatenaryBaseEntity::new, MobCategory.MISC).sized(0.2F, 0.2F).build("small_catenary_base"));
}
