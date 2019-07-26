package delete;

/**
 *
 * @author Neblis
 */
public class Connection {
    private final String from;
    private final String to;
    private final String via;
    
    private final char dir;
    private final char state;
    
    private final int fromLane;
    private final int toLane;

    public Connection(String from, String to, String via, char dir, char state, int fromLane, int toLane) {
        this.from = from;
        this.to = to;
        this.via = via;
        this.dir = dir;
        this.state = state;
        this.fromLane = fromLane;
        this.toLane = toLane;
    }
    
    
}
