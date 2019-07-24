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
    private String type;
    private int number;

    public Flow(int begin, int end, String origin, String destination, String type, 
            int number) {
        this.begin = begin;
        this.end = end;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.number = number;
    }

    public Flow(int begin, int end, String origin, String destination, int number) {
        this.begin = begin;
        this.end = end;
        this.origin = origin;
        this.destination = destination;
        this.number = number;
        this.type = VType.DISTRIBUTION;
    }
    
    public String getType() { return type; }
    public int getNumber() { return number; }
    public int getBegin() { return begin; }
    public int getEnd() { return end; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }

    public String originEdge(){
        return origin.substring(0, origin.indexOf('_'));
    }
    public String destinationEdge(){
        return destination.substring(0, destination.indexOf('_'));
    }
    
    @Override
    public String toString() {
        return "Flow{" + "begin=" + begin + ", end=" + end + ", origin=" + origin + ", destination=" + destination + ", type=" + type + ", number=" + number + '}';
    }
    
    public String toFile(String id) {
        return String.format(FILE_FORMAT, id, begin, end, number, originEdge(), 
                            destinationEdge());
    }
    
}
