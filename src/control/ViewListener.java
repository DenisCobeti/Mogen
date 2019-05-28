package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {SALIR, NEW_MAP, NEW_VEHICLE_TYPE}
    
    public void producedEvent(Event evento, Object obj);
}
