package willow.train.kuayue.Blocks.Switches;

import com.simibubi.create.CreateClient;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.graph.TrackNodeLocation;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.Util.EntityUtils;
import willow.train.kuayue.init.KYEdgePointTypes;

import java.util.Map;
import java.util.Objects;

public class TrackSwitchDebugVisualizer {

    public static void visualizeSwitchExits(TrackSwitch sw) {
        Minecraft mc = Minecraft.getInstance();
        Entity camera = mc.cameraEntity;
        if (camera == null) {
            return;
        }

        Vec3 offset = new Vec3(0, 0.4, 0);
        float width = 1 / 16f;

        TrackNodeLocation from = sw.getSwitchPoint();
        TrackNodeLocation activeExit = sw.getSwitchTarget();
        for (TrackNodeLocation to : sw.getExits()) {
            boolean active = to == activeExit;
            CreateClient.OUTLINER.showLine(to,
                            from.getLocation().add(offset),
                            to.getLocation().add(offset))
                    .colored(active ? new Color(0, 203, 150) : new Color(255, 50, 150))
                    .lineWidth(width);
        }
    }

    public static void visualizePotentialLocations() {
        Minecraft mc = Minecraft.getInstance();
        Entity camera = mc.cameraEntity;
        if (camera == null)
            return;

        if (mc.player == null)
            return;

        boolean skipHints = !EntityUtils.isHoldingItem(mc.player, (item) -> item instanceof TrackSwitchBlockItem);

        if (skipHints) {
            HitResult hitResult = mc.hitResult;
            if (!(hitResult instanceof BlockHitResult blockHitResult))
                return;

            if (blockHitResult.getType() == HitResult.Type.MISS)
                return;

            BlockPos pos = blockHitResult.getBlockPos();
            BlockState state = mc.player.level.getBlockState(pos);
            if (!(state.getBlock() instanceof TrackSwitchBlock))
                return;
        }

        int range = 64;
        int rangeSqr = range*range;

        for (TrackGraph graph : CreateClient.RAILWAYS.trackNetworks.values()) {
            for (TrackNodeLocation tnl : graph.getNodes()) {
                if (!Objects.equals(tnl.dimension, mc.level.dimension()))
                    continue;
                if (tnl.getLocation().distanceToSqr(camera.position()) > rangeSqr)
                    continue;

                for (TrackSwitch sw : graph.getPoints(KYEdgePointTypes.SWITCH)) {
                    TrackSwitchDebugVisualizer.visualizeSwitchExits(sw);
                }

                if (skipHints)
                    continue;

                TrackNode node = graph.locateNode(tnl);
                Map<TrackNode, TrackEdge> connections = graph.getConnectionsFrom(node);
                int connectionCount = connections.size();
                if (connectionCount > 2 && connectionCount <= 4) {
                    Vec3 basePos = node.getLocation().getLocation();
                    Vec3 averageOffset = Vec3.ZERO;
                    Vec3[] offsets = new Vec3[connectionCount];
                    int i = 0;
                    for (Map.Entry<TrackNode, TrackEdge> entry : connections.entrySet()) {
                        Vec3 offset = entry.getKey().getLocation().getLocation()
                                .subtract(basePos)
                                .normalize();
                        averageOffset = averageOffset.add(offset);
                        offsets[i] = offset;
                        i++;
                    }
                    Vec3 farthestOffset = Vec3.ZERO;
                    double farthestDistance = 0;

                    for (Vec3 offset : offsets) {
                        double distance = averageOffset.distanceToSqr(offset);
                        if (distance > farthestDistance) {
                            farthestDistance = distance;
                            farthestOffset = offset;
                        }
                    }

                    Direction offsetDirection = Direction.getNearest(farthestOffset.x, farthestOffset.y, farthestOffset.z);
                    CreateClient.OUTLINER.showAABB(node, AABB.ofSize(tnl.getLocation()
                                            .add(farthestOffset.x/2., farthestOffset.y/2., farthestOffset.z/2.)
                                            .add(0, 8 / 16f, 0),
                                    1, 1, 1))
                            .colored(graph.color)
                            //.disableLineNormals()
                            .lineWidth(1 / 16f);
                }
            }
        }
    }
}
