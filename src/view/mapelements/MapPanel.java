package view.mapelements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
    
    private final static String ID = "id";
    
    private MogenView view;
    private Lane selectedLane; 
    
    private final static String SELECTED_LANE_COLOR = "RED";
    private final static String SELECTED_LANE_COLOR2 = "BLUE";
    private final static String UNSELECTED_LANE_COLOR = "BLACK";
    
    public MapPanel(String name) throws FileNotFoundException, XMLStreamException {
        super();
        parseNetwork(name);
    }
    
    private void parseNetwork(String location) throws FileNotFoundException, 
                                                            XMLStreamException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(location);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        reader.nextTag(); // pass the net tag
        
        int event;
        String tag;
        Group group = new Group();
        
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
                }else if(tag.equals(Junction.TAG)){
                    /*junctions.put(reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.SHAPE));*/
                }      
            }  
            
        }
        
        for (Lane lane : lanes){
            EventHandler<MouseEvent> eventHandler = (MouseEvent e) -> {
                if(selectedLane == null){
                    lane.getPolyline().setStroke(Paint.valueOf(SELECTED_LANE_COLOR));
                    selectedLane = lane;
                }else {
                    selectedLane.getPolyline().setStroke(Paint.valueOf(UNSELECTED_LANE_COLOR));
                    lane.getPolyline().setStroke(Paint.valueOf(SELECTED_LANE_COLOR));
                    selectedLane = lane;
                }
                System.out.println(lane.toString()); 
            };   
            
            //Adding event Filter 
            lane.getPolyline().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            group.getChildren().add(lane.getPolyline());
            System.out.println(lane.getPolyline());
        }
        
        Scene scene = new Scene (group);
        this.setScene(scene);
    }
}
