package model.mobility;

import control.Simulation;
import java.io.BufferedReader;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;

import model.Config;
import model.constants.FilesExtension;
import model.constants.Netconvert;
import model.routes.VType;

/**
 *
 * @author Neblis
 */
public class Random implements MobilityModel{

    private final static String PYTHON_SCRIPT_NAME = "\\tools\\randomTrips.py";
    private final static String PYTHON = "python2";
    private final static String TRIPS_FILE = "trips.trips.xml";
    private final static String ROUTES_FILE = "routes.rou.xml";
    
    private final static String NETWORK_OPT = "-n";
    private final static String VEHICLE_OPT = "--additional-file";
    private final static String TIME_OPT = "-e";
    private final static String OUTPUT_OPT = "-o";
    private final static String ROUTES_OPT = "-r";
    private final static String NUM_OPT = "-n";
    private final static String ATT_OPT = "--trip-attributes";
    private final static String DIST_OPT = "type='dist1'";
    
    private final static String FILE_LOCATION = "./models/random/";
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
        command.add(sim + FilesExtension.NETCONVERT);
        command.add(VEHICLE_OPT);
        
        File vehicles = new File(FILE_LOCATION + VEHICLE_FILE);
        vehicles.createNewFile();
        
        PrintWriter writer = new PrintWriter(FILE_LOCATION + VEHICLE_FILE, "UTF-8");
        writer.println("<additional>");
        writer.println("<vTypeDistribution id=\"dist1\">");
        for (Map.Entry<String, VType> entry : vTypes.entrySet()) {
            writer.println(entry.getValue().toFile(entry.getKey()));
        }
        writer.println("</vTypeDistribution>");
        writer.println("</additional>");
        writer.close();
        
        command.add(FILE_LOCATION + VEHICLE_FILE);
        command.add(TIME_OPT);
        command.add(String.valueOf(time));
        command.add(OUTPUT_OPT);
        command.add(FILE_LOCATION + TRIPS_FILE);
        command.add(ROUTES_OPT);
        command.add(FILE_LOCATION + ROUTES_FILE);
        command.add(ATT_OPT);
        command.add(DIST_OPT);
        System.out.println(command.toString());
        
        ProcessBuilder randomTrips = new ProcessBuilder(command);
        randomTrips.start();
    }
}
