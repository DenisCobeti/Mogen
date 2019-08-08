package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import model.map.MapAPI.APIS;
import model.constants.FilesExtension;
import model.constants.Netconvert;
import model.constants.RoadTypes;

/**
 * Map once already converted using netconvert
 * @author Denis C
 */
public class MapConverter {
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    private static  List<String> convertCommand;
    
    private static  List<RoadTypes> filterRoads;
    private static  List<Netconvert> options;
    
    private final static String[] DEFAULT_OSM_OPTIONS = new String[] {
                    Netconvert.JOIN_JUNCTIONS.getCommand(),
                    Netconvert.GUESS_ROUNDABOUTS.getCommand() };
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
    }/*
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
                convertCommand.addAll(Arrays.asList(DEFAULT_OSM_OPTIONS));
        }
    }
    
    public String executeConvert(String convertedMap) throws IOException, InterruptedException{
        String fileOutput = convertedMap + FilesExtension.NETCONVERT;
        File netconvertFile = new File(fileOutput);
        String output = "";
        
        addOptions(Netconvert.OUTPUT.getCommand(), fileOutput);
        ProcessBuilder netconvert = new ProcessBuilder(convertCommand);
        netconvert.redirectErrorStream(true);
        
        netconvertFile.createNewFile();
        System.out.println(convertCommand);
        
        Process process = netconvert.start();
        InputStream stdin = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin);
        BufferedReader br = new BufferedReader(isr);
        
        String line = null;
        while ((line = br.readLine()) != null)
            output += line;

        process.waitFor();
        return output;
    }
    
    public void addOptions(String... options){
        convertCommand.addAll(Arrays.asList(options));
    }
    
    public void addOptions(List<String> options){
        for (String option : options)
            convertCommand.add(option);
    }  
}
