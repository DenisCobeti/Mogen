package view.mapsimulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import model.sumo.Edge;
import model.sumo.Junction;
import model.sumo.Lane;
import view.MogenView;

/**
 *
 * @author Neblis
 */
public class MapPanel extends JFXPanel {

    private final List<Lane> lanes = new LinkedList<>();
    private final Scene scene;
    private final Group group = new Group();
    private final static String ID = "id";
    
    
    
    public MapPanel(String name) throws FileNotFoundException, 
                                        XMLStreamException, IOException {
        super();
        parseNetwork(name);
        
        scene = new Scene (group);
        Platform.setImplicitExit(false);
        this.setScene(scene);
    }
    
    public void parseNetwork(String location) throws FileNotFoundException, 
                                                            XMLStreamException,
                                                            IOException{
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
                tag = reader.getLocalName();
                
                if(tag.equals(Lane.TAG)){
                    Lane lane = new Lane( reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.LENGTH)
                                 ,reader.getAttributeValue(null, Lane.SHAPE));
                    lanes.add(lane);
                    System.out.println(lane.toString());
                }else if(tag.equals(Junction.TAG)){
                    /*junctions.put(reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.SHAPE));*/
                }      
            }  
            
        }
        reader.close();
        in.close();
    }
    
    public void addMouseHandler(MapMouseEvent handler){
        
        Platform.runLater(() -> {
                group.getChildren().clear();
        
                for (Lane lane : lanes){
                    lane.getPolyline().setOnMousePressed(null);
                    EventHandler<MouseEvent> eventHandler = (MouseEvent e) -> {
                        handler.addFunctionToLanes(lane, e);
                    };

                    //Adding event Filter 
                    lane.getPolyline().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                    group.getChildren().add(lane.getPolyline());
                }
                this.updateUI();
            });
    }
}
