package model.routes;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author darkm
 */
public class TAZ {
    
    private static final String FILE_FORMAT = "<taz id=\"%s\" edges=\"%s\" />";
    
    private List<String> edges;

    public TAZ(List<String> edges) {
        this.edges = edges;
    }
    
    public TAZ() {
        this.edges = new LinkedList();
    }
    
    public void addEdge(String edge){
        edges.add(edge);
    }

    public void setEdges(List<String> edges) {
        this.edges = edges;
    }

    public List<String> getEdges() {
        return edges;
    }
    
    public String toFile(String id) {
        return String.format(FILE_FORMAT, id, edges);
    }
    
}
