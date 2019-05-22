package model.sumo;

import java.util.HashMap;

/**
 *
 * @author Neblis
 */
public class SumoNetwork {
    private HashMap<String, Edge> edges = new HashMap<>();
    private HashMap<String, Connection> connections = new HashMap<>();
    private HashMap<String, Junction> junctions = new HashMap<>();
    private HashMap<String, RoadType> roadTypes = new HashMap<>();
    
    private String mapLocation;

    public SumoNetwork(String mapLocation) {
        this.mapLocation = mapLocation;
    }
    
    private void parseNetwork(String location){
        
    }
}
