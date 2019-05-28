package control;

import control.VehicleMobility.Types;
import model.C4RModel;
import model.MapSelection;
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
    
    private VehicleMobility vehicleManager;
   
    public C4R(String[] args) {
        view = new C4RView(this);
        model = new C4RModel(control, view);
        control = new C4RControl(model, view);  
        
        vehicleManager = new VehicleMobility();
    }

    @Override
    public void producedEvent(Event evento, Object obj) {
        switch (evento) {
            case SALIR:
                salir();
                break;
            case NEW_MAP:
                control.saveMap((MapSelection)obj);
                view.update(model, null);
                break;
            case NEW_VEHICLE_TYPE:
                vehicleManager.addElement(Types.VTYPE, obj);
                view.update(model, vehicleManager.getvTypes());
        } 
    }

    private void salir() {
        System.exit(0);
    }
    
    public static void handleError(Exception e, Errors error){
        switch(error){
            case OSM_DOWNLOAD:
                //logger.log(Level.SEVERE, error.toString(), e);
                e.printStackTrace();
            case NETCONVERT_CMD:
                e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new control.C4R(args);
    } 
}
