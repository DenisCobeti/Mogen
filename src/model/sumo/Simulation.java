package model.sumo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Neblis
 */
public class Simulation {
    
    private final static String ID = "id";
    
    private HashMap<String, Edge> edges = new HashMap<>();
    private HashMap<String, Connection> connections = new HashMap<>();
    private HashMap<String, Junction> junctions = new HashMap<>();
    private HashMap<String, RoadType> roadTypes = new HashMap<>();
    
    private String  name;

    public Simulation(String name) {
        this.name = name;
    }
    
    private void parseNetwork(String location) throws FileNotFoundException, 
                                                            XMLStreamException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(location);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
        reader.nextTag(); // pass the net tag
        String id;
        
        while(!reader.getLocalName().equals(Edge.TAG)) reader.nextTag(); // go to edges
        while (reader.hasNext()){
           if(reader.isStartElement()){
               switch(reader.getLocalName()){
                    case Edge.TAG:
                        id = reader.getAttributeValue(null, ID);
                        reader.nextTag(); // to lane
                        edges.put(id, new Edge(
                                 reader.getAttributeValue(null, Edge.LENGTH)
                                ,reader.getAttributeValue(null, Edge.SHAPE)));
                        break;
                    case Edge.LANE:
                        
               }
           }
       }
    }

    public String getName() {
        return name;
    }
    
}
