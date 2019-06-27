package control;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;
import model.C4RModel;
import model.Tuple;
import model.map.MapSelection;
import model.constants.Errors;
import model.exceptions.DownloadMapException;
import model.exceptions.DuplicatedKeyException;
import model.mobility.MobilityModel;
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
    
   
    public C4R(String[] args) {
        
        view = new C4RView(this);
        model = new C4RModel(control, view);
        control = new C4RControl(model, view);  
        
        /*Simulation sim = new Simulation(new String[] {"boom"});
        try {
            sim.parseNetwork("dede.net.xml");
        } catch (FileNotFoundException | XMLStreamException ex) {
            System.out.println("Error");
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setSize(960, 960);
        frame.setVisible(true);
        frame.setSize(960, 960);
        frame.getGraphics().drawPolyline(new int[]{100,240, 300}, new int[]{100,200,300}, 3);

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
                    view.update(model, null);
                    view.update(model, true);
                }
                break;
                
            case OPEN_MAP:
                try {
                    control.openMap((String)obj);
                    view.update(model, true);
                } catch (IOException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
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
                
            case NEW_SIMULATION:
                tuple = (Tuple)obj;
                try {
                    InputStream output = control.createSimulation((String)tuple.obj1, 
                                                    (String[])tuple.obj2);
                    view.update(model, new Tuple((String)tuple.obj1, output));
                } catch (IOException ex) {
                    handleError(ex, Errors.NETCONVERT_CMD);
                } 
                break;
                
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
    
    public static void main(String[] args) {
        new control.C4R(args);
    } 

    
}
