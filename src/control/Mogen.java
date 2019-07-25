package control;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;

import model.MogenModel;
import model.Tuple;
import model.map.MapSelection;
import model.constants.Errors;
import model.exceptions.DownloadMapException;
import model.exceptions.DuplicatedKeyException;
import model.mobility.MobilityModel;
import model.routes.Flow;
import model.routes.VType;

import view.MogenView;
import view.mapsimulation.Map;

/**
 *
 * @author Denis C
 */
public class Mogen implements ViewListener{
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    
    private MogenControl control;
    private MogenModel model;
    private MogenView view;
    
    static Map map;
    
   
    public Mogen(String[] args) {
        
        view = new MogenView(this);
        model = new MogenModel(control, view);
        control = new MogenControl(model, view);  
        
    }

    @Override
    public void producedEvent(Event event, Object obj) {
        Tuple tuple;
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
                    view.update(model, model.getMap());
                }
                break;
                
            case OPEN_MAP:
                try {
                    control.openMap((String)obj);
                } catch (IOException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
                } finally {
                    view.update(model, model.getMap());
                }
                break;
                
            case NEW_VEHICLE_TYPE:
                VType type = new VType();
                
                if(model.addElement((String)obj, type)){ 
                    view.update(model, new Tuple<>((String)obj, type));
                    System.out.println(obj.toString());
                }else{
                    view.update(model, new DuplicatedKeyException
                                            (Errors.DUPLICATED_VTYPE));
                }
                break;
           
            case EXPORT:
                tuple = (Tuple)obj;
                try{
                    control.exportSimulation((MobilityModel)tuple.obj1, (String)tuple.obj2);
                    System.out.println("boom");
                } catch (IOException ex) {
                    handleError(ex, Errors.ROUTE);
                }
                break;
                
            case EDIT_VTYPE:
                tuple = (Tuple)obj;
                model.getvTypes().put((String)tuple.obj1, (VType)tuple.obj2);
                break;
                
            case NEW_FLOW:
                model.addFlow((Flow)obj);
                view.update(model, obj);
                break;
        } 
    }
    
    private void salir() {
        System.exit(0);
    }
    
    public  void handleError(Exception e, Errors error){
        switch(error){
            case OSM_DOWNLOAD:
                //logger.log(Level.SEVERE, error.toString(), e);
                view.update(model, e);
            case NETCONVERT_CMD:
                view.update(model, e);
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        new Mogen(args);
    }   
}
