package model.routes;

import java.util.LinkedList;
import java.util.List;
import model.topology.Lane;

/**
 *
 * @author darkm
 */
public class TAZ {
    
    private static final String FILE_FORMAT = "<taz id=\"%s\" edges=\"%s\" />";
    
    private List<Lane> edges;

    public TAZ(List<Lane> edges) {
        this.edges = edges;
    }
    public TAZ() {
        this.edges = new LinkedList();
    }
    
    public void addEdge(Lane edge){
        edges.add(edge);
    }

    public void setEdges(List<Lane> edges) {
        this.edges = edges;
    }

    public List<Lane> getEdges() {
        return edges;
    }
    
    public String toFile(String id) {
        String edgesString = "";
        
        for(Lane lane : edges){
            edgesString += lane.getEdge() + " ";
        }
        return String.format(FILE_FORMAT, id, edgesString);
    }
    
}
