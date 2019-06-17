package model.mobility;

import control.Simulation;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import model.Config;
import model.constants.Netconvert;
import model.routes.VType;

/**
 *
 * @author Neblis
 */
public class Random implements MobilityModel{

    private final static String PYTHON_SCRIPT_NAME = "\\tools\\randomTrips.py";
    private final static String PYTHON = "python2";
    
    private final static String NETWORK_OPT = "-n";
    private final static String VEHICLES_OPT = "-n";
    
    private final static String FILE_LOCATION= "./simulations/random/";
    private final static String VEHICLE_FILE = "vehicles.add.xml";
    
    private final int number;
    private final int time;
    
    public Random(int number, int time) {
        this.number = number;
        this.time = time;
    }
    
    
    @Override
    public  void export(String location, String sim, Map<String, VType> vTypes) 
                throws IOException{
        System.out.println(location + " " + sim + " "+ vTypes.toString());
        LinkedList <String> command = new LinkedList();
        command.add(PYTHON);
        command.add(Config.SUMO_LOCATION + PYTHON_SCRIPT_NAME);
        command.add(NETWORK_OPT);
        
        File vehicles = new File(VEHICLE_FILE);
        vehicles.createNewFile();
        
        PrintWriter writer = new PrintWriter(VEHICLE_FILE, "UTF-8");
        writer.println("<additional>");
        for (Map.Entry<String, VType> entry : vTypes.entrySet()) {
            writer.println(entry.getValue().toFile(entry.getKey()));
        }
        writer.println("</additional>");
        writer.close();
    }
    
}
