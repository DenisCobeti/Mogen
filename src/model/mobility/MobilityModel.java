package model.mobility;

import control.Simulation;
import java.io.IOException;
import java.util.Map;
import model.routes.VType;

/**
 *
 * @author Neblis
 */
public interface MobilityModel {
    public enum Models {RANDOM}
    
    public void export(String location, String sim, Map<String, VType> vTypes) throws IOException;
}
