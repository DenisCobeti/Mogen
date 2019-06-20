package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {SALIR, NEW_MAP, NEW_VEHICLE_TYPE, NEW_SIMULATION, 
                        OPEN_MAP, EXPORT, UPDATE_VEHICLES, EDIT_VTYPE}
    
    public void producedEvent(Event event, Object obj);
    
}
