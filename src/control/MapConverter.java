package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import model.map.MapAPI.APIS;
import model.constants.FilesExtension;
import model.constants.Netconvert;
import model.constants.RoadTypes;
import model.map.MapSelection;

/**
 * Map once already converted using netconvert
 * @author Denis C
 */
public class MapConverter {
    
    
    private final static String OSM_FILE_BOUNDS = "bounds"; 
    private final static String OSM_FILE_NODE = "node"; 
    private final static String OSM_FILE_LAT = "lat"; 
    private final static String OSM_FILE_LON = "lon"; 
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

    void pruneNodes(String mapName, MapSelection selection) throws FileNotFoundException, 
                                                            XMLStreamException,
                                                            IOException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        
        InputStream in = new FileInputStream(mapName);
        //XMLEventReader reader = inputFactory.createXMLEventReader(in);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        
        OutputStream out = new FileOutputStream(mapName +'0');
        XMLEventWriter writer =  outputFactory.createXMLEventWriter(out);
        
        //XMLEvent event;
        int event;
        boolean internal = true;
        String tag, function;
        // conversion for faster comparison
        
        double lon = 0, lat = 0;
        while (reader.hasNext()){
            event = reader.next();
            if(event == XMLStreamConstants.START_ELEMENT){
                tag = reader.getLocalName();
                
                if(tag.equals(OSM_FILE_NODE)){
                    lon = Double.valueOf(reader.getAttributeValue(null, OSM_FILE_LON));
                    lat = Double.valueOf(reader.getAttributeValue(null, OSM_FILE_LAT));
                    
                    if ((lon > selection.maxLon) || (lon < selection.minLon) ||
                     (lat > selection.maxLat) || (lat < selection.minLat)){
                        System.out.println("found one");
                    }
                }
        }
        /*
        while (reader.hasNext()){
            event = reader.nextEvent();
            writer.add(event);
            if(event.getEventType() == XMLStreamConstants.START_ELEMENT){
                tag = event.asStartElement().getName().toString();
                
                if(tag.equals(OSM_FILE_NODE)){
                    Iterator<Attribute> attribute = event.asStartElement().getAttributes();
                    
                    while(attribute.hasNext()){
                        Attribute myAttribute = attribute.next();
                        if(myAttribute.getName().toString().equals(OSM_FILE_LON)){
                            lon  = Double.valueOf(myAttribute.getValue());
                        }else if (myAttribute.getName().toString().equals(OSM_FILE_LAT)){
                            lat = Double.valueOf(myAttribute.getValue());
                        }
                    }
                    if ((lon > selection.maxLon) || (lon < selection.minLon) ||
                     (lat > selection.maxLat) || (lat < selection.minLat)){
                        System.out.println("found one");
                        continue;
                    }
                    
                    writer.add(event);
                }
            }else if (event.getEventType() == XMLStreamConstants.ATTRIBUTE){
                writer.add(event);
            }  */
            
        }
        reader.close();
        //writer.close();
        in.close();
        //out.close();
    }
}
