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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
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
        XMLEventReader reader = inputFactory.createXMLEventReader(in);
        
        OutputStream out = new FileOutputStream(mapName + ".tmp");
        XMLEventWriter writer =  outputFactory.createXMLEventWriter(out);
        
        XMLEvent event;
        boolean deleteSection = false;
        
        double lon = 0, lat = 0;
        
        while (reader.hasNext()){
            event = reader.nextEvent();
            
            if(deleteSection){
                // if we wish to delete a section iterate until you find an end element
                if(event.isEndElement()){
                    EndElement endElement = event.asEndElement();
                    // if the end element id the desired one to delete, return iteration to normal
                    if (endElement.getName().getLocalPart().equals(OSM_FILE_NODE)){
                        deleteSection = false;
                    }
                    continue;
                }else{
                    continue;
                }
            }
            
            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                
                if(startElement.getName().getLocalPart().equals(OSM_FILE_NODE)){
                    
                    Iterator<Attribute> attributes = startElement.getAttributes();
                    
                    while (attributes.hasNext()){
                        Attribute attribute = attributes.next();
                        
                        if(attribute.getName().toString().equals(OSM_FILE_LON)){
                            lon = Double.valueOf(attribute.getValue());
                        }else if(attribute.getName().toString().equals(OSM_FILE_LAT)){
                            lat = Double.valueOf(attribute.getValue());
                        }
                    }
                    
                    if ((lon > selection.maxLon) || (lon < selection.minLon) ||
                     (lat > selection.maxLat) || (lat < selection.minLat)){
                        System.out.println("found one");
                        deleteSection = true;
                        continue;
                    }
                   
                }
                
            }
            writer.add(event);
            /*
            if(event.isCharacters()){
                //System.out.println("Element TextValue :" + xsr.getText());
                txt = reader.getElementText();
                writer.add(event);
            }*/
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
            
        
        reader.close();
        //writer.close();
        in.close();
        //out.close();
    }
}
