package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {SALIR, NEW_MAP, NEW_VEHICLE_TYPE, NEW_SIMULATION, OPEN_MAP, EXPORT}
    
    public void producedEvent(Event event, Object obj);
    
}
