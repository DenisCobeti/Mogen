package model.sumo;

/**
 *
 * @author Neblis
 */
public class Lane {
    private final double length;
    private final String shape;
    
    public final static String TAG = "lane";
    public final static String LENGTH = "length";
    public final static String SHAPE = "shape";

    public Lane(String length, String shape) {
        this.length = Double.valueOf(length);
        this.shape = shape;
    }
    
}
