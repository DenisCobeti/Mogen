package model.topology;

/**
 *
 * @author Neblis
 */
public class Edge {
    private final double length;
    private final String shape;
    
    public final static String TAG = "edge";
    public final static String LANE = "lane";
    public final static String LENGTH = "length";
    public final static String SHAPE = "shape";

    public Edge(double length, String shape) {
        this.length = length;
        this.shape = shape;
    }
    public Edge(String length, String shape) {
        this.length = Double.valueOf(length);
        this.shape = shape;
    }
}
