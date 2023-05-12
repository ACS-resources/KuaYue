package willow.train.kuayue.Catenary;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public abstract class CatenaryType extends EntityType {
    public static final String Catenary = "Catenary";
    public static final String Low_Catenary = "Low_Catenary";
    public static final String Wire = "Wire";
    public static final String Steel = "Steel";


    public CatenaryType(EntityFactory pFactory, MobCategory pCategory, boolean pSerialize, boolean pSummon, boolean pFireImmune, boolean pCanSpawnFarFromPlayer, ImmutableSet pImmuneTo, EntityDimensions pDimensions, int pClientTrackingRange, int pUpdateInterval) {
        super(pFactory, pCategory, pSerialize, pSummon, pFireImmune, pCanSpawnFarFromPlayer, pImmuneTo, pDimensions, pClientTrackingRange, pUpdateInterval);
    }
}
