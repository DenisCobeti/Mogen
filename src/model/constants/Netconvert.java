package model.constants;

/**
 * Enum that contains all the constants related to the Netconvert tool.
 * The options that have a default boolean, are writen with the oposite one.
 * This tool is used to convert an OSM Map to the format used by SUMO
 * @author Denis C
 */
public enum Netconvert {
    
    PROGRAM ("netconvert"), //name of the conversion programm
    
    OSM_MAP ("--osm"), //OSM map to convert
    OUTPUT ("-o "), //File to output
    
    REMOVE_GEOMETRY ("--geometry.remove"), //Simplyfies the irrelevant geometry
    GUESS_ROUNDABOUTS("--roundabouts.guess"), //Decides direction of roundabouts
    JOIN_JUNCTIONS ("--junctions.join"), // Joins duplicated junctions, may fail
    FLATTEN("--flatten TRUE"),	//Remove all z-data; default: false
    LEFTHANDED ("--lefthand"),
    
    GUESS_TRAFIC_LIGHTS("--tls.guess TRUE");
    
    private final String command;
    
    private Netconvert(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
    
    @Override
    public String toString(){
        return command;
    }
}
