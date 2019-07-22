package view.mapsimulation;

import javafx.scene.input.MouseEvent;
import model.sumo.Lane;

/**
 *
 * @author Neblis
 */
public interface MapMouseEvent {
    
    void addFunctionToLanes(Lane lane, MouseEvent e);
}
