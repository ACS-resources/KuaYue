package willow.train.kuayue.Catenary;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlagSet;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public abstract class CatenaryType extends EntityType {
    public static final String Catenary = "Catenary";
    public static final String Low_Catenary = "Low_Catenary";
    public static final String Wire = "Wire";
    public static final String Steel = "Steel";

    public CatenaryType(EntityFactory p_251402_, MobCategory p_251431_, boolean p_251439_, boolean p_251973_, boolean p_252007_, boolean p_250908_, ImmutableSet p_250201_, EntityDimensions p_251742_, int p_250479_, int p_249249_, FeatureFlagSet p_250427_, Predicate velocityUpdateSupplier, ToIntFunction trackingRangeSupplier, ToIntFunction updateIntervalSupplier, BiFunction customClientFactory) {
        super(p_251402_, p_251431_, p_251439_, p_251973_, p_252007_, p_250908_, p_250201_, p_251742_, p_250479_, p_249249_, p_250427_, velocityUpdateSupplier, trackingRangeSupplier, updateIntervalSupplier, customClientFactory);
    }


//    public CatenaryType(EntityFactory pFactory, MobCategory pCategory, boolean pSerialize, boolean pSummon, boolean pFireImmune, boolean pCanSpawnFarFromPlayer, ImmutableSet pImmuneTo, EntityDimensions pDimensions, int pClientTrackingRange, int pUpdateInterval) {
//        super(pFactory, pCategory, pSerialize, pSummon, pFireImmune, pCanSpawnFarFromPlayer, pImmuneTo, pDimensions, pClientTrackingRange, pUpdateInterval);
//    }

}
