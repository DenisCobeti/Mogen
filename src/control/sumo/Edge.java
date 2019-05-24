package control.sumo;

import java.util.Collection;
/**
 *
 * @author Neblis
 */
public class Edge {
    private final double lenght;
    private final Collection shapePositions;

    public Edge(double linght, Collection shapePositions) {
        this.lenght = linght;
        this.shapePositions = shapePositions;
    }
}
