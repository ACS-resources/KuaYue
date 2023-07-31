package willow.train.kuayue.init;

import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import willow.train.kuayue.Entity.CatenaryBaseEntity;
import willow.train.kuayue.Entity.SmallCatenaryBaseEntity;
import willow.train.kuayue.Main;

public class EntityInit {
    public static LazyRegistrar<EntityType<?>> ENTITY_TYPES = LazyRegistrar.create(Registry.ENTITY_TYPE, Main.MOD_ID);

    public static final RegistryObject<EntityType<CatenaryBaseEntity>> CATENARY_BASE = ENTITY_TYPES.register("catenary_base",
            () -> EntityType.Builder.of((EntityType.EntityFactory<CatenaryBaseEntity>) CatenaryBaseEntity::new, MobCategory.MISC).sized(0.2F, 0.2F).build("catenary_base"));

    public static final RegistryObject<EntityType<SmallCatenaryBaseEntity>> SMALL_CATENARY_BASE = ENTITY_TYPES.register("small_catenary_base",
            () -> EntityType.Builder.of((EntityType.EntityFactory<SmallCatenaryBaseEntity>) SmallCatenaryBaseEntity::new, MobCategory.MISC).sized(0.2F, 0.2F).build("small_catenary_base"));
}
