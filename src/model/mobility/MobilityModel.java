package model.mobility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private static final String[] SUMO_CMD = {"sumo", 
                                            "-n", "", "-r", "", "--fcd-output", ""};
    
    private static final String[] TRACE_CMD = {Config.python2, 
                                        Config.sumoLocation + PYTHON_SCRIPT_NAME, 
                                        "--fcd-input", "", "--ns2mobility-output", 
                                        ""};
    
    public abstract void export(String location, String sim, String vTypes) throws IOException, InterruptedException;
    
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
    
    public static void executeProcess(Process process) throws IOException, InterruptedException{
        
        InputStream stdin = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        while ((line = br.readLine()) != null);

        process.waitFor();
        
    }
}
