/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Denis C
 */
public interface ViewListener {
    public enum Event {SALIR}
    
    public void producedEvent(Event evento, Object obj);
}
