package view.mapelements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import model.sumo.Edge;
import model.sumo.Junction;
import model.sumo.Lane;

/**
 *
 * @author Neblis
 */
public class Map extends Application {

    private final HashMap<String, Lane> lanes = new HashMap<>();
    
    private final static String ID = "id";
    
    public String name;
    private final Group group = new Group();
    
    public Map() throws FileNotFoundException, XMLStreamException {
        super();
        this.name = "mapNetconvert.net.xml";
        parseNetwork(name);
    }

    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(name);
        
        Scene scene = new Scene (group, 800, 800);
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
                    lanes.put(reader.getAttributeValue(null, ID),
                        new Lane( reader.getAttributeValue(null, Lane.LENGTH)
                                 ,reader.getAttributeValue(null, Lane.SHAPE)));
                    //System.out.println(reader.getAttributeValue(null, Lane.SHAPE));
                }else if(tag.equals(Junction.TAG)){
                    /*junctions.put(reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.SHAPE));*/
                }      
            }  
            
        }
        //printMap(lanes);
        
        for (Lane lane : lanes.values()){
            group.getChildren().add(lane.getPolyline());
            System.out.println(lane.getPolyline());
        }
        //printMap(junctions);
       
    }
    public void show(String name){
        this.name = name;
        launch();
    }
}
