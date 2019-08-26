package view.jxmapviewer2;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import model.map.MapSelection;

import org.jxmapviewer.JXMapViewer;

/**
 * Creates a selection rectangle based on mouse input
 * Also triggers repaint events in the viewer
 * @author Martin Steiger
 */
public class SelectionAdapter extends MouseAdapter {
    
    private boolean dragging;
    private JXMapViewer viewer;
    private MapSelect selecter;

    private Point2D startPos = new Point2D.Double();
    private Point2D endPos = new Point2D.Double();

    /**
     * @param viewer the jxmapviewer
     */
    public SelectionAdapter(JXMapViewer viewer, MapSelect selecter){
        this.viewer = viewer;
        this.selecter = selecter;
    }

    @Override
    public void mousePressed(MouseEvent e){
        
        if (e.getButton() == MouseEvent.BUTTON1){
            dragging = false;
            viewer.repaint();
            return;
        }
        
        if (e.getButton() != MouseEvent.BUTTON3) return;
        startPos.setLocation(e.getX(), e.getY());
        endPos.setLocation(e.getX(), e.getY());

        dragging = true;
    }

    @Override
    public void mouseDragged(MouseEvent e){
        
        if (!dragging)
            return;
        
        endPos.setLocation(e.getX(), e.getY());
        selecter.updateSelection(getGeoCoordinates(viewer).maxLon, 
                                    getGeoCoordinates(viewer).minLon, 
                                    getGeoCoordinates(viewer).maxLat, 
                                    getGeoCoordinates(viewer).minLat);
        viewer.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e){
        
        if (!dragging)
            return;

        if (e.getButton() == MouseEvent.BUTTON3){
            dragging = false;
            
            return;
        }
        viewer.repaint();
 
    }

    /**
     * @return the selection rectangle
     */
    public Rectangle getRectangle(){
        
        if (dragging){
            
            int x1 = (int) Math.min(startPos.getX(), endPos.getX());
            int y1 = (int) Math.min(startPos.getY(), endPos.getY());
            int x2 = (int) Math.max(startPos.getX(), endPos.getX());
            int y2 = (int) Math.max(startPos.getY(), endPos.getY());
            return new Rectangle(x1, y1, x2-x1, y2-y1);
        }

        return null;
    }
    
    public MapSelection getGeoCoordinates(JXMapViewer map){
        /*
        return new GeoPosition[]{
                    map.convertPointToGeoPosition(startPos),
                    map.convertPointToGeoPosition(endPos)
        };*/
        return new MapSelection(map.convertPointToGeoPosition(startPos),
                                map.convertPointToGeoPosition(endPos));
    }

}
