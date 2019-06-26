package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import model.sumo.Connection;
import model.sumo.Edge;
import model.sumo.Junction;
import model.sumo.Lane;
import model.sumo.RoadType;

/**
 *
 * @author Neblis
 */
public class Simulation {
    
    private final static String ID = "id";
    private final static int MAX_LANES = 4;
    
    private final HashMap<String, Lane> lanes = new HashMap<>();
    private final HashMap<String, Connection> connections = new HashMap<>();
    private final HashMap<String, String> junctions = new HashMap<>();
    private final HashMap<String, RoadType> roadTypes = new HashMap<>();
    
    private final String[]  commands;

    public Simulation(String[] commands) {
        this.commands = commands;
    }
    
    public void parseNetwork(String location) throws FileNotFoundException, 
                                                            XMLStreamException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(location);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        reader.nextTag(); // pass the net tag
        int event;
        String tag;
        while(!reader.getLocalName().equals(Edge.TAG)) reader.nextTag(); // go to edges
        while (reader.hasNext()){
            event = reader.next();
            if(event == XMLStreamConstants.START_ELEMENT){
                /*switch(reader.getLocalName()){
                     case Lane.TAG:
                         lanes.put(reader.getAttributeValue(null, ID),
                             new Lane( reader.getAttributeValue(null, Lane.LENGTH)
                                 ,reader.getAttributeValue(null, Lane.SHAPE)));
                         System.out.println(reader.getAttributeValue(null, Lane.SHAPE));
                         
                         break;
                     case Junction.TAG:
                        
                         break;
                     default: 
                        
                }*/
                tag = reader.getLocalName();
                
                if(tag.equals(Lane.TAG)){
                    lanes.put(reader.getAttributeValue(null, ID),
                        new Lane( reader.getAttributeValue(null, Lane.LENGTH)
                                 ,reader.getAttributeValue(null, Lane.SHAPE)));
                    //System.out.println(reader.getAttributeValue(null, Lane.SHAPE));
                }else if(tag.equals(Junction.TAG)){
                    junctions.put(reader.getAttributeValue(null, ID), 
                                  reader.getAttributeValue(null, Lane.SHAPE));
                }      
            }  
            
        }
        printMap(lanes);
        //printMap(junctions);
       
    }

    public static void printMap(Map mp) {
    Iterator it = mp.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        System.out.println(pair.getKey() + " = " + pair.getValue());
        it.remove(); // avoids a ConcurrentModificationException
    }
}
}
