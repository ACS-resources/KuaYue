package willow.train.kuayue.Catenary;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;

public class Catenary extends CatenaryType{

    public Catenary(EntityFactory pFactory, MobCategory pCategory, boolean pSerialize, boolean pSummon, boolean pFireImmune, boolean pCanSpawnFarFromPlayer, ImmutableSet pImmuneTo, EntityDimensions pDimensions, int pClientTrackingRange, int pUpdateInterval) {
        super(pFactory, pCategory, pSerialize, pSummon, pFireImmune, pCanSpawnFarFromPlayer, pImmuneTo, pDimensions, pClientTrackingRange, pUpdateInterval);
    }
}
