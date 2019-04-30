package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {SALIR}
    
    public void producedEvent(Event evento, Object obj);
}
