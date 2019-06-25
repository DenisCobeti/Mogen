package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import model.Config;
import model.map.MapAPI;
import model.map.MapAPI.APIS;
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
    /*
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
    }*/
    
    public MapConverter(String map, APIS api) throws IOException {
        convertCommand = new LinkedList();
        convertCommand.add(Netconvert.PROGRAM.toString());
        switch(api){
            case OSM:
                // add recommended options when an OSM file is converted
                convertCommand.add(Netconvert.OSM_MAP.toString());
                convertCommand.add(map);
        }
    }
    
    public void executeConvert(String convertedMap) throws IOException{
        String fileOutput = convertedMap + FilesExtension.NETCONVERT;
        File netconvertFile = new File(fileOutput);
        addOptions(Netconvert.STREET_NAMES.getCommand());
        addOptions(Netconvert.OUTPUT.getCommand(), fileOutput);
        ProcessBuilder netconvert = new ProcessBuilder(convertCommand);
        
        netconvertFile.createNewFile();
        netconvert.start();
        System.out.println(convertCommand);
        
    }
    
    public void addOptions(String... options){
        convertCommand.addAll(Arrays.asList(options));
    }
    public void addOptions(List<String> options){
        for (String option : options)
            convertCommand.add(option);
    }
    /*
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

    }*/
    
    
}
