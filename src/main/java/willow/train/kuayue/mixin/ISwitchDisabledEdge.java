package willow.train.kuayue.mixin;

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
        return ((ISwitchDisabledEdge) edge.getEdgeData()).isEnabled();
    }

    static boolean isDisabled(TrackEdge edge) {
        return !isEnabled(edge);
    }

    static boolean isAutomatic(TrackEdge edge) {
        return ((ISwitchDisabledEdge) edge.getEdgeData()).isAutomatic();
    }

    static void automaticallySelect(TrackEdge edge) {
        ((ISwitchDisabledEdge) edge.getEdgeData()).setAutomaticallySelected();
    }
}
