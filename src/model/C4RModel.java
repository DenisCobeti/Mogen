package model;

import control.C4RControl;
import java.util.Observable;
import view.C4RView;

/**
 *
 * @author Denis C
 */
public class C4RModel extends Observable{
    
    private C4RControl control;
    private C4RView view;

    public C4RModel(C4RControl control, C4RView view) {
        this.control = control;
        this.view = view;
    }
    
    
}
