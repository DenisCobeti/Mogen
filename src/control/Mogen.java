package control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;
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
import view.mapsimulation.MapPanel;

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
        /*
        Simulation sim = new Simulation(new String[] {"boom"});
        try {
            sim.parseNetwork("mapNetconvert.net.xml");
        } catch (FileNotFoundException | XMLStreamException ex) {
            System.out.println("Error");
        }*/
        /*
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        frame.add(new MapSystem());
        frame.setSize(960, 960);
        frame.setVisible(true);
        frame.repaint();*/
        
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
            /*
            case NEW_SIMULATION:
                tuple = (Tuple)obj;
                try {
                    InputStream output = control.createSimulation((String)tuple.obj1, 
                                                    (String[])tuple.obj2);
                    view.update(model, new Tuple((String)tuple.obj1, output));
                } catch (IOException ex) {
                    handleError(ex, Errors.NETCONVERT_CMD);
                } 
                break;*/
            /* 
            case NEW_SIMULATION_CMD:
                try {
                    InputStream output = control.createSimulation((String[])obj);
                    view.update(model, output);
                    view.update(model, control.DEFAULT_MAP_NAME);
                } catch (IOException ex) {
                    handleError(ex, Errors.NETCONVERT_CMD);
                } 
                break;*/
                
            case EXPORT:
                tuple = (Tuple)obj;
                try{
                    control.exportSimulation((MobilityModel)tuple.obj1, 
                                             (String)tuple.obj2);
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
        new control.Mogen(args);
        /*
        MapPanel s = new MapPanel("mapNetconvert.net.xml");
        JFrame frame = new JFrame("Swing and JavaFX");
        frame.add(s);
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        //s.show("mapNetconvert.net.xml", null);
    }   
}