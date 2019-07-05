package model.mobility;

import java.io.IOException;
import java.util.Map;
import model.routes.VType;

/**
 *
 * @author Neblis
 */
public abstract class MobilityModel {
    public enum Models {RANDOM}
    
    protected final static int SUMO_NETWORK_OPT = 2;
    protected final static int SUMO_ROUTES_OPT = 4;
    protected final static int SUMO_OUTPUT_OPT = 6;
    
    private static final String[] SUMO_CMD = {"sumo", "-n", "", "-r", "", 
                                                "--full-output", ""};
    
    public abstract void export(String location, String sim, Map<String, VType> vTypes) throws IOException;
    
    public String[] sumoCommand (String network, String routes, String output){
        SUMO_CMD[SUMO_NETWORK_OPT] = network;
        SUMO_CMD[SUMO_ROUTES_OPT] = routes;
        SUMO_CMD[SUMO_OUTPUT_OPT] = output;
        
        return SUMO_CMD;
    }
}
