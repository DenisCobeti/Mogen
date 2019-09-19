package view.mapsimulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
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
import javax.swing.JViewport;
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
    private Rectangle rectangle;
    
    private int mouseStartX;         
    private int mouseStartY; 
    
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private double xOffset = 0;
    private double yOffset = 0;
    
    public MapPanel(String name) throws FileNotFoundException, 
                                        XMLStreamException, IOException {
        super();
        parseNetwork(name);
        
        Platform.setImplicitExit(false);
        this.setAutoscrolls(true);
        rectangle = new Rectangle();
    }
    
    public void parseNetwork(String location) throws FileNotFoundException, 
                                                            XMLStreamException,
                                                            IOException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(location);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        reader.nextTag(); // pass the net tag
        
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
                    System.out.println(internal);
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
                ZoomableScrollPane pane = new ZoomableScrollPane(group);
                this.setScene(new Scene(pane));
                this.updateUI();
                this.repaint();
            });
    }
    

    public void addScrollListener(){
        this.setAutoscrolls(true);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                mouseStartX = e.getX();
                mouseStartY = e.getY();
                
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {}
            
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)){
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass
                                            (JViewport.class, MapPanel.this);
                    Point vpp = viewPort.getViewPosition();
                    vpp.translate(mouseStartX-e.getX(), mouseStartY-e.getY());
                    scrollRectToVisible(new Rectangle(vpp, viewPort.getSize()));
                    /*
                    if (origin != null) {
                        JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, panel);

                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        scrollRectToVisible(view);

                    }*/
                }else if(SwingUtilities.isRightMouseButton(e)){
                    /*
                    Platform.runLater(() -> {
                        group.getChildren().add(new javafx.scene.shape.Rectangle(mouseStartX, mouseStartY));
                    });*/
                }
            }
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                /*
                if (e.getWheelRotation() < 0){
                    group.setScaleX(group.getScaleX() + ZOOM_AMOUNT);
                    group.setScaleY(group.getScaleY() + ZOOM_AMOUNT);
                    group.setScaleZ(group.getScaleZ() + ZOOM_AMOUNT);
                }else {
                    group.setScaleX(group.getScaleX() - ZOOM_AMOUNT);
                    group.setScaleY(group.getScaleY() - ZOOM_AMOUNT);
                    group.setScaleZ(group.getScaleZ() - ZOOM_AMOUNT);
                }*/
                zoomer = true;
                //Zoom in
                if (e.getWheelRotation() < 0) {
                    zoomFactor *= 1.1;
                    repaint();
                }
                //Zoom out
                if (e.getWheelRotation() > 0) {
                    zoomFactor /= 1.1;
                    repaint();
                }
            }
        };
        
        this.addMouseListener(mouseAdapter);
        this.addMouseWheelListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
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
