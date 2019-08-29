package view.mapsimulation;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import model.topology.Edge;
import model.topology.Junction;
import model.topology.Lane;

/**
 *
 * @author Neblis
 */
public class MapPanel extends JFXPanel {

    private final List<Lane> lanes = new LinkedList<>();
    private final Scene scene;
    private final Group group = new Group();
    private final static String ID = "id";
    
    private int mouseStartX;         
    private int mouseStartY;  
    
    public MapPanel(String name) throws FileNotFoundException, 
                                        XMLStreamException, IOException {
        super();
        parseNetwork(name);
        
        scene = new Scene (group);
        Platform.setImplicitExit(false);
        this.setAutoscrolls(true);
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
    
    public void addScrollListener(JScrollPane mapScrollPane){
        this.setAutoscrolls(true);
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                mouseStartX = e.getX();
                mouseStartY = e.getY();
            }
            
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e){
                
                JViewport viewPort = mapScrollPane.getViewport();
                Point vpp = viewPort.getViewPosition();
                vpp.translate(mouseStartX-e.getX(), mouseStartY-e.getY());
                scrollRectToVisible(new Rectangle(vpp, viewPort.getSize()));
                mouseStartX = e.getX();
                mouseStartY = e.getY();
            }
        };
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }
}
