package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
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
    private  List<String> convertCommand;
    
    public final static Netconvert[] DEFAULT_OPTIONS = new Netconvert[] {
                    Netconvert.JOIN_JUNCTIONS,
                    Netconvert.GUESS_ROUNDABOUTS,
                    Netconvert.REMOVE_GEOMETRY};
    
    public final static RoadTypes[] DEFAULT_ROADS = new RoadTypes[] {
                    RoadTypes.RAIL};
   
    private HashSet<String> options;
    
    public MapConverter(String map, APIS api) throws IOException {
        convertCommand = new LinkedList();
        options = new HashSet();
        convertCommand.add(Netconvert.PROGRAM.toString());
        
        for (Netconvert option: DEFAULT_OPTIONS) options.add(option.getCommand());
        for (Netconvert option: DEFAULT_OPTIONS) options.add(option.getCommand());
        
        switch(api){
            case OSM:
                // add recommended options when an OSM file is converted
                convertCommand.add(Netconvert.OSM_MAP.toString());
                convertCommand.add(map);
                //convertCommand.addAll(Arrays.asList(DEFAULT_OSM_OPTIONS));
        }
    }
    
    public String executeConvert(String convertedMap) throws IOException, InterruptedException{
        String fileOutput = convertedMap + FilesExtension.NETCONVERT;
        File netconvertFile = new File(fileOutput);
        List<String> convertCommandFun =  this.convertCommand;
        convertCommandFun.addAll(options);
        String output = "";
        
        addOptions(convertCommandFun, Netconvert.OUTPUT.getCommand(), fileOutput);
        ProcessBuilder netconvert = new ProcessBuilder(convertCommandFun);
        netconvert.redirectErrorStream(true);
        
        netconvertFile.createNewFile();
        System.out.println(convertCommandFun);
        
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
    
    public String executeConvert(String convertedMap, HashSet<String> roads) 
                                        throws IOException, InterruptedException{
        String fileOutput = convertedMap + FilesExtension.NETCONVERT;
        File netconvertFile = new File(fileOutput);
        List<String> convertCommandFun =  this.convertCommand;
        
        convertCommandFun.addAll(options);
        String output = "";
        addOptions(convertCommandFun, Netconvert.OUTPUT.getCommand(), fileOutput);
        if (!roads.isEmpty()) addRoads(convertCommandFun, roads);
        
        ProcessBuilder netconvert = new ProcessBuilder(convertCommandFun);
        netconvert.redirectErrorStream(true);
        
        netconvertFile.createNewFile();
        System.out.println(convertCommandFun);
        
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
    public void addOptions( List<String> command, String... options){
        command.addAll(Arrays.asList(options));
    }
    
    public void addOptions(List<String> command, HashSet<String> options){
        options.forEach((option) -> {
            convertCommand.add(option);
        });
    }
    public void addRoads (List<String> command, HashSet<String> roads){
        command.add(Netconvert.REMOVE_ROADS.getCommand());
        StringJoiner joiner = new StringJoiner(",");
        
        roads.forEach((road) -> {
            joiner.add(road);
        });
        
        command.add(joiner.toString());
    }  

    void pruneNodes(String mapName) {
        
    }
}
