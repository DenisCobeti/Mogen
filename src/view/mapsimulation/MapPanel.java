package view.mapsimulation;

import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javax.swing.SwingUtilities;

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
    private final static double ZOOM_AMOUNT = 0.25;
    private final Rectangle selectionRectangle;
    
    private double mouseStartX;         
    private double mouseStartY; 
    
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private double xOffset = 0;
    private double yOffset = 0;
    
    
    public MapPanel(String name) throws FileNotFoundException, 
                                        XMLStreamException, IOException {
        super();
        parseNetwork(name);
        
        Platform.setImplicitExit(false);
        this.setAutoscrolls(true);
        selectionRectangle = new Rectangle();
        selectionRectangle.setStroke(Color.BLACK);
        selectionRectangle.setFill(Color.TRANSPARENT);
        selectionRectangle.getStrokeDashArray().addAll(5.0, 5.0);
        
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
                                  internal);
                    lanes.add(lane);
                    
                    //System.out.println(lane.toString());
                }else if(tag.equals(Junction.TAG)){
                    /*junctions.put(reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.SHAPE));*/
                }else if(tag.equals(Edge.TAG)){
                    function = reader.getAttributeValue(null, Edge.FUNCTION);
                    
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
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON1){
                    mouseStartX = (MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX());
                    mouseStartY = (MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY());
                    selectionRectangle.setX(mouseStartX);
                    selectionRectangle.setY(mouseStartY);
                    selectionRectangle.setWidth(0);
                    selectionRectangle.setHeight(0);
                    System.out.println("dale");
                }
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON1){
                    /*
                    mouseStartX = e.getX();
                    mouseStartY = e.getY();
                    selectionRectangle.setX(mouseStartX);
                    selectionRectangle.setY(mouseStartY);
                    selectionRectangle.setWidth(0);
                    selectionRectangle.setHeight(0);*/
                    System.out.println("daliendo");
                }
            }
            
        });
        
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
    
    public void addMouseHandler(MapMouseEvent handler, EventType event){
        
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
                group.getChildren().add(selectionRectangle);
                ZoomableScrollPane pane = new ZoomableScrollPane(group);
                this.setScene(new Scene(pane));
                this.updateUI();
                this.repaint();
            });
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
