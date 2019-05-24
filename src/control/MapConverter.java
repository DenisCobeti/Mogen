package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import model.Config;
import model.MapAPI;
import model.MapAPI.APIS;
import model.constants.Errors;
import model.constants.FilesExtension;
import model.constants.Netconvert;

/**
 * Map once already converted using netconvert
 * @author Denis C
 */
public class MapConverter {
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    
    private static  List<String> convertCommand;
    /*
    public MapConverter(MapAPI api) throws IOException {
        
        InputStream in = api.getMap();
        File osmFile = new File(Config.osmMap + FilesExtension.OSM);
        osmFile.createNewFile();
        File netconvertFile = new File(Config.sumoMap + FilesExtension.NETCONVERT);
        ProcessBuilder netconvert = new ProcessBuilder(api.netconvertCommand
               (osmFile.getCanonicalPath(), netconvertFile.getCanonicalPath()));
        copyInputStreamToFile(in, osmFile);
        netconvertFile.createNewFile();
        try{
            
            netconvert.start();
            System.out.println(api.netconvertCommand
               (osmFile.getCanonicalPath(), netconvertFile.getCanonicalPath()));
        }catch(IOException e){
            C4R.handleError(e, Errors.NETCONVERT_CMD);
        }
    }*/
    
    public MapConverter(MapAPI api, String map) throws IOException {
        
        File netconvertFile = new File(Config.sumoMap + FilesExtension.NETCONVERT);
        ProcessBuilder netconvert = new ProcessBuilder(api.netconvertCommand
               (map, netconvertFile.getCanonicalPath()));
        netconvertFile.createNewFile();
        
        try{
            
            netconvert.start();
            System.out.println(api.netconvertCommand
               (map, netconvertFile.getCanonicalPath()));
        }catch(IOException e){
            C4R.handleError(e, Errors.NETCONVERT_CMD);
        }
    }
    
    public MapConverter(String map, APIS api) throws IOException {
        convertCommand = java.util.Arrays.asList(
                                Netconvert.PROGRAM.toString(), 
                                Netconvert.OSM_MAP.toString(), map);
        
        switch(api){
            case OSM:
                // add recommended options when an OSM file is converted
                
        }
    }
    
    public void executeConvert(String convertedMap) throws IOException{
        File netconvertFile = new File(Config.sumoMap + FilesExtension.NETCONVERT);
        ProcessBuilder netconvert = new ProcessBuilder(convertCommand);
        try{
            netconvertFile.createNewFile();
            netconvert.start();
            System.out.println(convertCommand);
        }catch(IOException e){
            C4R.handleError(e, Errors.NETCONVERT_CMD);
        }
    }
    public void addOption(String option){
        
    }
    //esto esta copiado, habra que cambiarlo
    public static void copyInputStreamToFile(InputStream inputStream, File file) 
		throws IOException {
        file.createNewFile();
        
        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
    
    
}
