package model.routes;

/**
 *
 * @author Neblis
 */
public class VType {
    
    //Format that will appear on the XML File
    private static final String FILE_FORMAT = "<Vtype id=\"%i\" accel=\"%.2f\""
            + " decel=\"%.2f\" sigma=\"%.1f\" length=\"%d\" maxSpeed=\"%d\" />";
    
    private int id;
    private double accel;
    private double decel;
    private double sigma;
    
    private int length;
    private int speed;

    public VType(int id, double accel, double decel, double sigma, 
                    int length, int speed) {
        this.id = id;
        this.accel = accel;
        this.decel = decel;
        this.sigma = sigma;
        this.length = length;
        this.speed = speed;
    }
    
    
}
