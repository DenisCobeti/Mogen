package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {SALIR, NEW_MAP, NEW_VEHICLE_TYPE, NEW_SIMULATION, 
                        OPEN_MAP, EXPORT_RANDOM, EXPORT_FLOW, UPDATE_VEHICLES, 
                        EDIT_VTYPE, NEW_SIMULATION_CMD, NEW_FLOW, EDIT_PYTHON, 
                        EDIT_SUMO, REMOVE_VTYPE}
    
    public void producedEvent(Event event, Object obj);
    
}
