package model.mobility;

import control.MogenControl;
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
    private final int files; 
    private final File inputFiles = new File(FILE_LOCATION);

    public FlowModel(int files, HashMap<String, Flow> flows) {
        this.flows = new LinkedList();
        this.files = files;
        
        if (!inputFiles.exists()) inputFiles.mkdirs();
        
        flows.values().forEach((flow) -> {
            this.flows.add(flow);
        });
    }
    
    public void addFlow(Flow flow){
        flows.add(flow);
    }

    @Override
    public void export(String location, String sim, String vTypes, MogenControl control) throws IOException, InterruptedException {
        File output = new File(FILE_LOCATION + sim + FilesExtension.FCD);
        File routes = new File(FILE_LOCATION + ROUTES_FILE + FilesExtension.ROUTE);
        File project = new File(location + FilesExtension.SUMO_CFG);
        File projectFolder = new File(location);
        
        control.startExport(files);
        
        projectFolder.mkdirs();
        project.createNewFile();
        output.createNewFile();
        routes.createNewFile();
        
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
            control.progressExport(files);
            File ns2 = new File(location + i + FilesExtension.NS2_MOBILITY.getExtension());
            ns2.createNewFile();
        
            ProcessBuilder trace = new ProcessBuilder(traceCommand(
                output.getAbsolutePath(), ns2.getAbsolutePath()));

            System.out.println(sumo.command().toString());
            executeProcess(sumo.start());
            executeProcess(trace.start());
        }
        
        Files.copy(Paths.get(FILE_LOCATION + ROUTES_FILE + FilesExtension.ROUTE), Paths.get(projectFolder.toString(), 
                                            ROUTES_FILE), StandardCopyOption.REPLACE_EXISTING);
        
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
        writer2.println("<route-files value=\"" + Paths.get(projectFolder.toString(),ROUTES_FILE)+"\"/>");
        writer2.println("<additional-files value=\"" + Paths.get(projectFolder.toString(), 
                                            Netconvert.VEHICLES.getCommand() + 
                                            FilesExtension.VEHICLES.getExtension())+"\"/>");
        
        writer2.println("</input>");
        writer2.println("</configuration>");
        writer2.close();
    }
}
