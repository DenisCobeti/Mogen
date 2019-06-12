package control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import model.C4RModel;
import model.Tuple;
import model.map.MapSelection;
import model.constants.Errors;
import model.exceptions.DownloadMapException;
import model.exceptions.DuplicatedKeyException;
import model.routes.VType;
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
        //view = new C4RView(this);
        //model = new C4RModel(control, view);
        //control = new C4RControl(model, view);  
        
        //vehicleManager = new VehicleMobility();
        Simulation sim = new Simulation("boom");
        try {
            sim.parseNetwork("New3.net.xml");
        } catch (FileNotFoundException | XMLStreamException ex) {
            System.out.println("Error");
        }
    }

    @Override
    public void producedEvent(Event event, Object obj) {
        switch (event) {
            case SALIR:
                salir();
                break;
                
            case NEW_MAP:
                try {
                    control.saveMap((MapSelection)obj);
                } catch (IOException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
                }finally{
                    view.update(model, null);
                }
                break;
                
            case OPEN_MAP:
                try {
                    control.openMap((String)obj);
                } catch (IOException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
                }finally{
                    view.update(model, null);
                }
                break;
                
            case NEW_VEHICLE_TYPE:
                VType type = new VType();
                
                if(vehicleManager.addElement((String)obj, type)){ 
                    view.update(model, new Tuple<>((String)obj, type));
                    System.out.println(obj.toString());
                }else{
                    view.update(model, new DuplicatedKeyException
                                            (Errors.DUPLICATED_VTYPE));
                }
                break;
                
            case NEW_SIMULATION:
                Tuple tuple = (Tuple)obj;
                Simulation sim = null;
                try {
                    sim = control.createSimulation((String)tuple.obj1, (String[])tuple.obj2);
                    view.update(model, sim);
                } catch (IOException ex) {
                    handleError(ex, Errors.NETCONVERT_CMD);
                    
                } 
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
                e.printStackTrace();
            case NETCONVERT_CMD:
                e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new control.C4R(args);
    } 

    
}
