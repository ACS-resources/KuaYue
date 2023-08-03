package willow.train.kuayue.Blocks.Switches;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.track.ITrackBlock;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.ponder.PonderWorld;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.Util.CustomTrackOverlayRendering;
import willow.train.kuayue.init.KYBlockPartials;

import java.util.Map;

public class TrackSwitchRenderer extends SmartBlockEntityRenderer<TrackSwitchTileEntity> {

    public TrackSwitchRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    private void renderPonderData(PonderWorld ponderWorld, TrackSwitchBlock.SwitchState state, TrackSwitchTileEntity.PonderData ponderData, float partialTicks, PoseStack ms,
                                  MultiBufferSource buffer, int light, int overlay) {
        ms.pushPose();
        Vec3 offset = new Vec3(0, 0.4, 0);
        float width = 1 / 16f;

        Vec3 from = ponderData.basePos();
        for (Map.Entry<TrackSwitchBlock.SwitchState, Vec3> to : ponderData.getBranches().entrySet()) {
            boolean active = to.getKey() == state;
            ponderWorld.scene.getOutliner().showLine(to,
                            from.add(offset),
                            to.getValue().add(offset))
                    .colored(active ? new Color(0, 203, 150) : new Color(255, 50, 150))
                    .lineWidth(width);
        }
        ms.popPose();
    }
}
