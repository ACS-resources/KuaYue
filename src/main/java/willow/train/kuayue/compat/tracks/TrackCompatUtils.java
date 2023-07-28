package willow.train.kuayue.compat.tracks;

import com.google.common.collect.ImmutableSet;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.trains.track.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.ApiStatus;
import willow.train.kuayue.Main;
import willow.train.kuayue.compat.Mods;
import willow.train.kuayue.init.KYTrackMaterials;
import willow.train.kuayue.mixin.AccessorTrackMaterialFactory;

import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

import static willow.train.kuayue.init.KYTagGen.addOptionalTag;

public abstract class TrackCompatUtils {

    public static final Set<String> TRACK_COMPAT_MODS = ImmutableSet.of(
            "hexcasting",
            "byg", // Oh The Biomes You'll Go,
            "blue_skies",
            "twilightforest",
            "biomesoplenty"
    );

    public static boolean anyLoaded() {
        if (GenericTrackCompat.isDataGen())
            return true;
        for (String mod : TRACK_COMPAT_MODS) {
            if (Mods.valueOf(mod.toUpperCase(Locale.ROOT)).isLoaded)
                return true;
        }
        return false;
    }

    @ApiStatus.Internal
    public static boolean mixinIgnoreErrorForMissingItem(ResourceLocation resourceLocation) {
        if (resourceLocation.getNamespace().equals(Main.MOD_ID)) {
            for (String compatMod : TRACK_COMPAT_MODS) {
                if (resourceLocation.getPath().contains(compatMod)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final CreateRegistrate REGISTRATE = Main.registrate();

    public static BlockEntry<TrackBlock> makeTrack(TrackMaterial material) {
        return makeTrack(material, new CompatTrackBlockStateGenerator()::generate);
    }

    public static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, boolean hideInCreativeTabs) {
        return makeTrack(material, new CompatTrackBlockStateGenerator()::generate, (t) -> {}, (p) -> p, hideInCreativeTabs);
    }

    public static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen) {
        return makeTrack(material, blockstateGen, (t) -> {});
    }

    public static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen, NonNullConsumer<? super TrackBlock> onRegister) {
        return makeTrack(material, blockstateGen, onRegister, (p) -> p);
    }

    public static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen, Function<BlockBehaviour.Properties, BlockBehaviour.Properties> collectProperties) {
        return makeTrack(material, blockstateGen, (t) -> {}, collectProperties);
    }

    public static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen, NonNullConsumer<? super TrackBlock> onRegister, Function<BlockBehaviour.Properties, BlockBehaviour.Properties> collectProperties) {
        return makeTrack(material, blockstateGen, onRegister, collectProperties, false);
    }

    public static BlockEntry<TrackBlock> makeTrack(TrackMaterial material, NonNullBiConsumer<DataGenContext<Block, TrackBlock>, RegistrateBlockstateProvider> blockstateGen, NonNullConsumer<? super TrackBlock> onRegister, Function<BlockBehaviour.Properties, BlockBehaviour.Properties> collectProperties, boolean hideInCreativeTabs) {
        String owningMod = material.id.getNamespace();
        String name = "track_" + owningMod + "_" + material.resourceName();

        addOptionalTag(Main.asResource(name), AllTags.AllBlockTags.TRACKS.tag,
                BlockTags.MINEABLE_WITH_PICKAXE); // pickaxe-mineable tag is moved here as Registrate cannot add optional tag in BlockBuilder
        if (material.trackType != KYTrackMaterials.KYTrackType.STANDARD)
            addOptionalTag(Main.asResource(name), AllTags.AllBlockTags.GIRDABLE_TRACKS.tag);

        return REGISTRATE.block(name, material::createBlock)
                .initialProperties(Material.STONE)
                .properties(p -> collectProperties.apply(p)
                        .color(MaterialColor.METAL)
                        .strength(0.8F)
                        .sound(SoundType.METAL)
                        .noOcclusion())
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(blockstateGen)
                .lang(material.langName + " Train Track")
                .onRegister(onRegister)
                .onRegister(CreateRegistrate.blockModel(() -> TrackModel::new))
                .item(TrackBlockItem::new)
                .properties(p -> {
                    if (hideInCreativeTabs) //noinspection DataFlowIssue
                        p.tab(null);
                    return p;
                })
                .model((c, p) -> p.generated(c, new ResourceLocation(owningMod, "item/track/track_"+material.resourceName())))
                .build()
                .register();
    }

    public static TrackMaterial buildCompatModels(TrackMaterialFactory factory) {
        String namespace = ((AccessorTrackMaterialFactory)factory).getId().getNamespace();
        String path = ((AccessorTrackMaterialFactory)factory).getId().getPath();
        String prefix = "block/track/compat/" + namespace + "/" + path + "/";
        return factory.customModels(
                () -> () -> new PartialModel(Main.asResource(prefix + "tie")),
                () -> () -> new PartialModel(Main.asResource(prefix + "segment_left")),
                () -> () -> new PartialModel(Main.asResource(prefix + "segment_right"))
        ).build();
    }
}
