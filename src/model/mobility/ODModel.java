package model.mobility;

import control.Mogen;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    
    
    private final static String O_FORMAT_TYPE = "$OR;D2";
    private final static String V_FORMAT_TYPE = "$VMR";
    
    private final static String OD_ELEMENT_FACTOR = "1.00";
    
    private final static String TAZ_FILE = "tazs.taz.xml";
    private final static String OD_FILE = "od.taz.or";
    
    
    private Map<String, TAZ> tazs;
    private final ArrayList<ODElement> elements;
    private final File inputFiles = new File(FILE_LOCATION); 
    
    private int begin;
    
    public ODModel(int time, Map<String, TAZ> tazs) {
        this.elements = new ArrayList();
        
        if (!inputFiles.exists()) inputFiles.mkdirs();
        
        this.begin = time;
        this.tazs = tazs;
    }

    public void setTazs(HashMap<String, TAZ> tazs) {
        this.tazs = tazs;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }
    
    public boolean addTazs(String id, TAZ taz){
        if (tazs.containsKey(id)) return false;
        tazs.put(id, taz);
        return true;
    }
    
    public boolean removeTazs(String id){
        return !tazs.containsKey(id);
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
                Mogen control) throws IOException, InterruptedException {
        File output = new File(FILE_LOCATION + sim + FilesExtension.FCD);
        File tazsFile = new File(FILE_LOCATION + TAZ_FILE + FilesExtension.TAZ);
        File odFile = new File(FILE_LOCATION + OD_FILE + FilesExtension.TAZ);
        File project = new File(location + FilesExtension.SUMO_CFG);
        File projectFolder = new File(location);
        
        control.startExport(0);
        
      
        projectFolder.mkdirs();
        project.createNewFile();
        output.createNewFile();
        tazsFile.createNewFile();
        odFile.createNewFile();
        
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
        
        
        writerOD.println(O_FORMAT_TYPE);
        ////pasar a horas antes de exportar (por hacer)
        writerOD.println(begin);
        writerOD.println(OD_ELEMENT_FACTOR);
        
        elements.forEach((ODElement) -> {
            writerOD.println(ODElement.toFile());
        });
        
        writerOD.close();
        
    }
    
}
