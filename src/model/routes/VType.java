package model.routes;

/**
 *
 * @author Neblis
 */
public class VType {
    
    //Format that will appear on the XML File
    private static final String FILE_FORMAT = "<Vtype id=\"%s\" accel=\"%.2f\""
            + " decel=\"%.2f\" sigma=\"%.1f\" length=\"%d\" maxSpeed=\"%d\" />";
    
    private double accel;
    private double decel;
    private double sigma;
    
    private int length;
    private int maxSpeed;

    private static final double DEFAULT_ACCEL = 3;
    private static final double DEFAULT_DECEL = 5;
    private static final double DEFAULT_SIGMA = 0.5;
    
    private static final int DEFAULT_LENGTH = 1;
    private static final int DEFAULT_SPEED = 100;
    
    public VType() {
        this.accel = DEFAULT_ACCEL;
        this.decel = DEFAULT_DECEL;
        this.sigma = DEFAULT_SIGMA;
        
        this.length = DEFAULT_LENGTH;
        this.maxSpeed = DEFAULT_SPEED;
    }
    
    public VType(String id, double accel, double decel, double sigma, 
                    int length, int maxSpeed) {
        this.accel = accel;
        this.decel = decel;
        this.sigma = sigma;
        this.length = length;
        this.maxSpeed = maxSpeed;
    }

    public void setAccel(double accel) { this.accel = accel; }
    public void setDecel(double decel) { this.decel = decel; }
    public void setSigma(double sigma) { this.sigma = sigma; }
    public void setLength(int length) { this.length = length; }
    public void setMaxSpeed(int maxSpeed) { this.maxSpeed = maxSpeed; }

    public double getAccel() {
        return accel;
    }

    public double getDecel() {
        return decel;
    }

    public double getSigma() {
        return sigma;
    }

    public int getLength() {
        return length;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public String toFile(String id) {
        return String.format(FILE_FORMAT, id, accel, decel, sigma, length, maxSpeed);
    }
    
    
}
