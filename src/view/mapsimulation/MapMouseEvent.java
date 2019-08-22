package view.mapsimulation;

import javafx.scene.input.MouseEvent;
import model.sumo.Lane;

/**
 *
 * @author Neblis
 */
public interface MapMouseEvent {
    
    static final String SELECTED_LANE_COLOR = "RED";
    static final String UNSELECTED_LANE_COLOR = "BLACK";
    
    void addFunctionToLanes(Lane lane, MouseEvent e);
    void unselectLanes();
}
