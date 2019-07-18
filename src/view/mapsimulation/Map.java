package view.mapsimulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import model.constants.Netconvert;
import model.sumo.Edge;
import model.sumo.Junction;
import model.sumo.Lane;
import view.MogenView;

/**
 *
 * @author Neblis
 */
public class Map extends Application {

    private final List<Lane> lanes = new LinkedList<>();
    
    private final static String ID = "id";
    private final static int SIZE_X = 800;
    private final static int SIZE_Y = 800;
    
    private String name;
    private MogenView view;
    private final Group group = new Group();
    
    public Map() throws FileNotFoundException, XMLStreamException {
        super();
        this.name = Netconvert.MAP_FILE.toString();
    }

    
    @Override
    public void start(Stage stage) throws Exception {
        parseNetwork(name);
        stage.setTitle(name);
        
        Scene scene = new Scene (group, SIZE_X, SIZE_Y);
        stage.setScene(scene);
        stage.show();
    }
    
    private void parseNetwork(String location) throws FileNotFoundException, 
                                                            XMLStreamException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(location);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        reader.nextTag(); // pass the net tag
        
        int event;
        String tag;
        
        while(!reader.getLocalName().equals(Edge.TAG)) reader.nextTag(); // go to edges
        while (reader.hasNext()){
            event = reader.next();
            if(event == XMLStreamConstants.START_ELEMENT){
                /*switch(reader.getLocalName()){
                     case Lane.TAG:
                         lanes.put(reader.getAttributeValue(null, ID),
                             new Lane( reader.getAttributeValue(null, Lane.LENGTH)
                                 ,reader.getAttributeValue(null, Lane.SHAPE)));
                         System.out.println(reader.getAttributeValue(null, Lane.SHAPE));
                         
                         break;
                     case Junction.TAG:
                        
                         break;
                     default: 
                        
                }*/
                
                tag = reader.getLocalName();
                
                if(tag.equals(Lane.TAG)){
                    Lane lane = new Lane( reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.LENGTH)
                                 ,reader.getAttributeValue(null, Lane.SHAPE));
                    lanes.add(lane);
                    //System.out.println(reader.getAttributeValue(null, Lane.SHAPE));
                }else if(tag.equals(Junction.TAG)){
                    /*junctions.put(reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.SHAPE));*/
                }      
            }  
            
        }
        //printMap(lanes);
        
        for (Lane lane : lanes){
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
                @Override 
                public void handle(MouseEvent e) { 
                    System.out.println(lane.toString());
                } 
            };   
            //Adding event Filter 
            lane.getPolyline().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            group.getChildren().add(lane.getPolyline());
            System.out.println(lane.getPolyline());
        }
        //printMap(junctions);
       
    }
    public void show(String name, MogenView view) throws FileNotFoundException, XMLStreamException{
        this.name = name;
        this.view = view;
        launch();
    }
}
