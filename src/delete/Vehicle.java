package delete;

/**
 *
 * @author Denis C
 */
public class Vehicle {
    
    //Format that will appear on the XML File
    private static final String FILE_FORMAT = "<vehicle id=\"%d\" type=\"%s\""
            + " route=\"%s\" depart=\"%d\" color=\"%d, %d, %d\" />";
    
    private String type;
    private String route;
    private int id;
    private int depart;
    
    private int[] color;

    public Vehicle(String type, String route, int depart, int[] color) {
        this.type = type;
        this.route = route;
        this.depart = depart;
        this.color = color;
    }

    public String getType() {return type;}
    public String getRoute() {return route;}
    public int getId() {return id;}
    public int getDepart() {return depart;}
    public int[] getColor() {return color;}

    @Override
    public String toString() {
        return "ok boom";
    }
    public String toFileString(){
        return "";
    }
}
