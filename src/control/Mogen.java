package control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import javax.xml.stream.XMLStreamException;
import model.Config;

import model.MogenModel;
import model.Tuple;
import model.map.MapSelection;
import model.constants.Errors;
import model.exceptions.DownloadMapException;
import model.exceptions.DuplicatedKeyException;
import model.mobility.MobilityModel;
import model.routes.Flow;
import model.routes.TAZ;
import model.routes.VType;

import view.MogenView;

/**
 *
 * @author Denis C
 */
public class Mogen implements ViewListener{
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    
    private MogenControl control;
    private final MogenModel model;
    private final MogenView view;   
   
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
                tuple = (Tuple)obj;
                try {
                    control.saveMap((MapSelection)tuple.obj1, (String)tuple.obj2);
                } catch (IOException | InterruptedException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
                }finally{
                    view.update(model, model.getMap());
                }
                break;
                
            case OPEN_MAP:
                try {
                    control.openMap((String)obj);
                } catch (IOException | InterruptedException ex) {
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
                
           case NEW_FLOW:
                Flow flow = (Flow)obj;
                view.update(model, model.addFlow(flow));
                break;
                
           case NEW_TAZ:
                tuple = (Tuple)obj;
                view.update(model, model.addTAZ((String)tuple.obj1, (TAZ)tuple.obj2));
                break;
                
           case EXPORT_RANDOM:
                tuple = (Tuple)obj;
                try{
                    control.exportSimulation((MobilityModel)tuple.obj1, 
                            (String)tuple.obj2);
                } catch (IOException | InterruptedException ex) {
                    handleError(ex, Errors.ROUTE);
                }
                break;
                
            case EXPORT_FLOW:
                tuple = (Tuple)obj;
                try{
                    control.exportFlows((String)tuple.obj1, (int)tuple.obj2);
                } catch (IOException | InterruptedException ex) {
                    handleError(ex, Errors.ROUTE);
                }
                break; 
                
            case EDIT_VTYPE:
                tuple = (Tuple)obj;
                model.getvTypes().put((String)tuple.obj1, (VType)tuple.obj2);
                break;
                
            case FILTER_ROADS:
                HashSet roads = (HashSet)obj;
                control.setRoadsFiltered(roads);
                break;
                
            case REMOVE_VTYPE:
                model.removeVType((String)obj);
                break;
                
            case EDIT_PYTHON:
                Config.setPython2((String)obj);
                break;
                
            case EDIT_SUMO:
                Config.setSumoLocation((String)obj);
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
