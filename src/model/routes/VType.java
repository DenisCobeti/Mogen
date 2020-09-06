package model.routes;

import java.util.Iterator;
import javax.xml.stream.events.Attribute;
import model.followingmodels.FollowingModel;
import model.followingmodels.FollowingModelFactory;
import model.followingmodels.Krauss;

/**
 *
 * @author Neblis
 */
public class VType {
    
    //Format that will appear on the XML File
    private static final String FILE_FORMAT = "<vType id=\"%s\" accel=\"%.2f\""
            + " decel=\"%.2f\" tau=\"%.1f\" length=\"%d\" maxSpeed=\"%d\" %s />";
    
    private static final String FILE_FORMAT_PROB = "<vType id=\"%s\" accel=\"%.2f\""
            + " decel=\"%.2f\" tau=\"%.1f\" length=\"%d\" maxSpeed=\"%d\" %s "
            + "probability=\"%.2f\" emissionClass=\"%s\" />";
    
    
    public static final String DISTRIBUTION = "dist";
    
    public static enum EmissionType{
        
        HEAVY_DIESEL("HBEFA3/HDV_D_EU4", "Heavy diesel"),
        HEAVY_GASOLINE("HBEFA3/HDV_G", "Heavy gasoline"),
        
        LIGHT_DIESEL("HBEFA3/LDV_D_EU4", "Light diesel"),
        LIGHT_GASOLINE("HBEFA3/LDV_G_EU4", "Light gasoline"),
        
        PASSANGER_DIESEL("HBEFA3/PC_D_EU4", "Passanger diesel"),
        PASSANGER_GASOLINE("HBEFA3/PC_G_EU4", "Passanger gasoline"),
        
        ELECTRIC("Energy", "Electric"),
        ZERO("Zero", "Zero emissions");
        
        private final String value;
        private final String description;
        
        EmissionType(String value, String description){
            this.value = value;
            this.description = description;
        }

        public String getValue() { return value; }
        public String getDescription() { return description; }

        public static EmissionType fromString(String value) {
            for (EmissionType type : EmissionType.values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return ZERO;
        }
        
        @Override
        public String toString() {
            return description;
        }
        
    }
    private boolean enabled = true;
    
    private double accel;
    private double decel;
    private double tau;
    
    private int length;
    private int maxSpeed;
    
    private double probability;
    
    private FollowingModel model;
    private EmissionType emission;

    private static final double DEFAULT_ACCEL = 3;
    private static final double DEFAULT_DECEL = 5;
    private static final double DEFAULT_TAU = 7;
    
    private static final int DEFAULT_LENGTH = 1;
    private static final int DEFAULT_SPEED = 50;
    
    private static final double DEFAULT_PROBABILITY = 0.50;
    
    public VType() {
        this.accel = DEFAULT_ACCEL;
        this.decel = DEFAULT_DECEL;
        this.tau = DEFAULT_TAU;
        
        
        this.length = DEFAULT_LENGTH;
        this.maxSpeed = DEFAULT_SPEED;
        
        this.probability = DEFAULT_PROBABILITY;
        
        this.model = new Krauss();
        this.emission = EmissionType.LIGHT_GASOLINE;
    }
    
    public VType(String id, double accel, double decel, double tau, 
                    int length, int maxSpeed) {
        this.accel = accel;
        this.decel = decel;
        this.tau = tau;
        this.length = length;
        this.maxSpeed = maxSpeed;
        
        this.model = new Krauss();
        this.emission = EmissionType.LIGHT_GASOLINE;
    }
    
    public VType(String id, double accel, double decel, double tau, 
                    int length, int maxSpeed, FollowingModel model) {
        this.accel = accel;
        this.decel = decel;
        this.tau = tau;
        this.length = length;
        this.maxSpeed = maxSpeed;
        this.model = model;
        
        this.emission = EmissionType.LIGHT_GASOLINE;
    }
    
    public VType(String id, double accel, double decel, double tau, 
                    int length, int maxSpeed, FollowingModel model, 
                    EmissionType emission) {
        this.accel = accel;
        this.decel = decel;
        this.tau = tau;
        this.length = length;
        this.maxSpeed = maxSpeed;
        this.model = model;
        this.emission = emission;
    }
    
    public void setAccel(double accel) { this.accel = accel; }
    public void setDecel(double decel) { this.decel = decel; }
    public void setTau(double tau) { this.tau = tau; }
    public void setLength(int length) { this.length = length; }
    public void setMaxSpeed(int maxSpeed) { this.maxSpeed = maxSpeed; }
    public void setModel(FollowingModel model) { this.model = model; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setProbability(double probability) { this.probability = probability; }
    public void setEmission(EmissionType emission) { this.emission = emission; }
    
    public void setEmission(String emission) { 
        this.setEmission(EmissionType.fromString(emission));
    }
    
    
    
    public double getAccel() { return accel; }
    public double getDecel() { return decel; }
    public double getTau() { return tau; }
    public int getLength() { return length; }
    public int getMaxSpeed() { return maxSpeed; }
    public double getProbability() { return probability; }
    
    public EmissionType getEmission() { return emission; }
    public FollowingModel getModel() { return model; }
    public boolean isEnabled() { return enabled; }
    
    public void assignFollowingModel(String modelName, Iterator<Attribute> attributes){
        FollowingModel model = FollowingModelFactory.getFollowingModel(modelName);
        
        model.importAttributes(attributes);
        this.setModel(model);
    }
    /*
    public EmissionType getType(String type){
        
        switch(type){
            case EmissionType.ELECTRIC.getValue():
                return EmissionType.ELECTRIC;
                break;
        }
        
    }*/
    /*
    public String toFile(String id) {
        return String.format(FILE_FORMAT, id, accel, decel, tau, length, 
                            maxSpeed, model.toSimulation());
    }*/
    public String toFile(String id) {
        return String.format(FILE_FORMAT_PROB, id, accel, decel, tau, length, 
                            maxSpeed, model.toSimulation(), probability, 
                            emission.getValue());
    }
    
}
