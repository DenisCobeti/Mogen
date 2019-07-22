package model.routes;

/**
 *
 * @author Denis C
 */
public class Flow {
    private static final String FILE_FORMAT = "<flow id=\"%s\" begin=\"%d\""
            + " end=\"%d\" number=\"%d\" from=\"%s\" to=\"%s\" />";
    
    private int begin, end;
    private String origin, destination;
    private VType type;
    private int number;

    public Flow(int begin, int end, String origin, String destination, VType type, 
            int number) {
        this.begin = begin;
        this.end = end;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.number = number;
    }

    
    public VType getType() { return type; }
    public int getNumber() { return number; }
    
    public String toFile(String id) {
        return String.format(FILE_FORMAT, id, begin, end, number, origin, 
                            destination);
    }
    
}
