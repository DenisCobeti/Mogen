package model.routes;

import model.followingmodels.FollowingModel;
import model.followingmodels.Krauss;

/**
 *
 * @author Neblis
 */
public class VType {
    
    //Format that will appear on the XML File
    private static final String FILE_FORMAT = "<Vtype id=\"%s\" accel=\"%.2f\""
            + " decel=\"%.2f\" tau=\"%.1f\" length=\"%d\" maxSpeed=\"%d\" %s />";
    
    private double accel;
    private double decel;
    private double tau;
    
    private int length;
    private int maxSpeed;
    
    private FollowingModel model;

    private static final double DEFAULT_ACCEL = 3;
    private static final double DEFAULT_DECEL = 5;
    private static final double DEFAULT_TAU = 7;
    
    private static final int DEFAULT_LENGTH = 1;
    private static final int DEFAULT_SPEED = 50;
    
    public VType() {
        this.accel = DEFAULT_ACCEL;
        this.decel = DEFAULT_DECEL;
        this.tau = DEFAULT_TAU;
        
        this.length = DEFAULT_LENGTH;
        this.maxSpeed = DEFAULT_SPEED;
        this.model = new Krauss(0.5, 1);
    }
    
    public VType(String id, double accel, double decel, double tau, 
                    int length, int maxSpeed) {
        this.accel = accel;
        this.decel = decel;
        this.tau = tau;
        this.length = length;
        this.maxSpeed = maxSpeed;
    }
    
    public VType(String id, double accel, double decel, double tau, 
                    int length, int maxSpeed, FollowingModel model) {
        this.accel = accel;
        this.decel = decel;
        this.tau = tau;
        this.length = length;
        this.maxSpeed = maxSpeed;
        this.model = model;
    }
    public void setAccel(double accel) { this.accel = accel; }
    public void setDecel(double decel) { this.decel = decel; }
    public void setTau(double tau) { this.tau = tau; }
    public void setLength(int length) { this.length = length; }
    public void setMaxSpeed(int maxSpeed) { this.maxSpeed = maxSpeed; }

    public double getAccel() { return accel; }

    public double getDecel() { return decel; }

    public double getTau() { return tau; }

    public int getLength() { return length; }

    public int getMaxSpeed() { return maxSpeed; }

    public String toFile(String id) {
        return String.format(FILE_FORMAT, id, accel, decel, tau, length, 
                            maxSpeed, model.toSimulation());
    }
    
    
}
