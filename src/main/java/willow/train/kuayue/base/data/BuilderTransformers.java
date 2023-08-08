package willow.train.kuayue.base.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.world.level.block.SoundType;
import willow.train.kuayue.Blocks.Bogeys.df11g_bogey.DF11GBogeyBlock;
import willow.train.kuayue.Blocks.Bogeys.standard_bogey.KYStandardBogeyBlock;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class BuilderTransformers {

    public static <B extends KYStandardBogeyBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> kystandardbogey() {
        return b -> b.initialProperties(SharedProperties::softMetal)
                .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
                .properties(p -> p.noOcclusion())
                .transform(pickaxeOnly())
                .blockstate((c, p) -> BlockStateGen.horizontalAxisBlock(c, p, s -> p.models()
                        .getExistingFile(p.modLoc("block/bogey/ky_standard/top"))))
                .loot((p, l) -> p.dropOther(l, AllBlocks.RAILWAY_CASING.get()));
    }

    public static <B extends DF11GBogeyBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> df11gbogey() {
        return b -> b.initialProperties(SharedProperties::softMetal)
                .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
                .properties(p -> p.noOcclusion())
                .transform(pickaxeOnly())
                .blockstate((c, p) -> BlockStateGen.horizontalAxisBlock(c, p, s -> p.models()
                        .getExistingFile(p.modLoc("block/bogey/df11g/top"))))
                .loot((p, l) -> p.dropOther(l, AllBlocks.RAILWAY_CASING.get()));
    }

}
