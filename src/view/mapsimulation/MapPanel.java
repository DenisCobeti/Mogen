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
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

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
    private final Group group = new Group();
    private final static String ID = "id";
    private String currentEdge = "";
    private EventHandler<MouseEvent> currentEventHandler;
    
    public int mouseStartX;         
    public int mouseStartY; 
    
    public MapPanel(String name) throws FileNotFoundException, 
                                        XMLStreamException, IOException {
        super();
        parseNetwork(name);
        
        Platform.setImplicitExit(false);
        this.setAutoscrolls(true);
        
        
    }
    
    public void selectRoad(String[] roads){
        lanes.forEach(lane -> {
            for (String road : roads){
                if(lane.getName().equals(road)) 
                    lane.getPolyline().setStroke
                            (Paint.valueOf(MapMouseEvent.SELECTED_LANE_COLOR));
            }
        });
    }
    
    public Lane[] selectRoadFlows(String[] roads){
        if (roads.length == 2){
            Lane[] lanesSelected = new Lane[2];
            
            lanes.forEach(lane -> {
                if(lane.getName().equals(roads[0])){
                    lane.getPolyline().setStroke
                            (Paint.valueOf(MapMouseEvent.SELECTED_LANE_COLOR));
                    lanesSelected[0] = lane;
                } 
                    
                if(lane.getName().equals(roads[1])){ 
                    lane.getPolyline().setStroke
                            (Paint.valueOf(MapMouseEvent.SELECTED_LANE_COLOR_ALT));
                    lanesSelected[1] = lane;
                }
            });
            return lanesSelected;
        }else{
            return null;
        }
    }
    
    public void parseNetwork(String location) throws FileNotFoundException, 
                                                            XMLStreamException,
                                                            IOException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(location);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        reader.nextTag(); // pass the next tag
        
        int event;
        boolean internal = true;
        String tag, function;
        String edgeId = "";
        
        while(!reader.getLocalName().equals(Edge.TAG)) reader.nextTag(); // go to edges
        //HECER BIEN
        while (reader.hasNext()){
            event = reader.next();
            if(event == XMLStreamConstants.START_ELEMENT){
                tag = reader.getLocalName();
                
                if(tag.equals(Lane.TAG)){
                    
                    Lane lane = new Lane( reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.LENGTH),
                                  reader.getAttributeValue(null, Lane.SHAPE), 
                                  internal, edgeId);
                    lanes.add(lane);
                    
                    //System.out.println(lane.toString());
                }else if(tag.equals(Junction.TAG)){
                    /*junctions.put(reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.SHAPE));*/
                }else if(tag.equals(Edge.TAG)){
                    function = reader.getAttributeValue(null, Edge.FUNCTION);
                    edgeId = reader.getAttributeValue(null, Edge.ID);
                    
                    if(function != null){
                        if ("internal".equals(function)) internal = true;
                    } else {
                        internal = false;
                    }
                }
            }  
            
        }
        reader.close();
        in.close();
    }
    
    public void addRectangleSelection(){
        
        //group.getChildren().add(selectionRectangle);
        
        /*
        this.addMouseListener(new RectangularSelection(this));
        
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Platform.runLater(() -> {
                    if (SwingUtilities.isLeftMouseButton(e)){
                        selectionRectangle.setX(Math.min(e.getX(), mouseStartX));
                        selectionRectangle.setWidth(Math.abs(e.getX() - mouseStartX));
                        selectionRectangle.setY(Math.min(e.getY(), mouseStartY));
                        selectionRectangle.setHeight(Math.abs(e.getY() - mouseStartY));
                        System.out.println("andale");
                    }
                });
            }
        });
        
        /*
        Platform.runLater(() -> {
            
                group.getChildren().clear();
        
                lanes.forEach((lane) -> {
                    if (!lane.isInternal()){
                        lane.getPolyline().setOnMousePressed(null);
                        EventHandler<MouseEvent> eventHandler = (MouseEvent e) -> {
                            handler.addFunctionToLanes(lane, e);
                        };
                        
                        //Adding event Filter 
                        lane.getPolyline().addEventFilter(event, eventHandler);
                        group.getChildren().add(lane.getPolyline());
                    }else {
                        lane.getPolyline().setStroke(Paint.valueOf(MapMouseEvent.INTERNAL_LANE_COLOR));
                        group.getChildren().add(lane.getPolyline());
                    }
                });
                ZoomableScrollPane pane = new ZoomableScrollPane(group);
                this.setScene(new Scene(pane));
                this.updateUI();
                this.repaint();
            });*/
    }
    
    public void addMouseHandler(boolean rectangleSelection, MapMouseEvent handler,  
                                EventType event){
        Platform.runLater(() -> {
                group.getChildren().clear();
                
                lanes.forEach((lane) -> {
                    if (!lane.isInternal()){
                        lane.getPolyline().setOnMousePressed(null);
                        EventHandler<MouseEvent> eventHandler = (MouseEvent e) -> {
                            handler.addFunctionToLanes(lane, e);
                        };
                    
                        //Adding event Filter 
                        lane.getPolyline().addEventFilter(event, eventHandler);
                        group.getChildren().add(lane.getPolyline());
                    }else {
                        lane.getPolyline().setStroke(Paint.valueOf(MapMouseEvent.INTERNAL_LANE_COLOR));
                        group.getChildren().add(lane.getPolyline());
                    }
                });
                //group.getChildren().add(selectionRectangle);
                ZoomableScrollPane pane = new ZoomableScrollPane(group);
                if(rectangleSelection) pane.addRectangleSelection();
                this.setScene(new Scene(pane));
                this.updateUI();
                this.repaint();
            });
    }
    
    public void laneFunction(Lane lane, EventType event, MapMouseEvent handler){
        if (!lane.isInternal()){
            if (!lane.getEdge().equals(currentEdge)){
                currentEventHandler = (MouseEvent e) -> {
                    handler.addFunctionToLanes(lane,  e);
                };
            }
            lane.getPolyline().setOnMousePressed(null);
            currentEdge = lane.getEdge();
            //Adding event Filter 
            lane.getPolyline().addEventFilter(event, currentEventHandler);
            group.getChildren().add(lane.getPolyline());
        }else {
            lane.getPolyline().setStroke(Paint.valueOf(MapMouseEvent.INTERNAL_LANE_COLOR));
            group.getChildren().add(lane.getPolyline());
        }
    }
    /*
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (zoomer) {
            AffineTransform at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            g2.transform(at);
            zoomer = false;
        }
        // All drawings go here
    }*/
}
