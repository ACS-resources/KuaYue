package willow.train.kuayue.mixin_interfaces;

import com.simibubi.create.content.trains.graph.TrackEdge;

public interface ISwitchDisabledEdge {

    void setEnabled(boolean enabled);
    boolean isEnabled();

    void setAutomatic(boolean automatic);
    boolean isAutomatic();

    void setAutomaticallySelected();
    int getAutomaticallySelectedPriority();
    boolean isAutomaticallySelected();
    void ackAutomaticSelection();

    static boolean isEnabled(TrackEdge edge) {
        return ((willow.train.kuayue.mixin_interfaces.ISwitchDisabledEdge) edge.getEdgeData()).isEnabled();
    }

    static boolean isDisabled(TrackEdge edge) {
        return !isEnabled(edge);
    }

    static boolean isAutomatic(TrackEdge edge) {
        return ((willow.train.kuayue.mixin_interfaces.ISwitchDisabledEdge) edge.getEdgeData()).isAutomatic();
    }

    static void automaticallySelect(TrackEdge edge) {
        ((willow.train.kuayue.mixin_interfaces.ISwitchDisabledEdge) edge.getEdgeData()).setAutomaticallySelected();
    }
}

