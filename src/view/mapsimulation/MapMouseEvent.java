package view.mapsimulation;

import javafx.scene.input.MouseEvent;
import model.topology.Lane;

/**
 *
 * @author Neblis
 */
public interface MapMouseEvent {
    
    static final String SELECTED_LANE_COLOR = "RED";
    static final String SELECTED_LANE_COLOR_ALT = "BLUE";
    static final String UNSELECTED_LANE_COLOR = "BLACK";
    static final String INTERNAL_LANE_COLOR = "GREY";
    
    void addFunctionToLanes(Lane lane, MouseEvent e);
    void unselectLanes();
}
