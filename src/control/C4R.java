package control;

import model.C4RModel;
import view.C4RView;

/**
 *
 * @author Denis C
 */
public class C4R implements ViewListener{
    
    private C4RControl control;
    private C4RModel model;
    private C4RView view;
   
    public C4R(String[] args) {
        model = new C4RModel(control, view);
        view = new C4RView(this);
        control = new C4RControl(model, view);
    }

    @Override
    public void producedEvent(Event evento, Object obj) {
        switch (evento) {
            case SALIR:
                salir();
                break;
        } 
    }

    private void salir() {
        System.exit(0);
    }
    
    public static void main(String[] args) {
        new control.C4R(args);
    } 
}
