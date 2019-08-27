package model.mobility;

import control.MogenControl;
import java.io.File;
import java.util.LinkedList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import model.Config;
import model.constants.FilesExtension;
import model.constants.Netconvert;
import model.routes.VType;
import view.MogenView;

/**
 *
 * @author Neblis
 */
public class RandomModel extends MobilityModel{

    private final static String PYTHON_SCRIPT_NAME = "\\tools\\randomTrips.py";
    private final static String TRIPS_FILE = "trips.trips.xml";
    private final static String ROUTES_FILE = "routes.rou.xml";
    
    private final static String NETWORK_OPT = "-n";
    private final static String VEHICLE_OPT = "--additional-file";
    private final static String TIME_OPT = "-e";
    private final static String REPETITION_OPT = "-p";
    private final static String OUTPUT_OPT = "-o";
    private final static String ROUTES_OPT = "-r";
    
    private final static String ATT_OPT = "--trip-attributes";
    private final static String DIST_OPT = "type='" + VType.DISTRIBUTION + "'";
    private final static String START_OPT = "depart='0'";
    
    private final static String FILE_LOCATION = "models/random/";
    private final static String VEHICLE_FILE = "vehicles.add.xml";
    private final static int DEFAULT_TIME = 1;
    
    private final int vehicles;
    private final int time;
    private final int files;
    
    public RandomModel(int time, int files, int vehicles) {
        this.vehicles = vehicles;
        this.files = files;
        this.time = time;
    }
    
    /*
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
                writer.println(entry.getValue().toFile(entry.getKey()));
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
    }*/
    @Override
    public void export(String location, String sim, String vTypes, MogenControl control) throws IOException, InterruptedException {
        
        File output = new File(FILE_LOCATION + sim + FilesExtension.FCD);
        File project = new File(location);
        double repetition = DEFAULT_TIME / vehicles;
        
        project.mkdirs();
        output.createNewFile();
        control.startExport(files);
        
        LinkedList <String> command = new LinkedList(Arrays.asList(
                Config.python2,
                Config.sumoLocation + PYTHON_SCRIPT_NAME,
                NETWORK_OPT,
                sim + FilesExtension.NETCONVERT,
                VEHICLE_OPT,
                vTypes,
                TIME_OPT,
                String.valueOf(DEFAULT_TIME),
                REPETITION_OPT,
                String.valueOf(repetition),
                OUTPUT_OPT,
                FILE_LOCATION + TRIPS_FILE,
                ROUTES_OPT,
                FILE_LOCATION + ROUTES_FILE,
                ATT_OPT,
                DIST_OPT 
        ));
        
        System.out.println(command.toString());

        ProcessBuilder randomTrips = new ProcessBuilder(command);
        ProcessBuilder sumo = new ProcessBuilder(sumoCommand(
                sim + FilesExtension.NETCONVERT, FILE_LOCATION + ROUTES_FILE,
                output.getAbsolutePath()));
        
        System.out.println(sumo.command().toString());
        
        for (int i = 0; i < files; i++){
            File ns2 = new File(location + i + FilesExtension.NS2_MOBILITY.getExtension());
            ns2.createNewFile();
            control.progressExport(files);
            
            executeProcess(randomTrips.start());
            executeProcess(sumo.start());
            
            ProcessBuilder trace = new ProcessBuilder(traceCommand(
            output.getAbsolutePath(), ns2.getAbsolutePath()));
            System.out.println(trace.command().toString());

            executeProcess(trace.start());
        }
        
        Files.copy(Paths.get(vTypes), Paths.get(project.toString(), 
                                            Netconvert.VEHICLES.getCommand() + 
                                            FilesExtension.VEHICLES.getExtension()), 
                                            StandardCopyOption.REPLACE_EXISTING);
        
        Files.copy(Paths.get(FILE_LOCATION + ROUTES_FILE), Paths.get(project.toString(), 
                                            ROUTES_FILE), StandardCopyOption.REPLACE_EXISTING);
        
        Files.copy(Paths.get(sim + FilesExtension.NETCONVERT), Paths.get(project.toString(), 
                                            sim + FilesExtension.NETCONVERT), 
                                            StandardCopyOption.REPLACE_EXISTING);
        
    }
    
    private static double vehiclesToRepetition (int startTime, int endTime, int numVehicles){
        return ((endTime - startTime) / numVehicles);
    }
}
