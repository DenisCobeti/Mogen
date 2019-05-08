package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {SALIR, NUEVO_MAPA}
    
    public void producedEvent(Event evento, Object obj);
}
