package control.sumo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Neblis
 */
public class Simulation {
    private HashMap<String, Edge> edges = new HashMap<>();
    private HashMap<String, Connection> connections = new HashMap<>();
    private HashMap<String, Junction> junctions = new HashMap<>();
    private HashMap<String, RoadType> roadTypes = new HashMap<>();
    
    private String mapLocation;

    public Simulation(String mapLocation) {
        this.mapLocation = mapLocation;
    }
    
    private void parseNetwork(String location) throws FileNotFoundException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(location);
       // XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
    }
}
