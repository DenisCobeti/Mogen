package model;

import java.awt.geom.Point2D;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author Neblis
 */
public class MapSelection {
    
    public double minLat, maxLat, minLon, maxLon ;

    public MapSelection(double minLon, double maxLon, double minLat, double maxLat) {
        this.minLon = minLon;
        this.maxLon = maxLon;
        this.minLat = minLat;
        this.maxLat = maxLat;
    }

    //latitud es el primero
    public MapSelection(Point2D point1, Point2D point2) {
        
        orderPoints (point1.getX(), point2.getX(), 
                    point1.getY(), point2.getY());
    }
    
    public MapSelection(GeoPosition point1, GeoPosition point2) {
        
        orderPoints (point1.getLatitude(), point2.getLatitude(), 
                    point1.getLongitude(), point2.getLongitude());
    }
    
    private void orderPoints(double x1, double x2, double y1, double y2){
        
        if(x1 < x2){
            minLat = x1;
            maxLat = x2;
        }else{
            minLat = x2;
            maxLat = x1;
        }
        
        if(y1 < y2){
            minLon = y1;
            maxLon = y2;
        }else{
            minLon = y2;
            maxLon = y1;
        }
    }
    
    
    
    @Override
    public String toString() {
        return "MapSelection{" + "minLat=" + minLat + ", maxLat=" + maxLat + 
                ", minLon=" + minLon + ", maxLon=" + maxLon + '}';
    }
    
    
    
}
