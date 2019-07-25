package model.mobility;

import java.io.IOException;
import model.Config;

/**
 *
 * @author Neblis
 */
public abstract class MobilityModel {
    public enum Models {RANDOM}
    
    protected final static int SUMO_NETWORK_OPT = 2;
    protected final static int SUMO_ROUTES_OPT = 4;
    protected final static int SUMO_OUTPUT_OPT = 6;
    
    protected final static int TRACE_INPUT_OPT = 3;
    protected final static int TRACE_OUTPUT_OPT = 5;
    
    private final static String PYTHON_SCRIPT_NAME = "\\tools\\traceExporter.py";
    
    private static final String[] SUMO_CMD = {Config.sumoLocation + Config.SUMO_PROGRAM, 
                                            "-n", "", "-r", "", "--fcd-output", ""};
    
    private static final String[] TRACE_CMD = {Config.python2, 
                                        Config.sumoLocation + PYTHON_SCRIPT_NAME, 
                                        "--fcd-input", "", "--ns2mobility-output", 
                                        ""};
    
    public abstract void export(String location, String sim, String vTypes) throws IOException;
    
    public String[] sumoCommand (String network, String routes, String output){
        SUMO_CMD[SUMO_NETWORK_OPT] = network;
        SUMO_CMD[SUMO_ROUTES_OPT] = routes;
        SUMO_CMD[SUMO_OUTPUT_OPT] = output;
        
        return SUMO_CMD;
    }
    public String[] traceCommand (String fcd, String output){
        TRACE_CMD[TRACE_INPUT_OPT] = fcd;
        TRACE_CMD[TRACE_OUTPUT_OPT] = output;
        
        return TRACE_CMD;
    }
}
