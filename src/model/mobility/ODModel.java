package model.mobility;

import control.MogenControl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import model.Config;
import model.constants.FilesExtension;
import model.constants.Netconvert;
import model.routes.ODElement;
import model.routes.TAZ;
import model.routes.VType;

/**
 *
 * @author darkm
 */
public class ODModel extends MobilityModel {

    private final static String HEADER_TAZ = "<tazs>";
    private final static String FOOTER_TAZ = "</tazs>";
    private final static String FILE_LOCATION = "models/od/";
    
    public enum Format {
        OType("$OR"), VType("$VMR"), None("");
        
        private final String start;
        
        private Format(String start){
            this.start = start;
        }
        
        @Override
        public String toString(){
            return start;
        }
    };
    
    
    private final static String OD_ELEMENT_FACTOR = "1.00";
    
    private final static String TAZ_FILE = "tazs";
    private final static String OD_FILE = "od";
    private final static String TRIPS_FILE = "trips";
    private final static String FLOW_FILE = "flows";
    
    private final static String TAZ_OPT = "-n";
    private final static String OD_OPT = "-d";
    private final static String OUTPUT_OPT = "-o";
    private final static String TYPE_OPT = "--vtype";
    private final static String FLOW_OPT = "--flow-output";
    
    private Map<String, TAZ> tazs;
    private final Map<String, ODElement> elements;
    private final File inputFiles = new File(FILE_LOCATION); 
    
    private int time;
    
    public ODModel(int time, Map<String, TAZ> tazs, Map<String, ODElement> elements) {
        
        if (!inputFiles.exists()) inputFiles.mkdirs();
        
        this.time = time;
        this.tazs = tazs;
        this.elements = elements;
    }

    public void setTazs(HashMap<String, TAZ> tazs) {
        this.tazs = tazs;
    }

    public void setBegin(int begin) {
        this.time = begin;
    }
    
    public boolean addTazs(String id, TAZ taz){
        if (tazs.containsKey(id)) return false;
        tazs.put(id, taz);
        return true;
    }
    
    public boolean removeTazs(String id){
        return !tazs.containsKey(id);
    }
    
    public boolean addElement(String id, String origin, String destination, int vehicleNum){
        ODElement element = new ODElement(origin, destination, vehicleNum);
        if (elements.containsKey(id)) return false;
        elements.put(id, element);
        return true;
    }
    
    public boolean importFile(String file){
        return true;
    }
    
    @Override
    public void export(String location, String sim, String vTypes, 
                MogenControl control) throws IOException, InterruptedException {
        File output = new File(FILE_LOCATION + sim + FilesExtension.FCD);
        File tazsFile = new File(FILE_LOCATION + TAZ_FILE + FilesExtension.TAZ);
        File flowsFile = new File(FILE_LOCATION + FLOW_FILE + FilesExtension.ROUTE);
        File odFile = new File(FILE_LOCATION + OD_FILE + FilesExtension.OD);
        File od2TripsFile = new File(FILE_LOCATION + TRIPS_FILE + FilesExtension.TRIPS);
        File project = new File(location + FilesExtension.SUMO_CFG);
        File projectFolder = new File(location);
        
        control.startExport(0);
        
      
        projectFolder.mkdirs();
        project.createNewFile();
        output.createNewFile();
        flowsFile.createNewFile();
        tazsFile.createNewFile();
        odFile.createNewFile();
        od2TripsFile.createNewFile();
        
        PrintWriter writer = new PrintWriter(tazsFile.getAbsoluteFile(), "UTF-8");
        
        writer.println(HEADER_TAZ);
        System.out.println(tazs.toString());
        tazs.entrySet().forEach((entry) -> {
            //writer.println(entry.getValue().toFile(entry.getKey()));
            writer.println(entry.getValue().toFile(entry.getKey()));
        });
        writer.println(FOOTER_TAZ);
        
        writer.close();
        
        PrintWriter writerOD = new PrintWriter(odFile.getAbsoluteFile(), "UTF-8");
        //convert to hours before exporting
        int hours = time / 3600;
        int minutes = time % 3600;
        
        writerOD.println(Format.OType.toString());
        
        writerOD.println("0.0 " + hours + "." + minutes);
        writerOD.println(OD_ELEMENT_FACTOR);
        
        elements.forEach((id, ODelement) -> {
            writerOD.println(ODelement.toFile());
        });
        
        writerOD.close();
        
        LinkedList <String> command = new LinkedList(Arrays.asList(
                Config.sumoLocation + Config.OD2TRIPS_PROGRAM,
                TAZ_OPT,
                tazsFile.getAbsolutePath(),
                OD_OPT,
                odFile.getAbsolutePath(),
                TYPE_OPT,
                VType.DISTRIBUTION,
                OUTPUT_OPT,
                od2TripsFile.getAbsolutePath()
        ));
        
        ProcessBuilder sumo = new ProcessBuilder(sumoCommand(
                sim + FilesExtension.NETCONVERT, od2TripsFile.getAbsolutePath(), 
                output.getAbsolutePath(), vTypes + "," + tazsFile.getAbsolutePath()));
        
        ProcessBuilder od2Trips = new ProcessBuilder(command);
        System.out.println(od2Trips.command().toString());
        System.out.println(sumo.command().toString());
        executeProcess(od2Trips.start());
        executeProcess(sumo.start());
        
        File ns2 = new File(location + FilesExtension.NS2_MOBILITY.getExtension());
        ns2.createNewFile();
        
        ProcessBuilder trace = new ProcessBuilder(traceCommand(
                output.getAbsolutePath(), ns2.getAbsolutePath()));
        
        executeProcess(trace.start());
        
        control.endExport();
        
        Files.copy(Paths.get(od2TripsFile.getAbsolutePath()), Paths.get(projectFolder.toString(), 
                                            od2TripsFile.getName()), StandardCopyOption.REPLACE_EXISTING);
        
        Files.copy(Paths.get(sim + FilesExtension.NETCONVERT), Paths.get(projectFolder.toString(), 
                                            sim + FilesExtension.NETCONVERT), 
                                            StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Paths.get(vTypes), Paths.get(projectFolder.toString(), 
                                            Netconvert.VEHICLES.getCommand() + 
                                            FilesExtension.VEHICLES.getExtension()), 
                                            StandardCopyOption.REPLACE_EXISTING);
        
        PrintWriter writer2 = new PrintWriter(project.getAbsoluteFile(), "UTF-8");
        writer2.println("<configuration>");
        writer2.println("<input>");
        writer2.println("<net-file value=\"" +Paths.get(projectFolder.toString(),sim + FilesExtension.NETCONVERT)+"\"/>");
        writer2.println("<route-files value=\"" + Paths.get(projectFolder.toString(),od2TripsFile.getName())+"\"/>");
        writer2.println("<additional-files value=\"" + Paths.get(projectFolder.toString(), 
                                            Netconvert.VEHICLES.getCommand() + 
                                            FilesExtension.VEHICLES.getExtension())+"\"/>");
        
        writer2.println("</input>");
        writer2.println("</configuration>");
        writer2.close();
        /*
        ProcessBuilder sumo = new ProcessBuilder(sumoCommand(
                sim + FilesExtension.NETCONVERT, routes.getAbsolutePath(),
                output.getAbsolutePath(), vTypes));
        
        
        PrintWriter writer2 = new PrintWriter(project.getAbsoluteFile(), "UTF-8");
        writer2.println("<configuration>");
        writer2.println("<input>");
        writer2.println("<net-file value=\"" +Paths.get(projectFolder.toString(),sim + FilesExtension.NETCONVERT)+"\"/>");
        writer2.println("<route-files value=\"" + Paths.get(projectFolder.toString(),ROUTES_FILE)+"\"/>");
        writer2.println("<additional-files value=\"" + Paths.get(projectFolder.toString(), 
                                            Netconvert.VEHICLES.getCommand() + 
                                            FilesExtension.VEHICLES.getExtension())+"\"/>");
        
        writer2.println("</input>");
        writer2.println("</configuration>");
        writer2.close();*/
        
    }
    
}
