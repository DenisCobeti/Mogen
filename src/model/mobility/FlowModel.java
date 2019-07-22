package model.mobility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
    
    private final static String FILE_LOCATION = "./models/flow/";
    
    HashMap<String, Flow> flows; 

    public FlowModel() {
        flows = new HashMap();
    }
    
    public void addFlow(Flow flow){
        flows.put(String.valueOf(flows.size()), flow);
    }

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
    }
}
