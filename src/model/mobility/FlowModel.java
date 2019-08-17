package model.mobility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import model.constants.FilesExtension;
import model.constants.Netconvert;
import model.routes.Flow;

/**
 *
 * @author Neblis
 */
public class FlowModel extends MobilityModel {
    private final static String HEADER = "<routes>";
    private final static String FOOTER = "</routes>";
    private final static String ROUTES_FILE = "routes";
    
    private final static String FILE_LOCATION = "models/flow/";
    
    List<Flow> flows; 
    private int files; 

    public FlowModel(int files, HashMap<String, Flow> flows) {
        this.flows = new LinkedList();
        this.files = files;
        
        flows.values().forEach((flow) -> {
            this.flows.add(flow);
        });
    }
    public void addFlow(Flow flow){
        flows.add(flow);
    }

    @Override
    public void export(String location, String sim, String vTypes) throws IOException, InterruptedException {
        File output = new File(FILE_LOCATION + sim + FilesExtension.FCD);
        File routes = new File(FILE_LOCATION + ROUTES_FILE + FilesExtension.ROUTE);
        File project = new File(location);
        
        output.createNewFile();
        routes.createNewFile();
        project.mkdirs();
        
        PrintWriter writer = new PrintWriter(routes.getAbsoluteFile(), "UTF-8");
        writer.println(HEADER);
        for (Flow flow : flows) {
            writer.println(flow.toFile(String.valueOf(flow.getNumber())));  
        }
        writer.println(FOOTER);
        writer.close();
        
        ProcessBuilder sumo = new ProcessBuilder(sumoCommand(
                sim + FilesExtension.NETCONVERT, routes.getAbsolutePath(),
                output.getAbsolutePath(), vTypes));
        
        for (int i = 0; i < files; i++){
            File ns2 = new File(location + i + FilesExtension.NS2_MOBILITY.getExtension());
            ns2.createNewFile();
        
            ProcessBuilder trace = new ProcessBuilder(traceCommand(
                output.getAbsolutePath(), ns2.getAbsolutePath()));

            System.out.println(sumo.command().toString());
            executeProcess(sumo.start());
            executeProcess(trace.start());
        }
        
        Files.copy(Paths.get(routes.toURI()), Paths.get(project.toString(), 
                                            ROUTES_FILE + FilesExtension.ROUTE), 
                                            StandardCopyOption.REPLACE_EXISTING);
        
        Files.copy(Paths.get(vTypes), Paths.get(project.toString(), 
                                            Netconvert.VEHICLES.getCommand() + 
                                            FilesExtension.VEHICLES.getExtension()), 
                                            StandardCopyOption.REPLACE_EXISTING);
        
        Files.copy(Paths.get(sim + FilesExtension.NETCONVERT), Paths.get(project.toString(), 
                                            sim + FilesExtension.NETCONVERT), 
                                            StandardCopyOption.REPLACE_EXISTING);
    }
}
