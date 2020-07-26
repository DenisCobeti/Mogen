package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {EXIT, NEW_MAP, NEW_VEHICLE_TYPE, NEW_SIMULATION, 
                        OPEN_MAP, EXPORT_RANDOM, EXPORT_FLOW, UPDATE_VEHICLES, 
                        EDIT_VTYPE, NEW_SIMULATION_CMD, NEW_FLOW, EDIT_PYTHON, 
                        EDIT_SUMO, REMOVE_VTYPE, FILTER_ROADS, NEW_TAZ, 
                        SAVE_VEHICLES, NEW_ODELEMENT, EXPORT_ODMATRIX,
                        REMOVE_TAZ, REMOVE_OD_ELEMENT, REMOVE_FLOW, EDIT_FLOW,
                        ROADS_OPTIONS}
    
    public enum TableTypes{TAZType, FlowType, ODElementType}
    
    public void producedEvent(Event event, Object obj);
    
}
