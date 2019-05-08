package control;

import model.C4RModel;
import model.constants.Errors;
import view.C4RView;

/**
 *
 * @author Denis C
 */
public class C4R implements ViewListener{
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    
    private C4RControl control;
    private C4RModel model;
    private C4RView view;
   
    public C4R(String[] args) {
        view = new C4RView(this);
        model = new C4RModel(control, view);
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
    
    public static void handleError(Exception e, Errors error){
        switch(error){
            case OSM_DOWNLOAD:
                //logger.log(Level.SEVERE, error.toString(), e);
            case NETCONVERT_CMD:
                System.out.println(e.toString());
        }
    }
    public static void main(String[] args) {
        new control.C4R(args);
    } 
}
