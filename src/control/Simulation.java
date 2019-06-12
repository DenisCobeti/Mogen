package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.stream.XMLInputFactory;
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
    
    private HashMap<String, Lane> lanes = new HashMap<>();
    private HashMap<String, Connection> connections = new HashMap<>();
    private HashMap<String, Junction> junctions = new HashMap<>();
    private HashMap<String, RoadType> roadTypes = new HashMap<>();
    
    private String  name;

    public Simulation(String name) {
        this.name = name;
    }
    
    public void parseNetwork(String location) throws FileNotFoundException, 
                                                            XMLStreamException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(location);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        reader.nextTag(); // pass the net tag
        
        while(!reader.getLocalName().equals(Edge.TAG)) reader.nextTag(); // go to edges
        while (reader.hasNext()){
            switch(reader.getLocalName()){
                 case Lane.TAG:
                     lanes.put(reader.getAttributeValue(null, ID),
                         new Lane( reader.getAttributeValue(null, Lane.LENGTH)
                             ,reader.getAttributeValue(null, Lane.SHAPE)));
                     System.out.println(reader.getAttributeValue(null, Lane.SHAPE));
                     reader.next();
                     break;
                 case Junction.TAG:
                     reader.next();
                     break;
                 default: 
                     reader.next();
            }
        }
       
    }

    public String getName() {
        return name;
    }
    
}
