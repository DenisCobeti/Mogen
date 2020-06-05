package model.mobility;

import control.MogenControl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import model.constants.FilesExtension;
import model.routes.ODElement;
import model.routes.TAZ;

/**
 *
 * @author darkm
 */
public class ODModel extends MobilityModel {

    private final static String HEADER_TAZ = "<tazs>";
    private final static String FOOTER_TAZ = "</tazs>";
    private final static String FILE_LOCATION = "models/od/";
    
    private final static String TAZ_FILE = "tazs.taz.xml";
    
    
    private Map<String, TAZ> tazs;
    private ArrayList<ODElement> elements;
    
    private double begin;
    
    public ODModel(double time, Map<String, TAZ> tazs) {
        tazs = new HashMap();
        
        this.begin = time;
        this.tazs = tazs;
    }

    public void setTazs(HashMap<String, TAZ> tazs) {
        this.tazs = tazs;
    }

    public void setBegin(double begin) {
        this.begin = begin;
    }
    
    public boolean addTazs(String id, TAZ taz){
        if (tazs.containsKey(id)) return false;
        tazs.put(id, taz);
        return true;
    }
    
    public boolean removeTazs(String id){
        if (tazs.containsKey(id)) return false;
        return true;
    }
    
    public boolean addElement(String origin, String destination, int vehicleNum){
        ODElement element = new ODElement(origin, destination, vehicleNum);
        if (elements.contains(element)) return false;
        elements.add(element);
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
        File project = new File(location + FilesExtension.SUMO_CFG);
        File projectFolder = new File(location);
        
        control.startExport(0);
        
        projectFolder.mkdirs();
        project.createNewFile();
        output.createNewFile();
        tazsFile.createNewFile();
        
        PrintWriter writer = new PrintWriter(tazsFile.getAbsoluteFile(), "UTF-8");
        
        writer.println(HEADER_TAZ);
        tazs.entrySet().forEach((entry) -> {
            //writer.println(entry.getValue().toFile(entry.getKey()));
            writer.println(entry.getValue().toFile(entry.getKey()));
        });
        writer.println(FOOTER_TAZ);
        
        writer.close();
    }
    
}
