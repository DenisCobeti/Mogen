package model.mobility;

import static control.MogenControl.DEFAULT_VTYPE_LOCATION;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.constants.FilesExtension;
import model.routes.Flow;
import model.routes.VType;

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

    public FlowModel(List flows) {
        this.flows = flows;
    }
    
    public void addFlow(Flow flow){
        flows.add(flow);
    }

    /*
    @Override
    public void export(String location, String sim, Map<String, VType> vTypes) throws IOException {
        File output = new File(location + sim + FilesExtension.OSM);
        File routes = new File(FILE_LOCATION + sim + FilesExtension.ROUTE);
        
        output.createNewFile();
        routes.createNewFile();
        
        PrintWriter writer = new PrintWriter(routes.getAbsoluteFile());
        
        writer.println(HEADER);
        for (Map.Entry<String, Flow> entry : flows.entrySet()) {
            //writer.println(entry.getValue().toFile(entry.getKey()));
            writer.println(entry.getValue().toFile(entry.getKey()));
        }
        writer.println(FOOTER);
        writer.close();
    }*/

    @Override
    public void export(String location, String sim, String vTypes) throws IOException {
        File output = new File(FILE_LOCATION + sim + FilesExtension.FCD);
        File routes = new File(FILE_LOCATION + ROUTES_FILE + FilesExtension.ROUTE);
        output.createNewFile();
        routes.createNewFile();
        
        PrintWriter writer = new PrintWriter(routes.getAbsoluteFile(), "UTF-8");
        writer.println(HEADER);
        for (Flow flow : flows) {
            writer.println(flow.toFile(String.valueOf(flow.getNumber())));  
        }
        writer.println(FOOTER);
        writer.close();
    }
}
