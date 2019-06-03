package control.sumo;

/**
 *
 * @author Neblis
 */
public class RoadType {
    private String numLanea;
    private boolean oneway;
    private double width;

    public RoadType(String numLanea, boolean oneway, double width) {
        this.numLanea = numLanea;
        this.oneway = oneway;
        this.width = width;
    }
    
    
}
