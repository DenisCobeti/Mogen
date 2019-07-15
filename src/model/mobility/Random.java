package model.mobility;

import control.Simulation;
import java.io.BufferedReader;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import model.Config;
import model.constants.FilesExtension;
import model.constants.Netconvert;
import model.routes.VType;

/**
 *
 * @author Neblis
 */
public class Random extends MobilityModel{

    private final static String PYTHON_SCRIPT_NAME = "\\tools\\randomTrips.py";
    private final static String PYTHON = "python2";
    private final static String TRIPS_FILE = "trips.trips.xml";
    private final static String ROUTES_FILE = "routes.rou.xml";
    
    private final static String NETWORK_OPT = "-n";
    private final static String VEHICLE_OPT = "--additional-file";
    private final static String TIME_OPT = "-e";
    private final static String OUTPUT_OPT = "-o";
    private final static String ROUTES_OPT = "-r";
    private final static String ATT_OPT = "--trip-attributes";
    private final static String DIST_OPT = "type='dist1'";
    
    private final static String FILE_LOCATION = "./models/random/";
    private final static String VEHICLE_FILE = "vehicles.add.xml";
    
    private final int repetition;
    private final int time;
    
    HashMap<String, Double> vTypesProbability;
    
    
    public Random(int repetition, int time, HashMap<String, Double> vTypesProbability) {
        this.repetition = repetition;
        this.time = time;
        this.vTypesProbability = vTypesProbability;
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
        File output = new File(FILE_LOCATION + sim + FilesExtension.OSM);
        
        vehicles.createNewFile();
        output.createNewFile();
        
        PrintWriter writer = new PrintWriter(FILE_LOCATION + VEHICLE_FILE, "UTF-8");
        writer.println("<additional>");
        writer.println("<vTypeDistribution id=\"dist1\">");
        for (Map.Entry<String, VType> entry : vTypes.entrySet()) {
            //writer.println(entry.getValue().toFile(entry.getKey()));
            if(entry.getValue().isEnabled()){
                writer.println(entry.getValue().toFile(entry.getKey(), 
                        vTypesProbability.get(entry.getKey())));
            }
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
        ProcessBuilder sumo = new ProcessBuilder(sumoCommand(
                sim + FilesExtension.NETCONVERT, FILE_LOCATION + ROUTES_FILE, 
                FILE_LOCATION + sim + FilesExtension.OSM));
        sumo.start();
    }
}
