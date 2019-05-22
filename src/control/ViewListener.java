package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {SALIR, NEW_MAP}
    
    public void producedEvent(Event evento, Object obj);
}
