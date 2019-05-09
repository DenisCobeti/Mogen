package model.routes;

import java.util.List;

/**
 *
 * @author Neblis
 */
public class Route {
    //Format that will appear on the XML File
    private static final String FILE_FORMAT = "<Vtype id=\"%i\" color=\"%d, %d, %d\""
            + " edges=\"%s\" />";
    
    private int id;
    private int[] color;
    private List<String> edges;

    public Route(int id, int[] color, List<String> edges) {
        this.id = id;
        this.color = color;
        this.edges = edges;
    }
}
